/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.backend.bean.CdrLog;
import com.nh.backend.bean.Subscriber;
import com.nh.backend.process.InsertCdrProcess;
import com.nh.backend.process.RenewUpdateProcess;
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
 * <amount>#AMOUNT</amount>
 * <transid>#TRANSACTIONID</transid>
 * </soap:resultRequest>
 * </soapenv:Body></soapenv:Envelope>
 */
public class RenewHandler extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(RenewHandler.class);
    public RenewHandler(SoapRequest request) {
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
            
            if ("CHECK".equals(request.getValue("mode", ""))) {
                log.info(request.getClientIp() + " , check mode " + request.getMethod());
                return "0";
            }
            String msisdn = request.getValue("msisdn", null); 
            if (msisdn == null || msisdn.isEmpty()) {
                log.error(request.getClientIp() + " , send fail msisdn: " + request.getRawXml());
                return "1|msisdn is null ";
            }
            
            msisdn = CountryCode.formatMobile(msisdn);
            //Insert hoac update Subscriber
            Subscriber sb = new Subscriber();
            sb.MSISDN = msisdn;
            
            sb.MSISDN = CountryCode.formatMobile(sb.MSISDN);
            
            String serviceCode = request.getValue("serviceid", null);
            if (serviceCode == null) {
                return "1|add service id please, ex: <serviceid>#sub_service_name</serviceid>";
            }
            sb.SUB_SERVICE_NAME = request.getValue("serviceid", null);

            String registerTime = request.getValue("chargetime", null);
            if (registerTime != null) {
                SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
                try {
                    Date date = f.parse(registerTime);
                    sb.REGISTER_TIME = new Timestamp(date.getTime());
                } catch (ParseException ex) {
                    sb.REGISTER_TIME = new Timestamp(System.currentTimeMillis());
                }
            } else {
                sb.REGISTER_TIME = new Timestamp(System.currentTimeMillis());
            }

            sb.param = request.getValue("params", "1");
            sb.amount = request.getValue("amount", "0");
            RenewUpdateProcess.getInstance().enqueue(sb);

            //Inset log
            CdrLog cdr = new CdrLog();
            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = CountryCode.formatMobile(sb.MSISDN);
            cdr.TRANS_ID = request.getValue("transid", "");
            cdr.REQUEST_TIME = sb.REGISTER_TIME;
            cdr.RESPONSE_TIME = sb.REGISTER_TIME;
            cdr.RESPONSE_CODE = sb.param;
            if (cdr.RESPONSE_CODE != null && cdr.RESPONSE_CODE.length() > 20) {
                cdr.RESPONSE_CODE = cdr.RESPONSE_CODE.substring(0, 19);
            }
            cdr.SERVICE_NAME = sb.SUB_SERVICE_NAME;
            cdr.SUB_SERVICE_NAME = sb.SUB_SERVICE_NAME;
            cdr.CMD = "RENEW";

            if (sb.amount != null && NumberUtils.isNumber(sb.amount)) {
                cdr.PRICE = Integer.parseInt(sb.amount);
            } else {
                cdr.PRICE = 0;
            }

            cdr.CHANNEL = "SYS";

            InsertCdrProcess.getInstance().enqueue(cdr);

        } catch (Exception ex) {
            value = "1|" + ex.getMessage();
            log.error("error when receive renew request", ex);
        }

        return value;
    }

}
