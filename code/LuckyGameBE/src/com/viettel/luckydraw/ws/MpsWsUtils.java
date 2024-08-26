/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws;


import com.viettel.luckydraw.util.AppContext;
import com.viettel.luckydraw.util.AppUtils;
import com.viettel.luckydraw.util.Config;
import com.viettel.luckydraw.util.Constant;
import com.viettel.luckydraw.ws.form.MpsResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author MrCAP
 */
public class MpsWsUtils {

    public static final Logger logger = Logger.getLogger(MpsWsUtils.class);
    public static final Object lockObj = new Object();
    private static MpsWsUtils instance;
    private int timeOut;
    private int socketTimeOut;

    private String mpsReqOtpHandleUrl;
    private String mpsReqModBalance;
    private String mpsReqUpointUrl;

    public MpsWsUtils(int timeOut, int socketTimeOut, String reqHandleUrl, String reqModeBalance, String mpsReqUpointUrl) {
        this.timeOut = timeOut;
        this.socketTimeOut = socketTimeOut;
        this.mpsReqOtpHandleUrl = reqHandleUrl;
        this.mpsReqModBalance = reqModeBalance;
        this.mpsReqUpointUrl = mpsReqUpointUrl;

    }

    public static MpsWsUtils getInstance() {
        if (instance == null) {
            synchronized (lockObj) {
                try {
                    instance = (MpsWsUtils) AppContext.getInstance().getContext().getBean("MpsWsUtils");
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return instance;
    }

//    public String getToken() {
//        String token = null;
//        try {
//            HttpURLConnection httpConn = getHttpConnection(this.getTokenUrl);
//            if (httpConn != null) {
//                String request = this.getTokenRequest.replace("__USERNAME__", this.getTokenUsername).replace("__PASSWORD__", this.getTokenPassword);
//                String authenStr = this.getTokenHeaderUser + ":" + this.getTokenHeaderPass;
//                String authenStrEn = new BASE64Encoder().encode(authenStr.getBytes());
//                httpConn.setRequestProperty("Content-Length", Integer.toString(request.getBytes().length));
//                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                httpConn.setRequestProperty("Authorization", "Basic " + authenStrEn);
//                WsResponseForm result = sendRequest("getToken", request, httpConn);
//                if (HttpURLConnection.HTTP_OK == result.getHttpStatus() && result.getResponse() != null) {
//                    token = AppUtils.parseJson(result.getResponse(), "access_token");
//                }
//                logger.info("result = " + result);
//            }
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//        }
//        return token;
//
//    }
//    public WsResponseForm callCreditAccount(String token, long amount, String msisdn, String transDate, String transId) {
//        WsResponseForm result = null;
//        HttpURLConnection httpConn = getHttpConnection(this.creditAccountUrl);
//        if (httpConn != null) {
//            httpConn.setRequestProperty("Content-Type", "application/json");
//            httpConn.setRequestProperty("Authorization", "bearer" + token);
//            JSONObject dataPush = new JSONObject();
//            dataPush.put("phoneNumber", msisdn);
//            dataPush.put("amount", amount);
//            dataPush.put("merchantService", this.creditAccountMerchant);
//            dataPush.put("merchantTransId", transId);
//            dataPush.put("merchantTransDate", transDate);
//            dataPush.put("content", this.creditAccountContent);
//            String request = dataPush.toString();
//            logger.info("REQUEST:" + request);
//            result = sendRequest("CreditAccount", request, httpConn);
//        }
//        parseResult(result);
//        return result;
//    }
    public MpsResponse callMpsAddUpoint(String msisdn, String amount, String transId) {
        MpsResponse result = null;
        HttpURLConnection httpConn = getHttpConnection(this.mpsReqUpointUrl);
        if (httpConn != null) {
            httpConn.setRequestProperty("Content-Type", "application/json");
//            httpConn.setRequestProperty("Authorization", "bearer" + token);
            JSONObject dataPush = new JSONObject();
            dataPush.put("msisdn", msisdn);
            dataPush.put("amount", amount);
            dataPush.put("transid", transId);
            String request = dataPush.toString();
            logger.info("Req callMpsAddUpoint:" + request);
            result = sendRequest("callMpsAddUpoint", request, httpConn);
        }
        parseResult(result);
        return result;
    }

    public MpsResponse callMpsOtpHandle(String msisdn, String cmd, String otpType, String otp, String transId, String cate, String amount) {
        MpsResponse result = null;

        HttpURLConnection httpConn = getHttpConnection(this.mpsReqOtpHandleUrl);
        if (httpConn != null) {
            httpConn.setRequestProperty("Content-Type", "application/json");
//            httpConn.setRequestProperty("Authorization", "bearer" + token);
            JSONObject dataPush = new JSONObject();
            dataPush.put("msisdn", msisdn);

            dataPush.put("otpType", otpType);
            if (Constant.ERROR_CODE_1.equals(otpType)) {
                dataPush.put("otp", otp);
            }
            
            if (Constant.CANCEL_CMD.equalsIgnoreCase(cmd)) {

                dataPush.put("sub_service", Config.getInstance().getSubService());
              //  dataPush.put("cate",  Config.getInstance().getCateBuy());
              //  dataPush.put("amount", Config.getInstance().getAmount());
            }
           
            if (Constant.CHARGE_CMD.equalsIgnoreCase(cmd)) {

                dataPush.put("sub_service", Config.getInstance().getSubServiceBuy());
              //  dataPush.put("cate",  Config.getInstance().getCateBuy());
              //  dataPush.put("amount", Config.getInstance().getAmount());
            }
            
            
            
            if (Constant.REGISTER_CMD.equalsIgnoreCase(cmd) || Constant.RENEW_CMD.equalsIgnoreCase(cmd) ) {
                dataPush.put("sub_service", Config.getInstance().getSubService());
            }
           

            dataPush.put("cmd", cmd);
            dataPush.put("transid", transId);
            String request = dataPush.toString();
            logger.info("Req callMpsOtpHandle:" + request);
            result = sendRequest("callMpsOtpHandle", request, httpConn);
        }
        parseResult(result);
        return result;
    }
    public MpsResponse callMpsModbalance(String msisdn, String cmd, String transId, String cate) {
        MpsResponse result = null;

        HttpURLConnection httpConn = getHttpConnection(this.mpsReqModBalance);
        if (httpConn != null) {
            httpConn.setRequestProperty("Content-Type", "application/json");
//            httpConn.setRequestProperty("Authorization", "bearer" + token);
            JSONObject dataPush = new JSONObject();
            dataPush.put("msisdn", msisdn);

//            dataPush.put("otpType", otpType);
//            if (Constant.ERROR_CODE_1.equals(otpType)) {
//                dataPush.put("otp", otp);
//            }
            
         
            if (Constant.DATA_CMD.equalsIgnoreCase(cmd) || Constant.VOICE_CMD.equalsIgnoreCase(cmd) || Constant.BALANCE_CMD.equalsIgnoreCase(cmd)) {

                dataPush.put("sub_service", Config.getInstance().getSubServiceBuy());
                dataPush.put("cate", cate);
              //  dataPush.put("amount", Config.getInstance().getAmount());
            }
           

            dataPush.put("cmd", cmd);
            dataPush.put("transid", transId);
            String request = dataPush.toString();
            logger.info("Req callMpsModbalance:" + request);
            result = sendRequest("callMpsModbalance", request, httpConn);
        }
        parseResult(result);
        return result;
    }

//    public LinkedHashMap<String, Object> getEwalletInfo(String msisdn) {
//        LinkedHashMap<String, Object> res = null;
//        if (!msisdn.startsWith(Config.getInstance().getOtpPrefix())) {
//            msisdn = Config.getInstance().getOtpPrefix() + msisdn;
//        }
//        HttpURLConnection httpConn = getHttpConnection(this.getEwalletInfoUrl);
//        if (httpConn != null) {
//            httpConn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
//            String request = this.getEwalletInfoRequest;
//            String fielId0 = "0200";
//            String fielId3 = "311202";
//            String fielId7 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//            String fielId22 = "1";
//            String fielId34 = msisdn;
//            String fielId41 = "MY_UNITEL";
//            String fielId42 = "BALANCE_INQUIRY";
//            StringBuffer strBuf = new StringBuffer();
//            strBuf.append(fielId0).append(fielId3).append(fielId7).append(fielId22).append(fielId34).append(fielId41).append(fielId42);
//            String fielId64 = strBuf.toString();
//
//            String token = AppUtils.iMD5(fielId64.getBytes());
//            if (token != null) {
//                request = request.replace("__MSISDN__", msisdn).replace("__TOKEN__", token).replace("__TRANSDATE__", fielId7);
//                logger.info("REQUEST:" + request);
//                WsResponseForm result = sendRequest("getEwalletInfo", request, httpConn);
//                if (HttpURLConnection.HTTP_OK == result.getHttpStatus() && result.getResponse() != null) {
//                    res = parseEwalletInfo(result.getResponse());
//                }
//            }
//
//        }
//        return res;
//    }
    public HttpURLConnection getHttpConnection(String wsUrl) {
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(wsUrl);
            URLConnection connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
            httpConn.setConnectTimeout(this.timeOut);
            httpConn.setReadTimeout(this.socketTimeOut);
            httpConn.setRequestMethod("POST");
            httpConn.setInstanceFollowRedirects(false);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;

        }
        return httpConn;

    }

    public MpsResponse sendRequest(String apiName, String request, HttpURLConnection httpConn) {

        long startTime = System.currentTimeMillis();
        MpsResponse responseForm = new MpsResponse();
        String result = "";
        InputStreamReader istrReader = null;
        BufferedReader buffReader = null;
        try {
            DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());
            out.writeBytes(request);
            out.flush();
            out.close();
            int httpStatus = httpConn.getResponseCode();
            logger.info(apiName + " HTTP Response Code: " + httpStatus);
            responseForm.setHttpStatus(httpStatus);
            if (httpStatus == HttpURLConnection.HTTP_OK) { //success
                if (httpConn.getInputStream() != null) {
                    istrReader = new InputStreamReader(httpConn.getInputStream());
                    buffReader = new BufferedReader(istrReader);
                    String responseString;
                    while ((responseString = buffReader.readLine()) != null) {
                        result = result + responseString;
                    }
                }
            } else if (httpConn.getErrorStream() != null) {
                istrReader = new InputStreamReader(httpConn.getErrorStream());
                buffReader = new BufferedReader(istrReader);
                String responseString;
                while ((responseString = buffReader.readLine()) != null) {
                    result = result + responseString;
                }
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
            if (ex instanceof SocketTimeoutException) {
                result = Constant.TIMEOUT;
            } else if (ex instanceof ConnectException) {
                result = Constant.CONNECTION_ERROR;
            } else {
                result = Constant.SYSTEM_ERROR;
            }
        } finally {
            if (buffReader != null) {
                try {
                    buffReader.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (istrReader != null) {
                try {
                    istrReader.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }
            responseForm.setResponse(result);

            logger.info(apiName + " result:" + result);
            logger.info(apiName + ":Time:" + (System.currentTimeMillis() - startTime) + " ms");
        }

        return responseForm;
    }

    public void parseResult(MpsResponse res) {
        String response = res.getResponse();
        try {
            if (response != null && !"".equals(response)) {
                if (response.equals(Constant.TIMEOUT) || response.equals(Constant.SYSTEM_ERROR) || response.equals(Constant.CONNECTION_ERROR)) {
                    res.setErrorCode(response);
                } else {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(response);
                    JSONObject objson = (JSONObject) obj;
                    String errorCode = (String) objson.get("return");
                    //   String des = (String) objson.get("message");
                    res.setErrorCode(errorCode);
                    // res.setDesciption(des);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String parseEwalletStatus(String input) {
        String ewalletStatus = null;
        try {
            String returnValue = AppUtils.getElementXml(input, "return");
            if (returnValue != null) {
                ewalletStatus = AppUtils.getElementXml(returnValue, "responseCode");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ewalletStatus;
    }

    public static LinkedHashMap<String, Object> parseEwalletInfo(String input) {
        System.out.println(input);
        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        try {

            InputSource is = new InputSource(new StringReader(input));
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            df.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            df.setFeature("http://xml.org/sax/features/external-general-entities", false);
            df.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            df.setXIncludeAware(false);
            df.setExpandEntityReferences(false);
            DocumentBuilder db = df.newDocumentBuilder();
            Document dc = db.parse(is);
            Element root = dc.getDocumentElement();
            String respCode = root.getElementsByTagName(Constant.RESPONSE_CODE_TAG).item(0).getTextContent();
            res.put("responseCode", respCode);
            if (!Constant.RESP_SUCCESS.equals(respCode)) {
                return res;
            }
            NodeList nodeList = dc.getElementsByTagName("fieldMap");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String fieldID = eElement.getElementsByTagName("fieldID").item(0).getTextContent();
                    String value = eElement.getElementsByTagName("value").item(0).getTextContent();

                    if (Constant.BALANCE_FIELD.equals(fieldID)) {
                        res.put("balance", value);

                    } else if (Constant.STATUS_FIELD.equals(fieldID)) {
                        res.put("status", value);

                    }

                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return res;
    }
}
