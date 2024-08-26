/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.cdr;

import com.nh.GlobalConfig;
import com.nh.bean.AdvertiseHis;
import com.nh.bean.CPWebservice;
import com.nh.bean.SmsData;
import com.nh.repository.CPWebserviceDao;
import com.nh.repository.CreditConditionDao;
import com.nh.util.CallWS;
import com.nh.util.CommonUtil;
import com.nh.util.CountryCode;
import com.nh.util.SoapClient;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import org.apache.log4j.Logger;

public class SendAdvertise implements Runnable {

    private final static int DEFAULT_SLEEP = 1;

    private static SendAdvertise _instance = null;

    private final static Logger log = Logger.getLogger(SendAdvertise.class);
    private int interval = 5000; //5 giay
    private int advertiesNumber = 1; //sleep giay quang cao 1 so dien thoai
    private long lastReset = 0;

    private final Set<String> advertiseHours;
    private int countAdvertise = 0;

    CreditConditionDao conditionDao = new CreditConditionDao();
    CPWebserviceDao webserviceDao = new CPWebserviceDao();
    
    int maxAdvertis ;
    int channelParam ;
    
    private final String smsAdvertis ;
    private final String smsUrl;
    private final String smsUser;
    private final String smsPass;
    private final String smsShortCode;
    private final String smsAlias;
    private int smsWait = 45000;
     
    public SendAdvertise() {
        lastReset = System.currentTimeMillis();
        interval = GlobalConfig.getAdvertiseInterval();
        advertiesNumber = GlobalConfig.getAdvertiesNumber();
        String tmAdvertisHour = GlobalConfig.getAdvertiseHour();
        advertiseHours = CommonUtil.convertHashSet(tmAdvertisHour);
        maxAdvertis = GlobalConfig.getInt("advertis_max", 0);
        channelParam = GlobalConfig.getInt("advertis_ivr", 0);
        
        smsAdvertis = GlobalConfig.getSmsAdvertis();
         smsUrl = GlobalConfig.getSmsUrl();
         smsUser = GlobalConfig.getSmsUsername();
         smsPass = GlobalConfig.getSmsPassword();
         smsShortCode = GlobalConfig.getSmsShortcode();
         smsAlias = GlobalConfig.getSmsAlias();
         smsWait = GlobalConfig.getInt("sms_advertise_wait", 45000);
    }

    public synchronized  void start(int index) {
        Thread t = new Thread(this);
        t.setName("thread-" + SendAdvertise.class.getSimpleName() + index);
        t.start();
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

    private boolean isOverTPS() {
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
                String msisdn = AdvertiseProcess.getInstance().dequeueAdvertis();
                if (msisdn != null) {
                    sendAdvertis(msisdn);
                }

                Thread.sleep(1);
            } catch (Exception ex) {
                log.error("error processing Advertis", ex);
            }
        }
    }

    private void sendAdvertis(String msisdn) {
        boolean isUssd = true;
        
        if (channelParam == 0) {
            isUssd = true;
            log.info("Advantise ussd for: " + msisdn);
        } else if (channelParam == 1) {
            isUssd = false;
            log.info("Advantise ivr for: " + msisdn);
        } else if (channelParam == 2) {
            isUssd = isOverTPS();
            log.info("Advantise auto swicth: " + msisdn);
        } else if (channelParam == 3) {
            boolean isOver = isOverTPS();;
            isUssd = false;
            if (isOver){
                log.info("Advantise reject overTps: " + msisdn);
                return;
            }
            
            log.info("Advantise ivr: " + msisdn);
        }
        
        long startTime = System.currentTimeMillis();
        // khong no data
        String channel = isUssd? "USSD" : "IVR";
        log.info("Advantise chanel: " +channel + ", for: " + msisdn);
        String content = isUssd? GlobalConfig.get("ussd_content_invite") : "IVR";
        
        if (!isUssd) {
            log.info("advertise, ivr callout: " + msisdn );
            //ivr
//            String redisContent = msisdn + "_on1_promotion1";
        } else {
            String wsName = GlobalConfig.getWsUssdInvite();
            log.info("sendAdvertis, channel: " + channel + ", msisdn: " + msisdn + ", wsname: " + wsName);
            CPWebservice ws = webserviceDao.findByName(wsName);
            if (ws == null) {
                log.error("cannot ws add ussdinvite: " + GlobalConfig.getWsUssdInvite());
                log.info("No Advantise, because ws not found: " + msisdn);
                return;
            }
            log.info("sendAdvertis for: " + msisdn);
            String tranID = "A-" + UUID.randomUUID().toString();

            Properties pars = new Properties();
            pars.put(CallWS.WS_MSISDN, CountryCode.getCountryCode() + msisdn);
            pars.put(CallWS.WS_TRANS_ID, tranID);
            pars.put(CallWS.WS_COMMAND, content);

            
            SoapClient client = new SoapClient(ws.getUrl().trim());
            String inputXML = CallWS.parseValue(ws.getRawXML(), pars);
            Map<String, String> responses = client.sendRequest(inputXML);
            String response = responses.get(ws.getReturnTag());
            long durationTime = System.currentTimeMillis() - startTime;
            log.info("sendAdvertis finish: " + msisdn + ", response: " + response + ", duration (ms): " + durationTime);
        }

        SmsData sms = new SmsData(smsUrl, smsUser, smsPass, msisdn, smsAdvertis, smsShortCode, smsAlias, "TEXT");
        sms.setSendTime(System.currentTimeMillis() + smsWait);
        SendSmsProcess.getInstance().enqueue(sms);
        
        long durationTimex = System.currentTimeMillis() - startTime;
        AdvertiseHis advertiseHis = new AdvertiseHis();
        advertiseHis.setMsisdn(msisdn);
        advertiseHis.setChannel(channel);
        advertiseHis.setContent(content);
        AdvertiseHisInsert.getInstance().enqueue(advertiseHis);
        
        log.info("sendAdvertis finish: " + msisdn + ", duration (ms): " + durationTimex);
    }

}
