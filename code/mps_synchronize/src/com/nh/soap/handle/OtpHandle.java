/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.backend.bean.CdrLog;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import com.nh.util.EncryptUtil;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 *
 * @author 
 */
public class OtpHandle  extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(OtpHandle.class);
    public OtpHandle(SoapRequest request) {
        super(request);
    }
    
    @Override
    public String process(SoapRequest request) {
        String value = "0";
        try {
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1|Method:  " + request.getMethod();
            }
            log.info(request.getClientIp() + " ,OtpHandle receive: " + request.getRawXml());
//            String xml = request.getRawXml();
            CdrLog cdr = new CdrLog();
            cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
            String msisdn = request.getValue("msisdn", null);
            if (msisdn == null || msisdn.trim().isEmpty()) {
                log.info(request.getClientIp() + " ,receive unknow OtpHandle: " + msisdn);
                return "1|invalid parameter";
            }
            msisdn = CountryCode.formatMobile(msisdn);
            if (!CountryCode.isViettelNumber(msisdn)) {
                log.error("410|wrong msisdn:  " + msisdn);
                return "410|wrong msisdn:  " + msisdn;
            }
            String cmd = request.getValue("cmd", "REGISTER");
            String otpType = request.getValue("otpType", "0");
            String otp = request.getValue("otp", "");
            
            if (!"1".equals(otpType) && !"0".equals(otpType)) {
                log.error(request.getClientIp() + " ,receive unknow otpType: " + otpType);
                return "1|invalid parameter";
            }
            
            if ("1".equals(otpType) ) {
                if (otp == null || otp.isEmpty() || otp.trim().length() > 6) {
                    log.error(request.getClientIp() + " ,receive unknow otp: " + otp);
                    return "1|invalid parameter";
                }
                otp = otp.trim();
                try {  
                    Double.parseDouble(otp);  
                } catch(NumberFormatException e){  
                  return "1|invalid parameter";  
                }  
            }
                
            String sub_service = request.getValue("sub_service", GlobalConfig.get("mps.sub_service"));
//            String transid = StringUtils.substringBetween(xml, "<transid>", "</transid>");
            String transid = request.getValue("transid", UUID.randomUUID().toString());
            String SER = request.getValue("service", GlobalConfig.get("mps.service"));
            String PRO = request.getValue("provider",GlobalConfig.get("mps.provider"));

            String amount = request.getValue("amount", "0");
            if (amount == null || amount.isEmpty()) {
                amount = "0";
            } 
//            String cate = "BLANK";
//            String cate = StringUtils.substringBetween(xml, "<cate>", "</cate>");
            String cate = request.getValue("cate", "BLANK");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//            String REQ = "999" + dateFormat.format(new Date());
            String SESS = "998" + dateFormat.format(new Date());
            
            Map<String,String> moreParams = new HashMap<>();
            String award = request.getValue("award_code", null);
            if (award != null && !award.isEmpty()) {
                moreParams.put("AWARD_CODE", award);
            }
            
            if (otpType != null && !otpType.isEmpty()) {
                moreParams.put("OTP_TYPE", otpType);
            }
            
            if (otp != null && !otp.isEmpty()) {
                moreParams.put("OTP", otp);
            }
            Map<String, String> responseValues = new HashMap<>();
            value = EncryptUtil.sendRequestToMps(GlobalConfig.get("mps_url_otp"), PRO, SER, sub_service,
                    msisdn, cmd, amount,
                    sub_service + "|" + cmd, transid, SESS,
                    cate, sub_service, moreParams, responseValues);
            if (!responseValues.isEmpty()) {
                amount = responseValues.get("PRICE");
            }
            
            
            
            String item = null ;
            String content = null;
            
            if ("REGISTER".equals(cmd) ) {
                String sub_new = responseValues.get("SUB_NEW");
                if (sub_new == null) {
                    sub_new = "false";
                }
                item = sub_new ;
                content = "sub_new: " + sub_new ;
            }
                    
            log.info("command: " + cmd + ",msisdn:  " + msisdn +  ", PRICE:  " + amount + " => status: " + value);

            if ("REGISTER".equals(cmd) && value.startsWith("408")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
                amount = "0";
            }

            if ("CANCEL".equals(cmd) && value.startsWith("411")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
            }

            if ("CANCEL".equals(cmd) && value.startsWith("414")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
            }

            if ("RESTORE".equals(cmd) && value.startsWith("408")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
//                amount = "0";
            }

            if (value.startsWith("0")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
            }

            if (value.startsWith("401")) {
                log.info("convert code: " + value + "=> 401, for cmd: " + cmd);
                value = "401";
            }

            if (value.startsWith("408")) {
                log.info("convert code: " + value + "=> 408, for cmd: " + cmd);
                value = "408";
                amount = "0";
            }
            
            if (value.startsWith("100")) { 
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
            }
            
            DatabaseConnectionPool.getInstance().getConnection().updateOtpHandle(msisdn,sub_service , cmd,
                    value, otpType, cate, amount, transid, item, content);

//            cdr.ID = UUID.randomUUID().toString();
//            cdr.MSISDN = CountryCode.formatMobile(msisdn);
//            cdr.TRANS_ID = transid;
//            cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());;
//            cdr.RESPONSE_CODE = value;
////            if (cdr.RESPONSE_CODE != null && cdr.RESPONSE_CODE.length() > 20) {
////                cdr.RESPONSE_CODE = cdr.RESPONSE_CODE.substring(0, 19);
////            }
//            cdr.SERVICE_NAME = SER;
//            cdr.SUB_SERVICE_NAME = sub_service;
//
//            cdr.CMD = cmd;
//            if (!amount.isEmpty()) {
//                cdr.PRICE = Double.valueOf(amount);
//            } else {
//                cdr.PRICE = 0;
//            }
//
//            cdr.CHANNEL = "OTP";
//            InsertCdrProcess.getInstance().enqueue(cdr);
        } catch (Exception ex) {
            log.error("error when excute rsa to mps", ex);
            value = "1|" + ex.toString();
        }

        return value;
    }
}
