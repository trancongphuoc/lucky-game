/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.backend.bean.CdrLog;
import com.nh.backend.process.InsertCdrProcess;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

/**
 *
 * <
 * soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
 * xmlns:soap="http://soap.mps.ra.rabbit.vtech.com/"><soapenv:Header/><soapenv:Body>
 * <soap:resultRequest>
 * <username>#USERNAME</username>
 * <password>#PASSWORD</password>
 * <serviceid>#sub_service_name</serviceid> <!-- La package -->
 * <msisdn>#MSISDN</msisdn>
 * <chargetime>#CHARGE_TIME</chargetime>
 * <params>#PARAMS</params> <!-- 0-ok-gia han thanh cong, 1-fail - huy -->
 * <mode>#MODE</mode> <!-- check trang thai dich tu phia CP -->
 * <amount>#AMOUNT</amount>amount
 * <transid>#TRANSACTIONID</transid>
 * </soap:resultRequest>
 * </soapenv:Body></soapenv:Envelope>
 */
public class BuyOneTime extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(BuyOneTime.class);
    public BuyOneTime(SoapRequest request) {
        super(request);
    }

    @Override
    public String process(SoapRequest request) {
        String value = "0";
        String msisdn = "";
        String transid = "";
        try {
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1|Method:  " + request.getMethod();
            }
            
            msisdn = request.getValue("msisdn", "");
            msisdn = CountryCode.formatMobile(msisdn);
//            String sub_service = request.getDatas().get("sub_service");
            transid = request.getValue("transId", "");
            log.info("start update buytime: " + msisdn + ", transid:" +  transid);
            
            String amount = request.getValue("amount", "0");
            //Insert hoac update Subscriber
            
            String serviceCode = request.getValue("serviceid", "ILUCKY");

            String registerTime = request.getValue("chargetime", null);
            Timestamp REGISTER_TIME ;
            if (registerTime != null) {
                SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
                try {
                    Date date = f.parse(registerTime);
                    REGISTER_TIME = new Timestamp(date.getTime());
                } catch (ParseException ex) {
                    REGISTER_TIME = new Timestamp(System.currentTimeMillis());
                }
            } else {
                REGISTER_TIME = new Timestamp(System.currentTimeMillis());
            }

            //Inset log
            CdrLog cdr = new CdrLog();
            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = msisdn;
            cdr.TRANS_ID = transid;
            cdr.REQUEST_TIME = REGISTER_TIME;
            cdr.RESPONSE_TIME = REGISTER_TIME;
            cdr.RESPONSE_CODE = "0";
            cdr.SERVICE_NAME = serviceCode;
            cdr.SUB_SERVICE_NAME = serviceCode;
            cdr.CMD = "BUY";

            if (amount != null && NumberUtils.isNumber(amount)) {
                cdr.PRICE = Integer.parseInt(amount);
            } else {
                cdr.PRICE = 0;
            }

            cdr.CHANNEL = "USSD";

            InsertCdrProcess.getInstance().enqueue(cdr);
            
        } catch (Exception ex) {
            value = "1|" + ex.getMessage();
            log.error("error when receive renew request", ex);
        }
        log.info("finish update buytime: " + msisdn + ", transid:" +  transid + "=>" + value);
        return value;
    }

}
