/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;


import com.nh.backend.bean.HttpLog;
import com.nh.backend.database.DatabaseConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


public class HttpLogProcess implements Runnable{
    /**
     * thong thuong batch size se phai lon hon TPS (mac dinh TPS = 200/node)
     * day la ung dung lon nen de batch size an toan la 500 ~ max TPS
     * nen sau moi lan xu ly he thong se nghi: dua tren feedback phan hoi. max TPS/size of data
     */
    protected final static Logger log = Logger.getLogger(HttpLogProcess.class);
    private final static int BATCH_SIZE = 500;
    private final static int DEFAULT_SLEEP = 5000;
    
    private final static String SQL_PRO_INSERT = "Insert into HTTP_LOG " +
                                                    "(ID,TRANS_ID,MODULE_CODE,CHANNEL," +
                                                    "MSISDN,ACTION,DURATION,URL," +
                                                    "BODY,RESPONSE,DESCRIPTION,ERROR_CODE," +
                                                    "RESPONSE_TIME,REQUEST_TIME) " +
                                                    "values (?,?,?,?," +
                                                    "?,?,?,?," +
                                                    "?,?,?,?," +
                                                    "?,?)";
    
    private final LinkedList<HttpLog> queue = new LinkedList<HttpLog>();
    private final Object obj = new Object();
    private DataSource dataSource = null;
    
    private DatabaseConnectionPool databaseConnectionPool = null;
    
    private static HttpLogProcess _instance = null;
    
    private int batchSize = BATCH_SIZE;
    
    private HttpLogProcess(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HttpLogProcess config(DataSource dataSource) {
        if (_instance == null) {
            _instance = new HttpLogProcess(dataSource);
            Thread t = new Thread(_instance);
            t.setName("thread-" + HttpLogProcess.class.getSimpleName());
            t.start();
        }
        
        return _instance;
    }
    
    private HttpLogProcess(DatabaseConnectionPool dataSource) {
        this.databaseConnectionPool = dataSource;
    }

    public static HttpLogProcess config(DatabaseConnectionPool dataSource) {
        if (_instance == null) {
            _instance = new HttpLogProcess(dataSource);
            Thread t = new Thread(_instance);
            t.setName("thread-" + HttpLogProcess.class.getSimpleName());
            t.start();
        }
        
        return _instance;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
    
    public static HttpLogProcess getInstance() {
        return _instance;
    }
   
    public void enqueue(HttpLog transaction) {
        synchronized (obj) {
            queue.add(transaction);
            log.info("enqueue queue ("+queue.size()+"): " + transaction);
        }
    }

    private List<HttpLog> dequeue() {
        synchronized (obj) {
            if (queue.isEmpty()) {
                return null;
            }
            List<HttpLog> datas = new ArrayList<HttpLog>(batchSize);
            int i = 0;
            while (queue.size() > 0 && (i < batchSize)) {
                HttpLog transaction = queue.poll();
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
                List<HttpLog> datas = dequeue();
                if (datas == null) {
                     Thread.sleep(DEFAULT_SLEEP);
                     continue;
                }
                
                process(datas);
                int length = datas.size();
                log.info("Http log processed bacth queue = " + length);
                datas.clear();
                datas = null;

                int sleep = 1000 * batchSize / length; //m s
                if (sleep > DEFAULT_SLEEP) {
                    sleep = DEFAULT_SLEEP;
                }
                Thread.sleep(sleep);
            } catch (Exception ex) {
                log.error("UssdKpiInserDao.java",ex);
            }
        }
    }
     
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        if (dataSource != null) {
            connection = dataSource.getConnection();
        }
        
        if (connection == null && databaseConnectionPool != null) {
            connection = databaseConnectionPool.getConnection().getDatabaseConnection();
        }
        
        if (connection == null) {
            log.error("error can not getConnection");  
        }
        
        return connection;
    }
    
    private void closeConnection(Connection conn) throws SQLException{
        if (dataSource != null) {
            conn.close();
        }
    }
     
    private void process(List<HttpLog> datas) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(SQL_PRO_INSERT);
            ps.setQueryTimeout(10);//second
            //(ID,TRANS_ID,MODULE_CODE,CHANNEL,
            //MSISDN,ACTION,DURATION,URL,
            //BODY,RESPONSE,DESCRIPTION,ERROR_CODE,
            //RESPONSE_TIME,REQUEST_TIME) 

            int i = 0;
            for (HttpLog data : datas) {
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
//                ps.setInt(++i, data.getStateId());
//                ps.setString(++i, data.getSteps());
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
            log.error("error insert in class UssdKpiDao", ex);
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

