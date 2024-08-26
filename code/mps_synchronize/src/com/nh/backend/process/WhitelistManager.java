/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.process;

import com.nh.backend.bean.WhiteList;
import com.nh.backend.repository.WhiteListDao;
import com.nh.util.CountryCode;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class WhitelistManager implements Runnable {

    public static Logger logger = Logger.getLogger(WhitelistManager.class);
    private final static int DEFAULT_SLEEP = 10000;
    private final List<String> datas = new ArrayList<>();

    private final Object obj = new Object();
    private static WhitelistManager _instance = null;

    private WhitelistManager() {
    }

    public static synchronized WhitelistManager getInstance() {
        if (_instance == null) {
            _instance = new WhitelistManager();
            Thread thread = new Thread(_instance);
            thread.setDaemon(false);
            thread.setName("WhitelistManager-");
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
                logger.error("error load Whitelist", ex);
            }
        }
    }

    private void process() {
        WhiteListDao contentDao = new WhiteListDao();
        List<WhiteList> contents = contentDao.getAll();
        synchronized (obj) {
            datas.clear();
            for (WhiteList smsContent : contents) {
                if ("1".equals(smsContent.getStatus())) {
                    String tmp = smsContent.getMsisdn();
                    tmp = CountryCode.formatMobile(tmp);
                    datas.add(tmp.trim());
                }
            }
        }
    }

    public boolean isWhitelist(String msisdn) {
        String tmp = msisdn.trim().toLowerCase();
        synchronized (obj) {
            if (datas == null || datas.isEmpty()) {
                return true;
            }
            
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
