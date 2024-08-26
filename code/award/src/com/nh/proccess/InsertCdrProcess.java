/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.proccess;

import com.nh.GlobalConfig;
import com.nh.bean.CdrLog;
import com.nh.database.DatabaseConnectionPool;
import com.nh.database.DirectConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author abc
 */
public class InsertCdrProcess implements Runnable {
    private final static String SQL_PRO_INSERT_TRANS_MPI_INSERT_INTO = "INSERT INTO LUCKY_TRANSACTION_LOG (VCGW_REQUEST_ID, MSISDN,TRANS_ID, REQUEST_TIME,"
            + " RESPONSE_TIME, RESPONSE_CODE, SERVICE_NAME, SUB_SERVICE_NAME,"
            + " CMD, CATEGORY_NAME,ITEM_NAME,SUB_CP_NAME, "
            + "CONTENTS, PROVIDER_NAME, PRICE, channel)" +
            "VALUES   (?, ?, ?,?, "
            + "?, ?, ?,?, "
            + "?, ?, ?, ?,"
            + "?, ?, ?, ?)";
    
    private final Logger logger = Logger.getLogger(InsertCdrProcess.class);
    private final static int BATCH_SIZE = 500;
    private final static int DEFAULT_SLEEP = 5000;
    private final LinkedList<CdrLog> transactions = new LinkedList<>();

    private final Object obj = new Object();
    private static InsertCdrProcess _instance = null;

    private InsertCdrProcess() {
    }

    public static synchronized InsertCdrProcess getInstance() {
        if (_instance == null) {
            _instance = new InsertCdrProcess();
            Thread thread = new Thread(_instance);
//            thread.setDaemon(false);
            thread.setName("InsertCdr-");
            thread.start();
        }

        return _instance;
    }

    public void enqueue(CdrLog chargeLog) {
        synchronized (obj) {
            transactions.add(chargeLog);
            logger.info("enqueue cdr: (" + transactions.size() + "):" + chargeLog);
        }
    }

    public List<CdrLog> dequeue() {
        synchronized (obj) {
            if (transactions.isEmpty()) {
                return null;
            }
            List<CdrLog> datas = new LinkedList<>();
            int i = 0;
            while (transactions.size() > 0 && (i < BATCH_SIZE)) {
                CdrLog transaction = transactions.poll();
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
                List<CdrLog> datas = dequeue();
                if (datas == null || datas.isEmpty()) {
                    logger.info("cdr process no data sleep(s): " + (DEFAULT_SLEEP/1000));
                    Thread.sleep(DEFAULT_SLEEP);
                    continue;
                }
                int length = datas.size();
                try {
                    process(datas);
//                    int length = datas.size();
                   
                    datas.clear();
                    datas = null;
                    
                } catch (Exception ex) {
                    logger.error("gre", ex);
                }
                int sleep = 1000 * BATCH_SIZE / length; // ms
                if (sleep > DEFAULT_SLEEP) {
                    sleep = DEFAULT_SLEEP;
                }
                Thread.sleep(sleep);
                
            } catch (Exception ex) {
                logger.error("error2 when save cdr log", ex);
            }
        }
    }

    private void process(List<CdrLog> datas) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            DirectConnection directConnection = DatabaseConnectionPool.getInstance().getConnection();
            connection = directConnection.getDatabaseConnection();
            ps = connection.prepareStatement(SQL_PRO_INSERT_TRANS_MPI_INSERT_INTO);
            ps.setQueryTimeout(30);
            
            int i = 0;
            for (CdrLog cdrLog : datas) {
                i = 0;
                ps.setString(++i, cdrLog.getVCGW_REQUEST_ID());
                ps.setString(++i, cdrLog.getMSISDN());
                ps.setString(++i, cdrLog.getTRANS_ID());
                ps.setTimestamp(++i, cdrLog.getREQUEST_TIME());

                ps.setTimestamp(++i, cdrLog.getRESPONSE_TIME());
                ps.setString(++i, cdrLog.getRESPONSE_CODE());
                ps.setString(++i, cdrLog.getSERVICE_NAME());
                ps.setString(++i, cdrLog.getSUB_SERVICE_NAME());

                ps.setString(++i, cdrLog.getCMD());
                ps.setString(++i, cdrLog.getCATEGORY_NAME());
                ps.setString(++i, cdrLog.getITEM_NAME());
                ps.setString(++i, cdrLog.getSUB_CP_NAME());
                
                ps.setString(++i, cdrLog.getCONTENTS());
                ps.setString(++i, cdrLog.getPROVIDER_NAME());
                ps.setDouble(++i, cdrLog.getPRICE());
                ps.setString(++i, cdrLog.getCHANNEL());
                
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            ps.close();
            ps = null;
            directConnection.closeConnection(connection);
            logger.info("cdr processed batch size: " + datas.size());
        } catch (Exception ex) {
            logger.error("error when save cdr",ex);
        }
    }

}
