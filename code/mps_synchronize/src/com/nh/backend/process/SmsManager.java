/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.SmsContent;
import com.nh.backend.repository.SmsContentDao;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class SmsManager implements Runnable {

    public static Logger logger = Logger.getLogger(SmsManager.class);
    private final static int DEFAULT_SLEEP = 10 * 60000;
    private final List<SmsContent> datas = new ArrayList<>();

    private final Object obj = new Object();
    private static SmsManager _instance = null;

    private SmsManager() {
    }

    public static synchronized SmsManager getInstance() {
        if (_instance == null) {
            _instance = new SmsManager();
            Thread thread = new Thread(_instance);
            thread.setDaemon(false);
            thread.setName("SmsManager-");
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
        SmsContentDao contentDao = new SmsContentDao();
        List<SmsContent> contents = contentDao.getAll();
        if (contents.isEmpty()) {
            return;
        }
        
        synchronized (obj) {
            datas.clear();
            for (SmsContent smsContent : contents) {
                if ("1".equals(smsContent.getStatus())
                        && smsContent.getContent().length() < 135) {
                    datas.add(smsContent);
                }
            }
        }
    }

    public List<SmsContent> getRandom(int num) {
        List<SmsContent> contents = new LinkedList<>();
        synchronized (obj) {
            int length = num > datas.size() ? datas.size() : num;
            for (int i = 0; i < length; i++) {
                contents.add(datas.get(i));
            }
        }

        return contents;
    }

    /**
     * @param min
     * @param max
     * @return x, with min <= x <= max
     */
    public int randInt(int min, int max) {
        Random rand = new Random();
//        int randomNum = rand.nextInt((max - min) + 1) + min;
        int randomNum = rand.nextInt(max);
        if (randomNum >= max) {
            randomNum = randomNum % max;
        }
        return randomNum;
    }
    
}
