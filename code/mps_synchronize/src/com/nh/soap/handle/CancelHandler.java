/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.backend.bean.CdrLog;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.backend.process.BlackListManager;
import com.nh.backend.process.InsertCdrProcess;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import java.sql.Timestamp;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 *
 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
 * xmlns:soap="http://soap.mps.ra.nh.com/">
 * <soapenv:Header/><soapenv:Body><soap:subRequest>
 * <username>#USERNAME</username>
 * <password>#PASSWORD</password>
 * <serviceid>#subservice_name</serviceid> <!-- La package -->
 * <msisdn>#MSISDN</msisdn>
 * <chargetime>#CHARGE_TIME</chargetime>
 * <params>#PARAMS</params> <!-- 0-dang ky, 1-fail - huy -->
 * <mode>#MODE</mode> <!-- check trang thai dich tu phia CP -->
 * <amount>#AMOUNT</amount>
 * <command>#FULLSMS</command> <!-- Noi dung SMS -->
 * <transid>#TRANSACTIONID</transid>
 * </soap:subRequest>
 * </soapenv:Body>
 * </soapenv:Envelope>
 */
public class CancelHandler extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(CancelHandler.class);
    public CancelHandler(SoapRequest request) {
        super(request);
    }

    @Override
    public String process(SoapRequest request) {
        String value = "0|success";
        try {
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1|Method:  " + request.getMethod();
            }
            String msisdn = request.getValue("msisdn", null);
            
            
            if (msisdn == null) {
                log.error(request.getClientIp() + " , send fail msisdn: " + request.getRawXml());
                return "1|msisdn is null ";
            }
            msisdn = CountryCode.formatMobile(msisdn);
            if (BlackListManager.getInstance().isBlackList(msisdn)) {
                log.error(request.getClientIp() + " , msisdn in balcklist: " + msisdn);
                return "2|msisdn in balcklist";
            }
            
           DatabaseConnectionPool.getInstance().getConnection().cancelSub(msisdn);

            //Inset log
            CdrLog cdr = new CdrLog();
            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = CountryCode.formatMobile(msisdn);
            cdr.TRANS_ID = request.getValue("transid", "");
            cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
            cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());
            cdr.RESPONSE_CODE = "0";
            cdr.SERVICE_NAME = "SMSINFOMOCOM";
            cdr.SUB_SERVICE_NAME = "SMSINFOMOCOM";
            cdr.CMD = "CANCEL";
            cdr.PRICE = 0;
            cdr.CHANNEL = "USSD";
            InsertCdrProcess.getInstance().enqueue(cdr);
        } catch (Exception ex) {
            value = "0|" + ex.getMessage();
            log.error("error when receive Register or Cancel request", ex);
        }

        return value;
    }

}
