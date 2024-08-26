/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.Subscriber;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.backend.repository.SubscriberDao;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class RegisterCancelProcess implements Runnable{
    private final Logger logger = Logger.getLogger(RegisterCancelProcess.class);
    private final static int DEFAULT_SLEEP = 5000;
    private final LinkedList<Subscriber> datas = new LinkedList<>();
    private final static int ONE_DAY = 24* 3600 * 1000; //1 day
    
    private final Object obj = new Object();
    private static RegisterCancelProcess _instance = null;
    
    private RegisterCancelProcess() {
    }

    public static synchronized RegisterCancelProcess getInstance() {
        if (_instance == null) {
            _instance = new RegisterCancelProcess();
            Thread thread = new Thread(_instance);
//            thread.setDaemon(false);
            thread.setName("RegisterCancelProcess-");
            thread.start();
        }

        return _instance;
    }

    public void enqueue(Subscriber data) {
        synchronized (obj) {
            datas.add(data);
            logger.info("enqueue sub ("+datas.size()+"): " + data);
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
                Subscriber sub = dequeue();
                if (sub != null) {
                    process(sub);
                    Thread.sleep(1);
                } else {
                    logger.info("sub process no data sleep(s): " + (DEFAULT_SLEEP/1000));
                    Thread.sleep(DEFAULT_SLEEP);
                }
           } catch(Exception ex) {
               logger.error("error when save cdr log",ex);
           }
       }
    }

    private void process(Subscriber sub) {
        String param = sub.param;
        int status = Subscriber.PARAM_REGISTER.equalsIgnoreCase(param) ? Subscriber.PARAM_STATUS_ACTIVE :  Subscriber.PARAM_STATUS_CANCEL;
        DatabaseConnectionPool.getInstance().getConnection().getUpdateSub(sub.getMSISDN(),sub.SUB_SERVICE_NAME , status);
//        if (Subscriber.PARAM_REGISTER.equalsIgnoreCase(param)) {
//            processRegister(sub);
//        } else if (Subscriber.PARAM_CANCEL.equalsIgnoreCase(param)) {
//            processCancel(sub);
//        }
    }
    
    public boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
    
}
