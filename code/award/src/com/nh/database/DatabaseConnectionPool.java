/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.database;


import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author 
 */
public class DatabaseConnectionPool {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionPool.class);
    private int mNumberOfConnection;
    private DirectConnection[] mConnection;
    private int mIdx;
    
    private static DatabaseConnectionPool connectionPool;
    
    private DatabaseConnectionPool(Vector<String> connectionConf, int numberOfConnection) {
       this.mNumberOfConnection = numberOfConnection;
       mConnection = new DirectConnection[numberOfConnection];
             
        for (int i=0; i < numberOfConnection; i++) {
            mConnection[i] = new DirectConnection("Oracel-" + i, connectionConf);
            mConnection[i].startConnect();
        }
        
        mIdx = 0;
    }
    
    public static void config(Vector<String> connectionConf, int numberOfConnection) {
        if (connectionPool == null) {
            connectionPool = new DatabaseConnectionPool(connectionConf, numberOfConnection);
        }
    }
    
    public static DatabaseConnectionPool getInstance() {
        return connectionPool;
    }
    
    public synchronized  DirectConnection getConnection() {
        if (mConnection == null || mConnection.length == 0) {
            return null;
        }
        
        DirectConnection result = null;
        int count = 0;
        while (result == null) {
            mIdx++;
            count++;
            if (mIdx >= mConnection.length) {
                mIdx = 0;
            }
            
            if (mConnection[mIdx] != null && mConnection[mIdx].isConnected()) {
                result = mConnection[mIdx];
            }
            
            if (result == null && (count % mConnection.length == 0)) {
                // Go 1 round, wait 5 sec
                logger.warn("Get database Connection failed, wait 5 sec before continue ...");
                try { 
                    Thread.sleep(5000); 
                }
                catch (Exception ex) {
                    logger.error("getConnection DirectConnection  " + ex);
                }
                
                if (count > mConnection.length * 10) {
                    // try 10 round
                    logger.warn("Wait connection too long, retun NULL for GETTER");
                    break;
                }
            }
        }
        
        return result;
    }
    

}
