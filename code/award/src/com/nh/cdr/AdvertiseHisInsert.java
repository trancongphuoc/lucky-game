/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.cdr;


import com.nh.bean.AdvertiseHis;
import com.nh.repository.AdvertiseHisDao;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author hathutt
 */
public class AdvertiseHisInsert implements Runnable{
     /**
     * thong thuong batch size se phai lon hon TPS (mac dinh TPS = 200/node)
     * day la ung dung lon nen de batch size an toan la 500 ~ max TPS
     * nen sau moi lan xu ly he thong se nghi: dua tren feedback phan hoi. max TPS/size of data
     */
    
    private final static int BATCH_SIZE = 500;
    private final static int DEFAULT_SLEEP = 5000;
    
    private final LinkedList<AdvertiseHis> queue = new LinkedList<AdvertiseHis>();
    private final Object obj = new Object();
    
    private static AdvertiseHisInsert _instance = null;
    
    private final static Logger log = Logger.getLogger(AdvertiseHisInsert.class);
    private final AdvertiseHisDao advertiseHisDao ;
            
    private AdvertiseHisInsert() {
       advertiseHisDao = new AdvertiseHisDao();
    }
    
    public synchronized static AdvertiseHisInsert getInstance() {
        if (_instance == null) {
            _instance = new AdvertiseHisInsert();
            Thread t = new Thread(_instance);
            t.setName("thread-" + AdvertiseHisInsert.class.getSimpleName() + ((new Random()).nextInt()%100));
            t.start();
        }
        return _instance;
    }
   
    public void enqueue(AdvertiseHis transaction) {
        synchronized (obj) {
            queue.add(transaction);
            log.info("enqueue queue("+queue.size()+"): " + transaction);
        }
    }

    private  List<AdvertiseHis> dequeue() {
        synchronized (obj) {
            if (queue.isEmpty()) {
                return null;
            }
            List<AdvertiseHis> datas = new LinkedList<AdvertiseHis>();
            int i = 0;
            while (queue.size() > 0 && (i < BATCH_SIZE)) {
                AdvertiseHis transaction = queue.poll();
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
                List<AdvertiseHis> datas = dequeue();
                if (datas == null) {
                     Thread.sleep(DEFAULT_SLEEP);
                     continue;
                }
                
                process(datas);
                int length = datas.size();
                log.info("AdvertiseHisInserDao processed bacth queue = " + length);
                datas.clear();
                datas = null;

                int sleep = 1000 * BATCH_SIZE / length; //m s
                if (sleep > DEFAULT_SLEEP) {
                    sleep = DEFAULT_SLEEP;
                }
                Thread.sleep(sleep);
            } catch (Exception ex) {
                 log.error("error processing Busycall log", ex);
            }
        }
    }
     
    private void process(List<AdvertiseHis> datas) {
        try {
            advertiseHisDao.saveBatch(datas);
        } catch (Exception ex) {
            log.error("error insert in AdvertiseHis to DB", ex);
        } 

    }

   
}

