/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.BlackContent;
import com.nh.backend.repository.BlackContentDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class BlackContenManager implements Runnable {

    public static Logger logger = Logger.getLogger(BlackContenManager.class);
    private final static int DEFAULT_SLEEP = 7000;
    private final List<String> datas = new ArrayList<>();

    private final Object obj = new Object();
    private static BlackContenManager _instance = null;

    private BlackContenManager() {
    }

    public static synchronized BlackContenManager getInstance() {
        if (_instance == null) {
            _instance = new BlackContenManager();
            Thread thread = new Thread(_instance);
            thread.setDaemon(false);
            thread.setName("BlackContenManager-");
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
                logger.error("error load sms", ex);
            }
        }
    }

    private void process() {
        BlackContentDao contentDao = new BlackContentDao();
        List<BlackContent> contents = contentDao.getAll();
        if (contents.isEmpty()) {
            synchronized (obj) {
                 datas.clear();
            }
            return;
        }
        
        List<String> tmpdatas = new ArrayList<>();
        
        synchronized (obj) {
//            datas.clear();
            for (BlackContent smsContent : contents) {
                if ("1".equals(smsContent.getStatus())) {
                    String tmp = smsContent.getContent();
                    String[] tmp2 = tmp.split(";");
                    if (tmp2 != null && tmp2.length > 0) {
                        for (String blackKey : tmp2) {
                            String key = blackKey.toLowerCase().trim();
                            if (!datas.contains(key)) {
                                datas.add(key);
                            }
                            tmpdatas.add(key);        
                        }
                    } else {
                        String key = tmp.toLowerCase().trim();
                        if (!datas.contains(key)) {
                                datas.add(key);
                            }
                        tmpdatas.add(key); 
                    }
                }
            }
            
            List<String> removedatas = new ArrayList<>();
            for (String currentKey : datas) {
                if(!tmpdatas.contains(currentKey)){
                    removedatas.add(currentKey);
                }
            }
            
            if (!removedatas.isEmpty()) {
                for (String removeKey : removedatas) {
                    datas.remove(removeKey);
                }
            }
            
            logger.info("content black size: " + datas.size() + ": " + datas.toString());
        }
        
    }

    public boolean isBlackList(String signature) {
        boolean result = false;
        String tmp = signature.trim().toLowerCase();
        
        synchronized (obj) {
            if (datas.contains(tmp)) {
                result = true;
                logger.info(result + " black content: " + tmp + ", in " + datas.toString());
                return result;
            }
                
            for (String blackKey : datas) {
                if (tmp.contains(blackKey)
                        || tmp.matches(blackKey)
                        ||  tmp.equals(blackKey)) {
                    result = true;
                    logger.info(result + " black content: " + tmp + ", in " + datas.toString());
                    return result;
                }
            }
        }
        
        logger.info(result + " non black content: " + tmp);
        return result;
    }
    
}
