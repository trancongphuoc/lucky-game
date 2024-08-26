/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.ConstSMS;
import com.nh.GlobalConfig;
import com.nh.backend.bean.CdrLog;
import com.nh.backend.bean.SmsData;
import com.nh.backend.process.InsertCdrProcess;
import com.nh.backend.process.SendSmsProcess;
import com.nh.soap.SoapRequest;
import com.nh.util.CallWS;
import com.nh.util.CountryCode;
import com.nh.util.MyLog;
import com.nh.util.WebserviceTemplate;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;

public class UpointAdd extends AbstractHandler {
     protected final static Logger log = Logger.getLogger(UpointAdd.class);
    public UpointAdd(SoapRequest request) {
        super(request);
    }

    @Override
    public String process(SoapRequest request) {
        String value = "202";
        long startTime = System.currentTimeMillis();
        String msisdn = null;
        String transid = null;
         String amount = null ;
        try {
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1|Method:  " + request.getMethod();
            }
            if(!GlobalConfig.get("white_ip").contains(request.getClientIp())) {
                log.info(request.getClientIp() + " , Modbalance receive: " + request.getRawXml());
                return "1|unknow authen" ;
            }
            log.info(request.getClientIp() + " ,UpointAdd receive: " + request.getRawXml());
            String xml = request.getRawXml();
            CdrLog cdr = new CdrLog();
            cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
//            String msisdn = request.getDatas().get("msisdn");
//            String msisdn = StringUtils.substringBetween(xml, "<msisdn>", "</msisdn>");
           msisdn = request.getValue("msisdn", null);
            msisdn = CountryCode.formatMobile(msisdn);
            if (msisdn== null || !CountryCode.isViettelNumber(msisdn)) {
                log.error(request.getClientIp() + "410|wrong msisdn:  " + msisdn);
                return "410|wrong msisdn:  " + msisdn;
            }
//            String transid = StringUtils.substringBetween(xml, "<transid>", "</transid>");
            transid = request.getValue("transid", null);
            if (transid == null || transid.isEmpty()) {
                transid = UUID.randomUUID().toString();
//                log.error(request.getClientIp() + "410|wrong upoint transid:  " + msisdn );
//                return "410|wrong upoint amount:  " + msisdn;
            } 
             
//            String amount = StringUtils.substringBetween(xml, "<amount>", "</amount>");
            amount = request.getValue("amount", null);
            if (amount == null || amount.isEmpty()) {
                log.error(request.getClientIp() + "410|wrong upoint amount:  " + msisdn + ", amount: " + amount);
                return "410|wrong upoint amount:  " + msisdn;
            } 
            
             Map<String,String> parameters = new HashMap<>();
             parameters.put(CallWS.WS_MSISDN, msisdn);
             parameters.put(CallWS.WS_AMOUNT, amount);
             parameters.put(CallWS.WS_TRANS_ID, transid);
             
            String upointBody = WebserviceTemplate.getInstance().get("upoint_add.json");
            String upointUrl = GlobalConfig.get("upoint_url");
            
            upointBody = CallWS.parseValue(upointBody, parameters);
            
            Map<String,String> headers = new HashMap<>();
            headers.put("User-Name", GlobalConfig.get("upoint_header_user").trim()) ;
            headers.put("Password", GlobalConfig.get("upoint_header_password").trim()) ;
            
//            long startTime = System.currentTimeMillis();
            String jsonResponse = CallWS.callHttpWS(upointUrl, upointBody, 1, CallWS.HTTP_TIMEOUT, CallWS.MIME_JSON, headers);
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                MyLog.Error("call json upoint:" + upointUrl + ",body: " + upointBody+ ",jsonResponse: " + jsonResponse);
            }
            log.info("addUpoint: " + transid + ",msisdn:  " + msisdn + ", amount: " + amount 
                    + " => " + jsonResponse + ", in(ms): " + (System.currentTimeMillis() - startTime));
            String description = null;
            Object valueObject = CallWS.getValueObject(jsonResponse, true);
            if (valueObject == null) {
                MyLog.Error("json convert result: url,body: " + jsonResponse);
                value = "202";
            } else {
                 value = CallWS.getValue(valueObject, "errorCode", "202");
                 description = CallWS.getValue(valueObject, "description", null);
            }
            if ("0".equals(value)) {
                String content = ConstSMS.get("reward_upoint");
                if (content != null && !content.isEmpty()) {
                    content = CallWS.parseValue(content, parameters) ;
                    MyLog.Infor("let send content to: " + msisdn + "=>" + content);
                    sendMt(content, msisdn, transid);
                }
            }
            
            log.info("addUpoint: " + transid  + ",msisdn:  " + msisdn + ", amount: " + amount + " => status: " + value+ " , description : " + description
            + " , in (ms) : " + (System.currentTimeMillis() - startTime ));

            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = CountryCode.formatMobile(msisdn);
            cdr.TRANS_ID = transid;
            cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());;
            cdr.RESPONSE_CODE = value;
            if (cdr.RESPONSE_CODE != null && cdr.RESPONSE_CODE.length() > 20) {
                cdr.RESPONSE_CODE = cdr.RESPONSE_CODE.substring(0, 19);
            }
            cdr.SERVICE_NAME = GlobalConfig.get("mps.service");
            cdr.SUB_SERVICE_NAME = GlobalConfig.get("mps.sub_service");
            cdr.CONTENTS = description;
            cdr.CMD = "MODBALANCEUPOINT";
            if (amount != null && !amount.isEmpty()) {
                cdr.PRICE = Double.valueOf(amount);
            } else {
                cdr.PRICE = 0;
            }

            cdr.CHANNEL = "UPOINT";
            InsertCdrProcess.getInstance().enqueue(cdr);
        } catch (Exception ex) {
            log.error("error when excute call upoint", ex);
            value = "1|" + ex.toString();
        }
        log.info("addUpoint: " + transid + ",msisdn:  " + msisdn + ", amount: " + amount 
                    + " => " + value + ", in(ms): " + (System.currentTimeMillis() - startTime));
        return value;
    }

    public boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
    
    private void sendMt(String content, String msisdn, String transId) {
        if (content == null || content.trim().isEmpty()) {
             log.error("sms content null: " + msisdn + "|" + content);
            return;
        }
        String url = GlobalConfig.getSmsUrl();
        String user = GlobalConfig.getSmsUsername();
        String pass = GlobalConfig.getSmsPassword();
        String shortCode = GlobalConfig.getSmsShortcode();
        String alias = GlobalConfig.getSmsAlias();
        
        log.info("enqueue SMS msisdn: " + msisdn + "|" + content);
        SmsData smsData = new SmsData(url, user, pass, msisdn, content, shortCode, alias, "HEX_TEXT");
        SendSmsProcess.getInstance().enqueue(smsData);
    }
}
