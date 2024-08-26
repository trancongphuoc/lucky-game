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
import java.util.UUID;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

/**
 *
 * <?xml version='1.0' encoding='UTF-8'?><S:Envelope
 * xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
 * <S:Body>
 * <ws:doCharge>
 * <msisdn>864007813</msisdn>
 * <amount>1</amount>
 * <serviceName>MOBILETV</serviceName>
 * <providerName>VEGA</providerName>
 * <subCpName>subCpName</subCpName>
 * <category>category</category>
 * <item>item</item>
 * <command>CHARGE</command>
 * <subService>subService</subService>
 * <transId>transId</transId>
 * </ws:doCharge>
 * </S:Body>
 * </S:Envelope>
 */
public class TransactionMpiHandle extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(TransactionMpiHandle.class);
    public TransactionMpiHandle(SoapRequest request) {
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

            String msisdn = request.getValue("msisdn", null);
            if (msisdn == null) {
                log.debug(request.getClientIp() + " , TransactionMpiHandle send fail msisdn, default success: " + request.getRawXml());
                return "0";
            }
            String subServiceName = request.getValue("serviceName", null) ;
            String amount = request.getValue("amount", "0");
            //Inset log
            CdrLog cdr = new CdrLog();
            cdr.ID = UUID.randomUUID().toString();
            cdr.MSISDN = CountryCode.formatMobile(msisdn);
            cdr.TRANS_ID = request.getValue("transid", "");
            cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
            cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());
            cdr.RESPONSE_CODE = "0";
            cdr.SERVICE_NAME = request.getValue("serviceName", subServiceName);
            cdr.SUB_SERVICE_NAME = subServiceName;
            cdr.CMD = request.getValue("command","UNKNOW");

            if (amount != null && NumberUtils.isNumber(amount)) {
                cdr.PRICE = Double.parseDouble(amount);
            } else {
                cdr.PRICE = 0;
            }

            cdr.CHANNEL = request.getValue("channel", "UNKNOW");

            InsertCdrProcess.getInstance().enqueue(cdr);

        } catch (Exception ex) {
            value = "1|" + ex.getMessage();
            log.error("error when receive renew request", ex);
        }

        return value;
    }

}
