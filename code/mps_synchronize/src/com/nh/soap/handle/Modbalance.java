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
import org.apache.log4j.Logger;

/**
 *
 * @author abc
 */
public class Modbalance  extends AbstractHandler{
    protected final static Logger log = Logger.getLogger(Modbalance.class);
     public Modbalance(SoapRequest request) {
        super(request);
    }

    @Override
    public String process(SoapRequest request) {
        String value = "202";
        try {
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1|Method:  " + request.getMethod();
            }
            if(!GlobalConfig.get("white_ip").contains(request.getClientIp())) {
                log.info(request.getClientIp() + " , Modbalance receive: " + request.getRawXml());
                return "1|unknow authen" ;
            }
            log.info(request.getClientIp() + " ,Modbalance receive: " + request.getRawXml());
//            String xml = request.getRawXml();
            String msisdn = request.getValue("msisdn", null);
            if (msisdn == null || msisdn.trim().isEmpty()) {
                log.info(request.getClientIp() + " ,receive unknow : " + msisdn);
                return "1|invalid parameter";
            }
            msisdn = CountryCode.formatMobile(msisdn);
            String cmd = request.getValue("cmd", "MODBALANCE");
            
            String sub_service = request.getValue("sub_service", GlobalConfig.get("mps.sub_service_bonus"));
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
            
            Map<String,String> responseValues = new HashMap<>();
            String url = GlobalConfig.get("mps_url");
            String abcValue = EncryptUtil.sendRequestToMps(url, PRO, SER, sub_service,
                    msisdn, cmd, amount,
                    sub_service + "|" + cmd, transid, SESS,
                    cate, sub_service, moreParams, responseValues);
            
            if (abcValue.startsWith("0")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
            }
            
            log.info("command: " + cmd + ",msisdn:  " + msisdn + " => status: " + value);
            
            String mpsRequestId = responseValues.get("REQ");
            String price = responseValues.get("PRICE");
            
            CdrLog cdr = new CdrLog();
            cdr.setVCGW_REQUEST_ID(mpsRequestId);
            
            cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = CountryCode.formatMobile(msisdn);
            cdr.TRANS_ID = transid;
            cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());;
            cdr.RESPONSE_CODE = value;
            cdr.SERVICE_NAME = SER;
            cdr.SUB_SERVICE_NAME = sub_service;
            cdr.setCONTENTS(abcValue);
            
            cdr.CMD = cmd;
            if (price != null && !price.isEmpty()) {
                cdr.PRICE = Double.valueOf(price);
            } else {
                cdr.PRICE = 0;
            }

            cdr.CHANNEL = "CLIENT";
            InsertCdrProcess.getInstance().enqueue(cdr);
        } catch (Exception ex) {
            log.error("error when excute rsa to mps", ex);
            value = "1|" + ex.toString();
        }

        return value;
    }
}
