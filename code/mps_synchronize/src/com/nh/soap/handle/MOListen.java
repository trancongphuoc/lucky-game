/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.backend.bean.HttpApi;
import com.nh.backend.bean.HttpLog;
import com.nh.backend.bean.SmsData;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.backend.process.HttpLogProcess;
import com.nh.backend.process.SendSmsProcess;
import com.nh.soap.SoapRequest;
import com.nh.util.CallWS;
import com.nh.util.CommonUtil;
import com.nh.util.CountryCode;
import com.nh.util.SoapClient;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

public class MOListen extends AbstractHandler {
    protected final static Logger log = Logger.getLogger(MOListen.class);
    public static final String SCORE_BUSYTIME = "2";
    public static final String SCORE_WIN = "0";
    public static final String SCORE_LOSE = "1";
    
    public MOListen(SoapRequest request) {
        super(request);
    }

    /**
     * 0 win 
     * 
     * return
     * #AWARD_NAME,# LUCKY_CODE,#CONTACT_NUMBER,#PERIOD_CODE,#WINNER_MSISDN
     */
    @Override
    public String process(SoapRequest request) {
        String returnValue = "1";
        String adward = "";
        try {
            String msisdn = request.getValue("msisdn", null);
            msisdn = CountryCode.formatMobile(msisdn);
            String transid = request.getValue("transid", "");
            String mocode = request.getValue("mocode", null);
            if (mocode == null || transid == null || msisdn == null) {
                log.info("invalid request:  " + mocode
                    + ", msisdn: " + msisdn + ", transId: " + transid);
                 return returnValue ;
            }
//           
//            GoloBean golo = null;

//            if (golo == null) {
                log.info("call db error:  " + mocode
                    + ", msisdn: " + msisdn + ", transId: " + transid);
                sendMt(GlobalConfig.get("msg_sms_error"), msisdn, transid);
                return returnValue;
//            }

//            log.info(transid + "|" + msisdn + ", result: " + returnValue);
        } catch (Exception ex) {
            log.error("error when excute rsa to mps", ex);
            returnValue = "-1|" + ex.toString();
        }
        return returnValue;
    }

    public boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
    
    private String currentDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }
    
    private String currentHour() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.HOUR_OF_DAY);

        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }
    
    private boolean doAction(String httpCode, String msisdn, String transId, Map<String, String> params) {
        boolean isResponseOk = false;
        log.info("http start:" + httpCode + " ,transId:" + transId);
        long start = System.currentTimeMillis();
        long end = -1l;
        long duration = -1l;
        try {
            HttpApi httpApi = DatabaseConnectionPool.getInstance().getConnection().getHttpApi(httpCode);
            if (httpApi == null) {
                log.error("http not found any http api with code:" + httpCode + " ,transId:" + transId+ ", msisdn:" + msisdn);
                return false;
            }

            HttpLog httpLog = new HttpLog(transId, msisdn);
            httpLog.setModuleCode(httpApi.getModuleCode());
            httpLog.setChannel(HttpLog.CHANNEL_HTTP);
            httpLog.setAction(httpCode);
            httpLog.setUrl(httpApi.getUrl());
            httpLog.setRequestTime(new Timestamp(start));
            httpLog.setErrorCode(HttpLog.ERROR_CODE_FAIL);

            Map<String, String> res = new HashMap<>();

            if (HttpApi.WEBSERVICE_TYPE_SOAP.equals(httpApi.getWebserviceType())) {
                Properties pars = new Properties();
                pars.put(CallWS.WS_USERNAME, httpApi.getUserName());
                pars.put(CallWS.WS_PASSWORD, httpApi.getPassword());
                pars.put(CallWS.WS_MSISDN, CountryCode.getCountryCode() + CountryCode.formatMobile(msisdn));
                pars.put(CallWS.WS_ISDN, CountryCode.formatMobile(msisdn));
                pars.put(CallWS.WS_TRANS_ID, transId);
                pars.put(CallWS.WS_CHARGE_TIME, CommonUtil.getCurrentDateTime());
                String xml = CallWS.parseValue(httpApi.getBody(), pars);
                if (params != null && !params.isEmpty()) {
                    xml = CallWS.parseValue(xml, params);
                }
                SoapClient soapClient = new SoapClient(httpApi.getUrl());
                String response = null;
                try {
                    response = soapClient.sendXml(xml, res, httpApi.getTimeout());
                    if (response != null) {
                        httpLog.setResponse(response);
                    }
                } catch (Exception ex) {
                    log.error("error for: " + transId, ex);
                    String exep = CommonUtil.getError(ex);
                    httpLog.setResponse(exep);
                    httpLog.setErrorCode(HttpLog.ERROR_CODE_ERROR);
                }
                httpLog.setBody(xml);
            }

            String returnValue = res.get(httpApi.getReturnTag());
            if (returnValue != null) {
                if (httpApi.getReturnAuto() > 0) {
                    StringTokenizer tok = new StringTokenizer(returnValue, "|");
                    tok.nextToken();
                    String desc;
                    if (tok.countTokens() > 0) {
                        desc = tok.nextToken();
                        log.info("http ReturnAuto:" + desc + " ,transId:" + transId);
                    }
                }
            }

            if (returnValue != null
                    && (httpApi.getReturnSuccessValue().startsWith(returnValue)
                    || returnValue.startsWith(httpApi.getReturnSuccessValue())
                    || returnValue.matches(httpApi.getReturnSuccessValue()))) {
                httpLog.setErrorCode(HttpLog.ERROR_CODE_OK);
                isResponseOk = true;
            }

            end = System.currentTimeMillis();
            duration = end - start;
            httpLog.setResponseTime(new Timestamp(System.currentTimeMillis()));
            httpLog.setDuration(duration);
            HttpLogProcess.getInstance().enqueue(httpLog);
        } catch (Exception ex) {
            log.error("error for: " + transId, ex);
        }

        log.info("http end: response: " + isResponseOk + " ,transId:"
                + transId + ", duration(ms): " + (System.currentTimeMillis() - start));
        return isResponseOk;
    }
    
    
    private void sendMt(String content, String msisdn, String transId) {
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
