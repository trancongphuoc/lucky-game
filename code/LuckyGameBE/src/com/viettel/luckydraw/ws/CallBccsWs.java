/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws;

import com.viettel.security.PassTranformer;
import com.viettel.luckydraw.util.AppContext;
import com.viettel.luckydraw.util.AppUtils;
import com.viettel.luckydraw.util.Constant;
import com.viettel.luckydraw.ws.form.RequestBccsForm;
import com.viettel.luckydraw.ws.form.ResponseBccsForm;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author manhnv23
 */
public class CallBccsWs {//

    private static final Logger log = Logger.getLogger(CallBccsWs.class);
    private static CallBccsWs instance;
    private static final Object lockObj = new Object();
    private final SimpleDateFormat requestTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String CHARSET = "UTF-8";
    private static final String TYPE = "text/xml";

    private HttpClient httpTransport;
    private int timeOut;
    private int socketTimeOut;
    private int retryTimes;
    private int retrySleep;
    private String urlWs;
    private String getAccountInfoTemplate;
    private String addBalanceTemplate;
    private String encUser;
    private String encPass;

    public CallBccsWs(int timeOut, int socketTimeOut, int retryTimes, int retrySleep, String urlWs, String getAccountInfoTemplate, String addBalanceTemplate, String encUser, String encPass) {
        this.timeOut = timeOut;
        this.socketTimeOut = socketTimeOut;
        this.retryTimes = retryTimes;
        this.retrySleep = retrySleep;
        this.urlWs = urlWs;
        this.getAccountInfoTemplate = getAccountInfoTemplate;
        this.addBalanceTemplate = addBalanceTemplate;
        this.encUser = encUser;
        this.encPass = encPass;
    }

    public static CallBccsWs getInstance() {
        if (instance == null) {
            synchronized (lockObj) {
                try {
                    instance = (CallBccsWs) AppContext.getInstance().getContext().getBean("CallBccsWs");
                } catch (Exception ex) {
                   log.error(ex.getMessage(), ex);
                }
            }
        }
        return instance;
    }

    public HttpClient getHttpTransport() {
        return httpTransport;
    }

    public void setHttpTransport(HttpClient httpTransport) {
        this.httpTransport = httpTransport;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getRetrySleep() {
        return retrySleep;
    }

    public void setRetrySleep(int retrySleep) {
        this.retrySleep = retrySleep;
    }

    public String getUrlWs() {
        return urlWs;
    }

    public void setUrlWs(String urlWs) {
        this.urlWs = urlWs;
    }

    public String getGetAccountInfoTemplate() {
        return getAccountInfoTemplate;
    }

    public void setGetAccountInfoTemplate(String getAccountInfoTemplate) {
        this.getAccountInfoTemplate = getAccountInfoTemplate;
    }

    public String getAddBalanceTemplate() {
        return addBalanceTemplate;
    }

    public void setAddBalanceTemplate(String addBalanceTemplate) {
        this.addBalanceTemplate = addBalanceTemplate;
    }

    public String getEncUser() {
        return encUser;
    }

    public void setEncUser(String encUser) {
        this.encUser = encUser;
    }

    public String getEncPass() {
        return encPass;
    }

    public void setEncPass(String encPass) {
        this.encPass = encPass;
    }



     public ResponseBccsForm callBccsWs(RequestBccsForm request) {
        ResponseBccsForm ret = new ResponseBccsForm();
        int retry = 1;
        String wsReponse = "";
        Date date = new Date();
        request.setRequestTime(requestTimeFormat.format(date));
        try {
            wsReponse = callWs(request);
            while (retry <= retryTimes && wsReponse.equals(Constant.SYSTEM_ERROR)) {
                try {
                    Thread.sleep(retrySleep);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                wsReponse = callWs(request);
                retry++;
                log.info("WS time out retry no : " + retry + "times");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
 
        wsReponse = StringEscapeUtils.unescapeXml(wsReponse);
        ret = parseWsResponse(request.getMsisdn(), wsReponse, request.getRequestTime());
        return ret;
    }
     
     private String callWs(RequestBccsForm rx) {
        getInstance();
        String res = null;
        instanceHttpTransport();

        String wsdl = urlWs; // wsdl cua webservice
        PostMethod post = new PostMethod(wsdl);
        long startTime = System.currentTimeMillis();
        try {
//            String soapAction = null;
            String soapRequest = buildSoapRequest(rx);
            RequestEntity entity = new StringRequestEntity(soapRequest, TYPE, CHARSET);
            post.setRequestEntity(entity);
//            post.setRequestHeader("SOAPAction", soapAction);
            httpTransport.executeMethod(post);
            res = post.getResponseBodyAsString();
//            log.info(res);
        } catch (SocketTimeoutException e) {
            log.error("ERROR_CALLING_WS_TIME_OUT " );
            log.error(e.getMessage(), e);
            res = Constant.TIMEOUT;
        } catch (IOException e) {
            log.error("ERROR_CALLING_WS" );
            log.error(e.getMessage(), e);
            res = Constant.SYSTEM_ERROR;
        } finally {
            post.releaseConnection();
        }
        log.debug("return: " + res);
        log.info("Time call ws: " + (System.currentTimeMillis() - startTime) + " ms");
        return res;
    }
     
     
    private ResponseBccsForm parseWsResponse(String msisdn, String wsReponse, String requestTime) {
        ResponseBccsForm res = new ResponseBccsForm();
        log.info("reponse: " + msisdn + ": " + wsReponse);
        if (wsReponse != null && !"".equals(wsReponse)) {
            try {
                if (wsReponse.equals(Constant.TIMEOUT) || wsReponse.equals(Constant.SYSTEM_ERROR)) {
                    res.setErrorCode(wsReponse);
                } else {

                    String original = AppUtils.getElementXml(wsReponse, "original");
                    res.setErrorCode(AppUtils.getElementXml(original, "responseCode"));
                    res.setDescription(AppUtils.getElementXml(original, "description"));

                }
            } catch (Exception e) {
//                res.setErrorCode(Constant.SYSTEM_ERROR);
                log.error("ERR_WHEN_PARSER " + e.getMessage(), e);
            }
        } else {
            res.setErrorCode(Constant.SYSTEM_ERROR);
        }
        return res;
    }

    private void instanceHttpTransport() {
        if (httpTransport == null) {
            httpTransport = new HttpClient();
            HttpConnectionManagerParams params = new HttpConnectionManagerParams();
            params.setSoTimeout(socketTimeOut);
            params.setConnectionTimeout(timeOut);
            log.info("timeOut " + timeOut);
            log.info("socketTimeOut " + socketTimeOut);
            params.setDefaultMaxConnectionsPerHost(10);
            MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
            manager.setParams(params);
            httpTransport.setHttpConnectionManager(manager);
        }
    }


    private String buildSoapRequest(RequestBccsForm rx) {
        String soapMsg = getAccountInfoTemplate;
        if (Constant.CALL_BCCS_ADD_BALANCE.equals(rx.getRequestType())) {
            soapMsg = addBalanceTemplate;
        }
        soapMsg = soapMsg.replace("__MSISDN__", rx.getMsisdn() == null ? "" : rx.getMsisdn());
        soapMsg = soapMsg.replace("__ACC_ID__", rx.getAccId() == null ? "" : rx.getAccId());
//        soapMsg = soapMsg.replace("__REQUEST_ID__", UUID.randomUUID().toString());
        soapMsg = soapMsg.replace("__VALUE__", rx.getValue() == null ? "" : rx.getValue());
//        soapMsg = soapMsg.replace("__RESET__", rx.getReset() == null ? "" : rx.getReset());
        soapMsg = soapMsg.replace("__ADD_EXPIRE__", rx.getAddExpire() == null ? "" : rx.getAddExpire());
        log.info("request:" + rx.getMsisdn() + ": " + soapMsg);
        soapMsg = soapMsg.replace("__USER__", PassTranformer.decrypt(encUser));
        soapMsg = soapMsg.replace("__PASS__", PassTranformer.decrypt(encPass));
        return soapMsg;
    }

}
