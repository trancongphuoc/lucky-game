/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.cdr;

import com.nh.GlobalConfig;
import com.nh.bean.CreditCondition;
import com.nh.database.DB;
import com.nh.repository.CPWebserviceDao;
import com.nh.repository.CreditConditionDao;
import com.nh.util.CallWS;
import com.nh.util.CountryCode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;

public class AdvertiseProcessWorker implements Runnable {

    private final static int DEFAULT_SLEEP = 1;

    private final static Logger log = Logger.getLogger(AdvertiseProcessWorker.class);

    CreditConditionDao conditionDao = new CreditConditionDao();
    CPWebserviceDao webserviceDao = new CPWebserviceDao();
    
    int maxAdvertis ;
    int channelParam ;
   
    int threadIndex;
    
    private boolean trialEnable = false;
    
    public AdvertiseProcessWorker(int index) {
        maxAdvertis = GlobalConfig.getInt("advertis_max", 0);
        channelParam = GlobalConfig.getInt("advertis_ivr", 0);
        
        this.threadIndex = index;
        trialEnable = GlobalConfig.getInt("trial_enable", 0) > 0;
    }

    public void start() {
        Thread t = new Thread(this);
        t.setName("thread-" + AdvertiseProcessWorker.class.getSimpleName() + threadIndex);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msisdn = AdvertiseProcess.getInstance().dequeue();
                if (msisdn == null) {
                    Thread.sleep(1000);
                    continue;
                }
                
                process(msisdn);
                Thread.sleep(DEFAULT_SLEEP);
            } catch (Exception ex) {
                log.error("error processing Advertis", ex);
            }
        }
    }

    private void process(String msisdn) {
        try {
            String transId = UUID.randomUUID().toString();
            if (trialEnable) {
                int i = DB.queryForInt(GlobalConfig.get("trial_sql_check"), msisdn);
                if (i <= 0) {
                    log.info("not advertis not trial user: " + msisdn);
                    return;
                }
                log.info("trial user: " + msisdn);
            }
            
            if (maxAdvertis > 0) {
                int i = DB.queryForInt(GlobalConfig.get("advertis_check"), msisdn);
                if (i >= maxAdvertis) {
                    log.info("not advertis > max in time: " + msisdn);
                    return;
                }
                log.info("ok < max time advertis: " + msisdn);
            }

            List<CreditCondition> ocsConditions = conditionDao.findByType(CreditCondition.TYPE_OCS_REGISTER);
            
            if (ocsConditions != null && !ocsConditions.isEmpty()) {
                Map<String, String> ocsViews = CallWS.viewOcs(CountryCode.getCountryCode() + msisdn, transId);
                log.info("view ocs for: " + msisdn);
                boolean ocsValid = conditionDao.validCondition(ocsConditions, ocsViews);
                if (!ocsValid) {
                    log.info("No Advantise, invalid ocs check: " + msisdn);
                    return;
                }
            }

            log.info("Advantise: " + msisdn);
            AdvertiseProcess.getInstance().enqueueAdvertis(msisdn);
            
        } catch (Exception ex) {
            log.error("error insert in Exception to DB", ex);
        }
    }

   

}
