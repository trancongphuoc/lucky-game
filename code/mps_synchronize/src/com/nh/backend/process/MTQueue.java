/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.MTSMS;
import com.nh.backend.database.ConnectionPoolManager;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.backend.database.DirectConnection;
import com.nh.util.CountryCode;
import com.nh.util.MyLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoand
 */
public class MTQueue implements Runnable {
    private static final String PARAMS_MSISDN = "#MSISDN"; 
    private static final String PARAMS_SHORTCODE = "#SHORT_CODE";
    private static final String PARAMS_ALIAS = "#ALIAS";
    private static final String PARAMS_CONTENT = "#CONTENT";
    private static final String PARAMS_USERNAME = "#USERNAME";
    private static final String PARAMS_CREATED_TIME = "#CREATED_TIME";
    private static final String PARAMS_TRANSID = "#TRANSID";
    private static final int BATCH_SIZE = 1000;
    private static final int INSERT_SEQUENCE = 10 * 1000;
    
    private static MTQueue mMe = null;
    private static final Object mLogLock = new Object();
    
    private final List<MTSMS> mLogQueue;
    private boolean mIsRunning;
    
    private MTQueue() {
        mLogQueue = new LinkedList<MTSMS>();
        mIsRunning = false;
    }

    public synchronized static MTQueue getInstance() {
        if (mMe == null) {
            mMe = new MTQueue();
            mMe.start();
        }
        
        return mMe;
    }
    
  
    public void enqueue(MTSMS value) {
            synchronized (mLogLock) {
                mLogQueue.add(value);
                MyLog.Infor("enqueue MT: ("+mLogQueue.size()+")" + value.getDestAddress());
            }
    }
   
    public void start() {
        if (!mIsRunning) {
            mIsRunning = true;
            Thread t = new Thread(this);
            t.start();
        }
    }
    
    public void stop() {
        mIsRunning = false;
    }
    
    
    private void process(List<MTSMS> datas) {
        Connection connection = null;
        PreparedStatement cs = null;
//        String f[] = "#MSISDN|#SHORT_CODE|#ALIAS|#CONTENT|#USERNAME|#CREATED_TIME".split("|");
        try {
            DirectConnection directConnection = DatabaseConnectionPool.getInstance().getConnection();
            connection = directConnection.getDatabaseConnection();
//            connection = ConnectionPoolManager.getDefaultConnection();
            cs = connection.prepareStatement("INSERT INTO LUCKY_SMSGW_MT_LOGS (isdn, shortcode,content, username, created)" +
            " VALUES   (?, ?, ?,?,?)");
           
            int j ;
            for (MTSMS task : datas) {
                j = 0;
                cs.setString(++j, CountryCode.formatMobile(task.getDestAddress()));
                cs.setString(++j, task.getSourceAddress());
//                cs.setString(++j, task.getAlias());
                String content = task.getOriginalContent();
                if (content != null && content.length() > 1000) {
                    content = content.substring(0, 1000);
                }
                cs.setString(++j, content);
                cs.setString(++j, task.getUsername());
                cs.setTimestamp(++j, new Timestamp(task.getCreatedTime()));

                cs.addBatch();
            }

            cs.executeBatch();
            connection.commit();
             cs.close();
             cs = null;
            directConnection.closeConnection(connection);
        } catch (SQLException ex) {
            MyLog.Error("error insert in class MT log",ex);
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                    cs = null;
                }
//                if (connection != null) {
//                    connection.commit();
//                }
            } catch (SQLException ee) {
            }
                
        }
    }
    private  List<MTSMS> dequeueLog() {
        synchronized (mLogLock) {
            if (mLogQueue.isEmpty()) {
                return null;
            }
            
            List<MTSMS> datas = new LinkedList<MTSMS>();
            
            int i = 0;
            while (mLogQueue.size() > 0 && (i < BATCH_SIZE)) {
                MTSMS transaction = mLogQueue.remove(0);
                datas.add(transaction);
                i++;
            }

            return datas;
        }
    }
    
    @Override
    public void run() {
        while (mIsRunning) {
            try {
                List<MTSMS> datas = dequeueLog();
                if (datas != null && !datas.isEmpty()) {
                    process(datas);
                    int length = datas.size();
                    MyLog.Infor("MTlog processed batch size: " + length);
                    datas.clear();
                    datas = null;

                    int sleep = 1000 * BATCH_SIZE / length; // ms
                    if (sleep > INSERT_SEQUENCE) {
                        sleep = INSERT_SEQUENCE;
                    }
                    Thread.sleep(sleep);
                } else {
                    MyLog.Infor("MTlog no data sleep(s): " + (INSERT_SEQUENCE/1000));
                    Thread.sleep(INSERT_SEQUENCE);
                }

            } catch (Exception ex) {
                MyLog.Error(ex);
            }
        }
    }

    
}
