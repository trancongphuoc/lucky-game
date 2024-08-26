/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import com.nh.util.EncryptUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 *
 * @author ac
 */
public class Authen extends AbstractHandler{
    protected final static Logger log = Logger.getLogger(Authen.class);
    public Authen(SoapRequest request) {
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
            log.info(request.getClientIp() + " ,Authen receive: " + request.getRawXml());
//            String xml = request.getRawXml();
            String msisdn = request.getValue("msisdn", null);
            if (msisdn == null || msisdn.trim().isEmpty()) {
                log.info(request.getClientIp() + " ,receive unknow : " + msisdn);
                return "1|invalid parameter";
            }
            msisdn = CountryCode.formatMobile(msisdn);
            String cmd = request.getValue("cmd", "VIEW");
            
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
            
            value = EncryptUtil.sendRequestToMps(GlobalConfig.get("mps_url_authen"), PRO, SER, sub_service,
                    msisdn, cmd, amount,
                    sub_service + "|" + cmd, transid, SESS,
                    cate, sub_service, moreParams);
            log.info("command: " + cmd + ",msisdn:  " + msisdn + " => status: " + value);
            
            //205 MPS acount is exist and registed CP service
            if (value.startsWith("205")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "1";
            } else if (value.startsWith("204")) {
                log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                value = "0";
            }

        } catch (Exception ex) {
            log.error("error when excute rsa to mps", ex);
            value = "1|" + ex.toString();
        }

        return value;
    }
}
