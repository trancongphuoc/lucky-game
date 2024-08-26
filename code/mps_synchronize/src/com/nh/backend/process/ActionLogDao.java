/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;


import com.nh.backend.bean.UssdKpi;
import com.nh.backend.database.ConnectionPoolManager;
import com.nh.util.MyLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActionLogDao implements Runnable{
    /**
     * thong thuong batch size se phai lon hon TPS (mac dinh TPS = 200/node)
     * day la ung dung lon nen de batch size an toan la 500 ~ max TPS
     * nen sau moi lan xu ly he thong se nghi: dua tren feedback phan hoi. max TPS/size of data
     */
    
    private final static int BATCH_SIZE = 500;
    private final static int DEFAULT_SLEEP = 5000;
    
    private final static String SQL_PRO_INSERT = "Insert into ACTION_LOG " +
                                                    "(ID,TRANS_ID,MODULE_CODE,CHANNEL," +
                                                    "MSISDN,ACTION,DURATION,URL," +
                                                    "BODY,RESPONSE,DESCRIPTION,ERROR_CODE," +
                                                    "RESPONSE_TIME,REQUEST_TIME,STATE_ID,STEPS) " +
                                                    "values (?,?,?,?," +
                                                    "?,?,?,?," +
                                                    "?,?,?,?," +
                                                    "?,?,?,?)";
    
    private final LinkedList<UssdKpi> queue = new LinkedList<UssdKpi>();
    private final Object obj = new Object();
    
    
    private static ActionLogDao _instance = null;
    
    private int batchSize = BATCH_SIZE;
    
    private ActionLogDao() {
    }


    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
    
    public synchronized static ActionLogDao getInstance() {
         if (_instance == null) {
            _instance = new ActionLogDao();
            Thread t = new Thread(_instance);
            t.setName("thread-" + ActionLogDao.class.getSimpleName());
            t.start();
        }
        
        return _instance;
    }
   
    public void enqueue(UssdKpi transaction) {
        synchronized (obj) {
            queue.add(transaction);
            MyLog.Infor("enqueue queue ("+queue.size()+"): " + transaction);
        }
    }

    private List<UssdKpi> dequeue() {
        synchronized (obj) {
            if (queue.isEmpty()) {
                return null;
            }
            List<UssdKpi> datas = new ArrayList<UssdKpi>(batchSize);
            int i = 0;
            while (queue.size() > 0 && (i < batchSize)) {
                UssdKpi transaction = queue.poll();
                datas.add(transaction);
                i++;
            }

            return datas;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<UssdKpi> datas = dequeue();
                if (datas == null) {
                     Thread.sleep(DEFAULT_SLEEP);
                     continue;
                }
                
                process(datas);
                int length = datas.size();
                MyLog.Infor("ActionLog processed bacth queue = " + length);
                datas.clear();
                datas = null;

                int sleep = 1000 * batchSize / length; //m s
                if (sleep > DEFAULT_SLEEP) {
                    sleep = DEFAULT_SLEEP;
                }
                Thread.sleep(sleep);
            } catch (Exception ex) {
                MyLog.Error("ActionLog",ex);
            }
        }
    }
     
    public Connection getConnection() throws SQLException {
        return ConnectionPoolManager.getDefaultConnection();
    }
    
    private void closeConnection(Connection conn) throws SQLException{
        conn.close();
    }
     
    private void process(List<UssdKpi> datas) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(SQL_PRO_INSERT);
            ps.setQueryTimeout(20);//second
            int i = 0;
            for (UssdKpi data : datas) {
                i = 0;
                ps.setString(++i, data.getId());
                ps.setString(++i, data.getTransId());
                ps.setString(++i, data.getModuleCode());
                ps.setString(++i, data.getChannel());
                
                ps.setString(++i, data.getMsisdn());
                ps.setString(++i, data.getAction());
                ps.setLong(++i, data.getDuration());
                ps.setString(++i, data.getUrl());
                
                ps.setString(++i, getMax(data.getBody(),2000));
                ps.setString(++i, getMax(data.getResponse(),2000));
                ps.setString(++i, data.getDescription());
                ps.setString(++i, data.getErrorCode());
                
                ps.setTimestamp(++i, data.getResponseTime());
                ps.setTimestamp(++i, data.getRequestTime());
                ps.setInt(++i, data.getStateId());
                ps.setString(++i, data.getSteps());
                
//                ps.setString(++i, data.getIpNode());
                ps.addBatch();
            }

            ps.executeBatch();
            connection.commit();
            ps.close();
            ps = null;
        } catch (Exception ex) {
            if (connection != null) {
                try {
                    connection.rollback();
                }catch (Exception ee) {
                }
            }
            MyLog.Error("error insert in class action log", ex);
        } finally {
            try {
                closeConnection(connection);
            } catch (Exception ee) {
            }
        }
    }

    private String getMax(String value, int maxLength) {
        if (value == null || value.length() < maxLength) {
            return value;
        }
        
        return value.substring(0, maxLength -1);
    }
}

