/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.backend.bean.CdrLog;
import com.nh.backend.process.InsertCdrProcess;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import com.nh.util.EncryptUtil;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class RsaHandler extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(RsaHandler.class);
    public RsaHandler(SoapRequest request) {
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
            log.info(request.getClientIp() + " ,RsaHandler receive: " + request.getRawXml());
            String xml = request.getRawXml();
            CdrLog cdr = new CdrLog();
            cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
//            String msisdn = request.getDatas().get("msisdn");
            String msisdn = StringUtils.substringBetween(xml, "<msisdn>", "</msisdn>");
            msisdn = CountryCode.formatMobile(msisdn);
            if (!CountryCode.isViettelNumber(msisdn)) {
                log.info("410|wrong msisdn:  " + msisdn);
                return "410|wrong msisdn:  " + msisdn;
            }
            String cmd = StringUtils.substringBetween(xml, "<cmd>", "</cmd>");

            String sub_service = StringUtils.substringBetween(xml, "<sub_service>", "</sub_service>");
            String transid = StringUtils.substringBetween(xml, "<transid>", "</transid>");
            String SER = StringUtils.substringBetween(xml, "<service>", "</service>");
            if (SER == null || SER.isEmpty()) {
                SER = GlobalConfig.get("mps.service");
            }

            if (sub_service == null || sub_service.isEmpty()) {
                sub_service = GlobalConfig.get("mps.sub_service");
            }

            String PRO = request.getValue("provider", null);
            if (PRO == null || PRO.isEmpty()) {
                PRO = GlobalConfig.get("mps.provider");
            }

            String amount = StringUtils.substringBetween(xml, "<amount>", "</amount>");
            if (amount == null || amount.isEmpty()) {
                amount = "0";
            } 
//            String cate = "BLANK";
            String cate = StringUtils.substringBetween(xml, "<cate>", "</cate>");
            if (cate == null || cate.isEmpty()) {
                cate = "BLANK";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//            String REQ = "999" + dateFormat.format(new Date());
            String SESS = "998" + dateFormat.format(new Date());
            
            Map<String,String> moreParams = new HashMap<>();
            String award = StringUtils.substringBetween(xml, "<award_code>", "</award_code>");
            if (award != null && !award.isEmpty()) {
                moreParams.put("AWARD_CODE", award);
            }
            
            Map<String, String> responseValues = new HashMap<>();
            
            value = EncryptUtil.sendRequestToMps(null, PRO, SER, sub_service,
                    msisdn, cmd, amount,
                    sub_service + "|" + cmd, transid, SESS,
                    cate, sub_service, moreParams, responseValues);
            
            if (!responseValues.isEmpty()) {
                amount = responseValues.get("PRICE");
            }
            
            String sub_new = responseValues.get("SUB_NEW");
            if (sub_new == null) {
                sub_new = "false";
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
                amount = "0";
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

            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = CountryCode.formatMobile(msisdn);
            cdr.TRANS_ID = transid;
            cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());;
            cdr.RESPONSE_CODE = value;
            if (cdr.RESPONSE_CODE != null && cdr.RESPONSE_CODE.length() > 20) {
                cdr.RESPONSE_CODE = cdr.RESPONSE_CODE.substring(0, 19);
            }
            cdr.SERVICE_NAME = SER;
            cdr.SUB_SERVICE_NAME = sub_service;

            cdr.CMD = cmd;
            if (amount != null && !amount.isEmpty()) {
                cdr.PRICE = Double.valueOf(amount);
            } else {
                cdr.PRICE = 0;
            }
            
            if ("REGISTER".equals(cmd)) {
                cdr.ITEM_NAME = sub_new +"";
                cdr.setCONTENTS("sub_new: " + sub_new);
            }
           
            cdr.CHANNEL = "SYS";
            InsertCdrProcess.getInstance().enqueue(cdr);
        } catch (Exception ex) {
            log.error("error when excute rsa to mps", ex);
            value = "1|" + ex.toString();
        }

        return value;
    }

    public boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
}
