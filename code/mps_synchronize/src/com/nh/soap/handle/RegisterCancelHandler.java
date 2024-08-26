/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.backend.bean.CdrLog;
import com.nh.backend.bean.Subscriber;
import com.nh.backend.process.InsertCdrProcess;
import com.nh.backend.process.RegisterCancelProcess;
import com.nh.backend.process.WhitelistManager;
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
public class RegisterCancelHandler extends AbstractHandler {
protected final static Logger log = Logger.getLogger(RegisterCancelHandler.class);
    public RegisterCancelHandler(SoapRequest request) {
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
            
            if ("CHECK".equals(request.getValue("mode", null))) {
//                msisdn = CountryCode.formatMobile(msisdn);
//                if (BlackListManager.getInstance().isBlackList(msisdn)) {
//                    log.error(request.getClientIp() + " , msisdn in balcklist: " + msisdn);
//                    return "2|msisdn in balcklist";
//                } else {
                     return "0";
//                }
            }
            
            if (msisdn == null) {
                log.error(request.getClientIp() + " , send fail msisdn: " + request.getRawXml());
                return "1|msisdn is null ";
            }
            msisdn = CountryCode.formatMobile(msisdn);
//            if (BlackListManager.getInstance().isBlackList(msisdn)) {
//                log.error(request.getClientIp() + " , msisdn in blacklist: " + msisdn);
//                return "2|msisdn in blacklist";
//            }
            
            if (!WhitelistManager.getInstance().isWhitelist(msisdn)) {
                log.error(request.getClientIp() + " , msisdn in whitelist: " + msisdn);
                return "2|not in whitelist";
            }
            
            Subscriber sb = new Subscriber();
            sb.MSISDN = msisdn;
            String serviceCode = request.getValue("serviceid", null);
            if (serviceCode == null) {
                serviceCode = GlobalConfig.get("mps.sub_service");
            }
            sb.SUB_SERVICE_NAME = serviceCode;

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

            sb.param = request.getValue("params", "0");
            sb.amount = request.getValue("amount","0");
            String command = request.getValue("command", "UNKNOW");
            String sub_new = request.getValue("sub_new", "false");
             
            RegisterCancelProcess.getInstance().enqueue(sb);

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

            if (Subscriber.PARAM_REGISTER.equalsIgnoreCase(sb.param)) {
                cdr.CMD = "REGISTER";
                cdr.ITEM_NAME = sub_new +"";
            } else {
                cdr.CMD = "CANCEL";
            }

            if (sb.amount != null && NumberUtils.isNumber(sb.amount)) {
                cdr.PRICE = Integer.parseInt(sb.amount);
            } else {
                cdr.PRICE = 0;
            }

            cdr.CHANNEL = "SMS";
            if ("CANCEL".equals(cdr.CMD) && !"OFF".equals(command)) {
                cdr.CHANNEL = "SYS";
            }

            InsertCdrProcess.getInstance().enqueue(cdr);
        } catch (Exception ex) {
            value = "1|" + ex.getMessage();
            log.error("error when receive Register or Cancel request", ex);
        }

        return value;
    }

}
