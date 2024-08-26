package com.viettel.luckydraw.util;


import com.viettel.luckydraw.bo.CallWsTransactionLog;
import com.viettel.luckydraw.bo.ResponseObj;
import com.viettel.luckydraw.bo.Webservice;
import com.viettel.luckydraw.ws.form.SoapWSResponse;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.util.Date;
import javax.sql.DataSource;


public class WebServiceUtil {

    private static final Logger logger = Logger.getLogger(WebServiceUtil.class);
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
    private int connectTimeout;
    private int recvTimeout;
    private HttpClient httpclient;
    private long timeStart;
    private long timeExecute;
    private Webservice wsObject;

    private static WebServiceUtil instance;
    private DataSource dataSource;
    private CallWsTransactionLog transLog = new CallWsTransactionLog();
    private static final Object lockObj = new Object();

    private void initConnection() {
        if (httpclient != null) {
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(connectTimeout);
            conPars.setSoTimeout(recvTimeout);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    public WebServiceUtil(Webservice wsObject) {
        this.wsObject = wsObject;

        try {
            //    logger = Logger.getLogger("WebServiceUtil");

            connectTimeout = wsObject.getTimeout();
            recvTimeout = wsObject.getTimeout();
            httpclient = new HttpClient();
            initConnection();
            logger.info("CONNECT_TIMEOUT: " + connectTimeout);
            logger.info("RECEIVE_TIMEOUT: " + recvTimeout);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WebServiceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ResponseObj parseResult(String response, String webservice) throws SOAPException {
        ResponseObj result = new ResponseObj();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(response));
            Document doc = builder.parse(is);

            // if
            // (webservice.equalsIgnoreCase(Constants.WEBSERVICE_NAME_SENDMT)) {
            String code = doc.getFirstChild().getTextContent().trim();
            if (Constant.ERROR_CODE_0.equalsIgnoreCase(code)) {
                result.setErrCode(Constant.ERROR_CODE_0);
                result.setResponseStr("success");
            } else {
                result.setErrCode(Constant.ERROR_CODE_1);
                result.setResponseStr("fail");
            }

        } catch (Exception ex) {
            logger.error("", ex);
            result.setErrCode(Constant.ERROR_CODE_1);
        }

        return result;
    }

    public SoapWSResponse callWebService(LinkedHashMap<String, Object> params, Boolean enableRetry) {
        try {
            return iCallWebService(params);
        } catch (Exception ex) {
            if (enableRetry) {
                logger.error("\r\nCalling webservice found an exception ", ex);
                logger.error("\r\n Now retry again");
                try {
                    return iCallWebService(params);
                } catch (Exception e) {
                    logger.error("\r\nCalling webservice found an exception ", e);
                    transLog.setResponse("Calling webservice found an exception: " + e.getMessage());
                    return null;
                }
            } else {
                logger.error("\r\nCalling webservice found an exception ", ex);
                transLog.setResponse("Calling webservice found an exception: " + ex.getMessage());
                return null;
            }
        }
    }

    private SoapWSResponse iCallWebService(LinkedHashMap<String, Object> params) throws Exception {

        try {
            String webserviceName = wsObject.getWsName();
            PostMethod post = new PostMethod(wsObject.getUrl());
            // replace params
            String requestBody = wsObject.getRawXml();
            if (params != null) {
                for (String param : params.keySet()) {
                    requestBody = requestBody.replace("@" + param, params.get(param).toString());
                }
            }

            RequestEntity entity = new StringRequestEntity(requestBody, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", webserviceName);

            String responseBody = "";
            timeStart = System.currentTimeMillis();

            try {
                transLog.setRequest(requestBody);
                transLog.setRequestTime(new Date());
                logger.info(requestBody);

                httpclient.executeMethod(post);
                transLog.setResponseTime(new Date());
                transLog.setDuration(new Date().getTime() - timeStart);
                transLog.setCommand(webserviceName);

                responseBody = post.getResponseBodyAsString();
            } catch (IOException ex) { // connection reset
                logger.error("ExecuteMethodException", ex);
                if (!ex.getMessage().contains("Connection reset")) {
                    // ignore, don't retry

                    transLog.setExtraInfo(ex.getMessage());
                    throw ex;
                }
                logger.warn(
                        "Connection reset during process of sending request " + ex.getMessage() + ", resend request");
                // recreate request
                httpclient = new HttpClient();

                initConnection();
                // send request
                httpclient.executeMethod(post);
                responseBody = post.getResponseBodyAsString();
            }

            logger.info(responseBody);
            SoapWSResponse response = new SoapWSResponse(responseBody);
            if (wsObject.getRspCodeSucc() != null && wsObject.getRspCodeSucc()
                    .equalsIgnoreCase(response.getTextContent(wsObject.getXpathResponseCode()))) {
                response.setErrCode(Constant.ERROR_CODE_0);
            } else {
                response.setErrCode(Constant.ERROR_CODE_1);
            }
            transLog.setErrorCode(response.getErrCode());

            timeExecute = System.currentTimeMillis() - timeStart;
            logger.info("Time to iCallWebService is " + timeExecute + "ms");
            transLog.setResponse(responseBody);

            return response;
        } catch (Exception ex) {
            logger.error("", ex);
            transLog.setExtraInfo(ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public CallWsTransactionLog getTransLog() {
        return transLog;
    }

    public void setTransLog(CallWsTransactionLog transLog) {
        this.transLog = transLog;
    }

    public ResponseObj callWebServiceSms(LinkedHashMap<String, Object> params, boolean b) {
        SoapWSResponse response = callWebService(params, true);
        try {
            return parseResult(response.getWSResponse(), "");
        } catch (Exception e) {
            logger.error("parseResultError", e);
            return null;
        }

    }
}
