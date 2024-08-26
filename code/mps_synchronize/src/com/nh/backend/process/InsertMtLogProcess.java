/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.MTlog;
import com.nh.backend.repository.MTlogDao;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class InsertMtLogProcess implements Runnable{
    private Logger logger = Logger.getLogger(InsertMtLogProcess.class);
    private final static int BATCH_SIZE = 500;
    private final static int DEFAULT_SLEEP = 5000;
    private final LinkedList<MTlog> transactions = new LinkedList<>();
    
    private final Object obj = new Object();
    private static InsertMtLogProcess _instance = null;
    
    private InsertMtLogProcess() {
    }

    public static synchronized InsertMtLogProcess getInstance() {
        if (_instance == null) {
            _instance = new InsertMtLogProcess();
            Thread thread = new Thread(_instance);
//            thread.setDaemon(false);
            thread.setName("MtLog-");
            thread.start();
        }

        return _instance;
    }

    public void enqueue(MTlog chargeLog) {
        synchronized (obj) {
            transactions.add(chargeLog);
            logger.info("enqueue mt ("+transactions.size()+"): " + chargeLog);
        }
    }

    public List<MTlog> dequeue() {
        synchronized (obj) {
            if (transactions.isEmpty()) {
                return null;
            }
            List<MTlog> datas = new LinkedList<>();
            int i = 0;
            while (transactions.size() > 0 && (i < BATCH_SIZE)) {
                MTlog transaction = transactions.poll();
                datas.add(transaction);
                i++;
            }

            return datas;
        }
    }

    @Override
    public void run() {
        while (true) {
            List<MTlog> datas = dequeue();
            if (datas != null && !datas.isEmpty()) {
                 int length = datas.size();
                try {
                    process(datas);
                     logger.info("processed batch size: " + length);
                    datas.clear();
                    datas = null;
                } catch (Exception ex) {
                    logger.error("error when save cdr log",ex);
                }
               
                try {
                    int sleep = 1000 * BATCH_SIZE / length; // ms
                    if (sleep > DEFAULT_SLEEP) {
                        sleep = DEFAULT_SLEEP;
                    }
                    Thread.sleep(sleep);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else {
                try {
                    logger.info("MT process no data sleep(s): " + (DEFAULT_SLEEP/1000));
                    Thread.sleep(DEFAULT_SLEEP);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }
    }

    private void process(List<MTlog> datas) {
        try {
            MTlogDao dao = new MTlogDao();
            dao.saveBatch(datas);
        }catch(Exception ex){
            logger.error("error when save cdr log",ex);
        }
    }
    
}
