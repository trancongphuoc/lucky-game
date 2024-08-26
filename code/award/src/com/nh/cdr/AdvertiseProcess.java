/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.cdr;

import com.nh.GlobalConfig;
import com.nh.repository.CPWebserviceDao;
import com.nh.repository.CreditConditionDao;
import com.nh.util.CommonUtil;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;

public class AdvertiseProcess implements Runnable {

    private final static int DEFAULT_SLEEP = 1;

    private final LinkedList<String> queue = new LinkedList<String>();

    private final LinkedList<String> queueUssdout = new LinkedList<String>();

    private final Object lock = new Object();

    private static AdvertiseProcess _instance = null;

    private final static Logger log = Logger.getLogger(AdvertiseProcess.class);
    private int interval = 5000; //5 giay
    private int advertiesNumber = 1; //sleep giay quang cao 1 so dien thoai
    private long lastReset = 0;

    private final Set<String> advertiseHours;
    private int countAdvertise = 0;

    
     
    private AdvertiseProcess() {
        lastReset = System.currentTimeMillis();
        interval = GlobalConfig.getAdvertiseInterval();
        advertiesNumber = GlobalConfig.getAdvertiesNumber();
        String tmAdvertisHour = GlobalConfig.getAdvertiseHour();
        advertiseHours = CommonUtil.convertHashSet(tmAdvertisHour);
    }

    public synchronized static AdvertiseProcess getInstance() {
        if (_instance == null) {
            _instance = new AdvertiseProcess();
            Thread t = new Thread(_instance);
            t.setName("thread-" + AdvertiseProcess.class.getSimpleName() + ((new Random()).nextInt() % 10));
            t.start();
            for (int i = 0; i < 10; i++) {
                AdvertiseProcessWorker worker = new AdvertiseProcessWorker(i);
                worker.start();
            }
            
            for (int i = 0; i < 10; i++) {
                SendAdvertise worker = new SendAdvertise();
                worker.start(i);
            }
        }
        return _instance;
    }

    public void enqueue(String msisdn) {
        synchronized (lock) {
            queue.add(msisdn);
            log.info("Advantise enqueue(" + queue.size() + "): " + msisdn);
        }
    }

    public String dequeue() {
        synchronized (lock) {
            if (queue.isEmpty()) {
                return null;
            }
            String msisdn = queue.poll();
            log.info("Advantise dequeue(" + queue.size() + "): " + msisdn);
            return msisdn;
        }
    }
    
    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
    
    private final Object lockOut = new Object();
    public void enqueueAdvertis(String msisdn) {
        synchronized (lockOut) {
            queueUssdout.add(msisdn);
            log.info("Advantise enqueueUssdout(" + queueUssdout.size() + "): " + msisdn);
        }
    }
    
    public String dequeueAdvertis() {
        synchronized (lockOut) {
            if (queueUssdout.isEmpty() || !isInSentTime()) {
                return null;
            }
            String msisdn = queueUssdout.poll();
            log.info("Advantise dequeueUssdout(" + queueUssdout.size() + "): " + msisdn);
            return msisdn;
        }
    }
    
    public int sizeAdvertis() {
        synchronized (lockOut) {
            return queueUssdout.size();
        }
    }
    
    public boolean isInSentTime() {
        if (advertiseHours == null || advertiseHours.isEmpty()) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);

        return advertiseHours.contains(String.valueOf(hour));
    }

    public synchronized boolean isOverTPS() {
        long duration = System.currentTimeMillis() - lastReset;
        if (duration > interval) {
            countAdvertise = 0;
            lastReset = System.currentTimeMillis();
        }

        if (countAdvertise > advertiesNumber) {
            //vuat qua tps can thiet
            log.info("tps over:  " + countAdvertise + "/" + duration  
                    + ", max: "  + advertiesNumber + "/" + interval );
            return true;
        }

        countAdvertise++;
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                log.error("error processing Advertis", ex);
            }
        }
    }

    public boolean isReady() {
        return isInSentTime() && size() == 0 && sizeAdvertis() == 0;
    }

}
