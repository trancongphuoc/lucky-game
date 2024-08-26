/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 *
 * @author HALT14
 * @since 12/9/2015
 * @version 1.0
 */
public class SmsGwUtils {

    private static long sessionId = 0L;
    private static long MAX_SESSION_ID = 99999999L;
    private MtStub stub;
    private String username;
    private String password;
    private String url;
    private String xml;
    private String serviceId;
    private String status;
    private String contentType;
    private int maxRetry;
    private String shortCode;
        
    private static Logger log = Logger.getLogger(SmsGwUtils.class);
    private static SmsGwUtils instance;

    public static synchronized SmsGwUtils getInstance() {
        if (instance == null) {
            instance = new SmsGwUtils();
        }
        return instance;
    }

    public SmsGwUtils() {
        loadSmsGwInfor();

    }

    public void loadSmsGwInfor() {
        try {

            url = Config.getInstance().getOtpUrl();
         //   smsUrl = Config.getInstance().getSmsUrl();
            //   username = PassTranformer.decrypt(Config.getInstance().getOtpUser());
            //    password = PassTranformer.decrypt(Config.getInstance().getOtpPass());
            username = Config.getInstance().getOtpUser();
            password = Config.getInstance().getOtpPass();
            xml = Config.getInstance().getOtpXml();
            serviceId = Config.getInstance().getOtpServiceId();
            status = Config.getInstance().getOtpStatus();
            contentType = Config.getInstance().getOtpContentType();
            maxRetry = Config.getInstance().getOtpRetry();
            shortCode = Config.getInstance().getOtpSender();
            stub = new MtStub(url, xml, username, password);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


    public synchronized String getSessionId() {
        sessionId++;
        if (sessionId > MAX_SESSION_ID) {
            sessionId = 1;
        }
        String str = "" + sessionId;
        if (str.length() < 8) {
            for (int i = 0; str.length() < 8; i++) {
                str = "0" + str;
            }
        }
        return "S" + str;
    }

    public int sendMt(String msisdn, String content) {
        int retry = 0;
        int error;
        do {
            error = stub.send(getSessionId(), serviceId, shortCode, msisdn,
                    contentType, content, status);
            log.info("Errorcode from webservice: " + error);
            if (error != 0) {
                retry++;
            }
        } while (retry < maxRetry && (error != 0));

        return error;
    }
    
   

    public void sendMtUnicode(String msisdn, String content) {
        try {

            if (content == null) {
                return;
            }
            if (!msisdn.startsWith(Config.getInstance().getOtpPrefix())) {
                msisdn = Config.getInstance().getOtpPrefix() + msisdn;
            }
            log.info("SendMt >> " + msisdn + ":" + content);
            byte[] tmp = null;
            try {
                tmp = content.getBytes("UTF-16BE");
             
//            message = Hex.encode(tmp);
            } catch (UnsupportedEncodingException ex) {
                log.error(ex.getMessage(), ex);
            }
            int retry = 0;
            int error;
            do {
                error = stub.send(getSessionId(), serviceId, shortCode, msisdn,
                        contentType, tmp, status);
                log.info("Errorcode from webservice: " + error);
                if (error != 0) {
                    retry++;
                }
            } while (retry < maxRetry && (error != 0));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
