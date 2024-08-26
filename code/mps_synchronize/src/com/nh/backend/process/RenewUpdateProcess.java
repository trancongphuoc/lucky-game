/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.Subscriber;
import com.nh.backend.database.DatabaseConnectionPool;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class RenewUpdateProcess implements Runnable{
    private final static int ONE_DAY = 24* 3600 * 1000; //1 day
    
    private final Logger logger = Logger.getLogger(RenewUpdateProcess.class);
    private final static int DEFAULT_SLEEP = 5000;
    private final LinkedList<Subscriber> datas = new LinkedList<>();
    
    private final Object obj = new Object();
    private static RenewUpdateProcess _instance = null;
    
    private RenewUpdateProcess() {
    }

    public static synchronized RenewUpdateProcess getInstance() {
        if (_instance == null) {
            _instance = new RenewUpdateProcess();
            Thread thread = new Thread(_instance);
//            thread.setDaemon(false);
            thread.setName("RenewUpdateProcess-");
            thread.start();
        }

        return _instance;
    }

    public void enqueue(Subscriber data) {
        synchronized (obj) {
            datas.add(data);
            logger.info("enqueue renew ("+datas.size()+"): " + data);
        }
    }

    public Subscriber dequeue() {
        synchronized (obj) {
            if (datas.isEmpty()) {
                return null;
            }

            return datas.poll();
        }
    }

    @Override
    public void run() {
       while(true) {
           try {
                Subscriber data = dequeue();
                if (data != null) {
                    process(data);
                    Thread.sleep(1);
                } else {
                    logger.info("renew process no data sleep(s): " + (DEFAULT_SLEEP/1000));
                    Thread.sleep(DEFAULT_SLEEP);
                }
           } catch(Exception ex) {
               logger.error("error when save renew log",ex);
           }
       }
    }

    private void process(Subscriber data) {
        updateInfo(data);
    }
    
    public boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
    
    private void updateInfo(Subscriber data){
//        SubscriberDao subscriberDao = new SubscriberDao();
//        Subscriber subscriber  =  subscriberDao.find(data.MSISDN,data.SUB_SERVICE_NAME); 
        //tim kiem trong bang service_subscription
        // neu co thong tin thi update
        //neu chua co, ma trang thai renew la thanh cong thi phai insert (coi nhu la tien trình register)
        boolean success = "0".equals(data.param);
        int status = success ? Subscriber.PARAM_STATUS_ACTIVE :  Subscriber.PARAM_STATUS_PENDING;
//        DatabaseConnectionPool.getInstance().getConnection().renew(data.getMSISDN(),data.SUB_SERVICE_NAME , status);
      
        DatabaseConnectionPool.getInstance().getConnection().getUpdateSub(data.getMSISDN(), data.SUB_SERVICE_NAME, status);
    }
}
