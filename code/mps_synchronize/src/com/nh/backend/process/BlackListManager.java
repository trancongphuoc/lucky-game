/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.BlackList;
import com.nh.backend.repository.BlackListDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class BlackListManager implements Runnable {

    public static Logger logger = Logger.getLogger(BlackListManager.class);
    private final static int DEFAULT_SLEEP = 10000;
    private final List<String> datas = new ArrayList<>();

    private final Object obj = new Object();
    private static BlackListManager _instance = null;

    private BlackListManager() {
    }

    public static synchronized BlackListManager getInstance() {
        if (_instance == null) {
            _instance = new BlackListManager();
            Thread thread = new Thread(_instance);
            thread.setDaemon(false);
            thread.setName("BlackListManager-");
            thread.start();
        }

        return _instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                process();
                Thread.sleep(DEFAULT_SLEEP);
            } catch (Exception ex) {
                logger.error("error load blackList", ex);
            }
        }
    }

    private void process() {
        BlackListDao contentDao = new BlackListDao();
        List<BlackList> contents = contentDao.getAll();
//        if (contents.isEmpty()) {
//            return;
//        }
        
        synchronized (obj) {
            datas.clear();
            for (BlackList smsContent : contents) {
                if ("1".equals(smsContent.getStatus())) {
                    String tmp = smsContent.getMsisdn();
                    datas.add(tmp.trim());
                }
            }
        }
    }

    public boolean isBlackList(String msisdn) {
        String tmp = msisdn.trim().toLowerCase();
        synchronized (obj) {
            if (datas.contains(msisdn)) {
                return true;
            }
            
            for (String blackKey : datas) {
                if (tmp.contains(blackKey)
                        || tmp.matches(blackKey)) {
                    return true;
                }
            }
        }
        return false;
    }

    
}
