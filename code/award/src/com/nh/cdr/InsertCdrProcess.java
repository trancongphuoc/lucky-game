/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.cdr;

import com.nh.bean.CdrLog;
import com.nh.repository.CdrLogDao;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class InsertCdrProcess implements Runnable{
    public static Logger logger = Logger.getLogger(InsertCdrProcess.class);
    private final static int BATCH_SIZE = 500;
    private final static int DEFAULT_SLEEP = 5000;
    private final LinkedList<CdrLog> transactions = new LinkedList<>();
    
    private final Object obj = new Object();
    private static InsertCdrProcess _instance = null;
    
    private InsertCdrProcess() {
    }

    public static InsertCdrProcess getInstance() {
        if (_instance == null) {
            _instance = new InsertCdrProcess();
            Thread thread = new Thread(_instance);
            thread.setDaemon(false);
            thread.setName("RenewProcess-");
            thread.start();
        }

        return _instance;
    }

    public void enqueue(CdrLog chargeLog) {
        synchronized (obj) {
            transactions.add(chargeLog);
            logger.info("enqueue("+transactions.size()+"): " + chargeLog);
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
            List<CdrLog> datas = dequeue();
            if (datas != null) {
                int length = datas.size();
                try {
                    process(datas);
                    logger.info("processed batch size: " + length);
                } catch (Exception ex) {
                    logger.error("error when save cdr log",ex);
                }
                datas.clear();
                datas = null;
                try {
                    int sleep = 1000 * BATCH_SIZE / length; // ms
                    if (sleep > DEFAULT_SLEEP) {
                        sleep = DEFAULT_SLEEP;
                    }
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }
            } else {
                try {
                    Thread.sleep(DEFAULT_SLEEP);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }
            }
        }
    }

    private void process(List<CdrLog> datas) {
        try {
        CdrLogDao dao = new CdrLogDao();
        dao.saveBatch(datas);
        }catch(Exception ex){
            logger.error("error when save cdr log",ex);
        }
    }
    
}
