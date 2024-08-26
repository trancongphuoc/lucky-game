/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.luckydraw.util.AppContext;
import com.viettel.luckydraw.util.AppUtils;
import com.viettel.luckydraw.util.Constant;
import com.viettel.luckydraw.ws.form.MpsResponse;
import com.viettel.luckydraw.ws.form.UniIdResponseBean;
import com.viettel.luckydraw.ws.form.UuidResponse;
import com.viettel.luckydraw.ws.form.WsResponseForm;
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
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import javax.ws.rs.core.HttpHeaders;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
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
public class UuidUtils {

    public static final Logger logger = Logger.getLogger(UuidUtils.class);
    public static final Object lockObj = new Object();
    private static UuidUtils instance;
    private int timeOut;
    private int socketTimeOut;

    private  String uuidUrl;
    private  String uuidUsername;
    private  String uuidPassword;
    
    private String uniIdUrl ;
    

    private static final String PHONE = "phone";
    private static final String DATA = "data";


    public UuidUtils(int timeOut, int socketTimeOut, String reqUrl, String username, String uuidPass, String uniIdUrl ) {
        this.timeOut = timeOut;
        this.socketTimeOut = socketTimeOut;
        this.uuidUrl = reqUrl;
        this.uuidUsername = username;
        this.uuidPassword = uuidPass;
        this.uniIdUrl = uniIdUrl ;
    }
    

    public static UuidUtils getInstance() {
        if (instance == null) {
            synchronized (lockObj) {
                try {
                    instance = (UuidUtils) AppContext.getInstance().getContext().getBean("UuidUtils");
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return instance;
    }

    public String getIsdn(String uuid) {
        String isdn = null;
        try {
            HttpURLConnection httpConn = getHttpConnection(this.uuidUrl);
            if (httpConn != null) {



            JSONObject dataPush = new JSONObject();
            dataPush.put("username", this.uuidUsername);
            dataPush.put("password ", this.uuidPassword);
            dataPush.put("uuid", uuid);
            
            String urlParameters  = "username="+this.uuidUsername +"&password="+this.uuidPassword+"&uuid="+uuid;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int   postDataLength = postData.length;
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("charset", "utf-8");
            httpConn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            httpConn.setUseCaches(false);

          //  String request = dataPush.toString();
            logger.info("Req getIsdn:" + urlParameters);

            UuidResponse result = sendRequest("getIsdn", postData, httpConn);
                
            if (HttpURLConnection.HTTP_OK == result.getHttpStatus() && result.getResponse() != null) {
                    isdn = AppUtils.parseJson(result.getResponse(),"data", "msisdn");
                }
                logger.info("result Isdn = " + result);
            }
            
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        
        return isdn;

    }
    
    
    private static final int OK_VAL_UNIID = 100;
    private static final String SUCCESS_ERROR_UNIID = "SUCCESS";
    
   
    
    


//    public MpsResponse callMpsOtpHandle(String msisdn, String cmd, String otpType, String otp, String transId, String cate, String amount, String subService) {
//        MpsResponse result = null;
//
//        HttpURLConnection httpConn = getHttpConnection(this.mpsReqOtpHandleUrl);
//        if (httpConn != null) {
//            httpConn.setRequestProperty("Content-Type", "application/json");
////            httpConn.setRequestProperty("Authorization", "bearer" + token);
//            JSONObject dataPush = new JSONObject();
//            dataPush.put("msisdn", msisdn);
//
//            dataPush.put("otpType", otpType);
//            if (Constant.ERROR_CODE_1.equals(otpType)) {
//                dataPush.put("otp", otp);
//            }
//            
//            if (Constant.CHARGE_CMD.equalsIgnoreCase(cmd)) {
//
//                dataPush.put("sub_service", subService);
//                dataPush.put("cate", cate);
//                dataPush.put("amount", amount);
//            }
//            
//            if (Constant.REGISTER_CMD.equalsIgnoreCase(cmd) || Constant.RENEW_CMD.equalsIgnoreCase(cmd) ) {
//                dataPush.put("sub_service", subService);
//            }
//           
//
//            dataPush.put("cmd", cmd);
//            dataPush.put("transid", transId);
//            String request = dataPush.toString();
//            logger.info("Req callMpsOtpHandle:" + request);
//            result = sendRequest("callMpsOtpHandle", request, httpConn);
//        }
//        parseResult(result);
//        return result;
//    }

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

    public UuidResponse sendRequest(String apiName,byte[]  request, HttpURLConnection httpConn) {

        long startTime = System.currentTimeMillis();
        UuidResponse responseForm = new UuidResponse();
        String result = "";
        InputStreamReader istrReader = null;
        BufferedReader buffReader = null;
        try {

            DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());
          //  out.writeBytes(request);
            out.write(request);
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


    
     public  String verifyUniIdUser(String token) throws ClientProtocolException, IOException {
       
        String retVal;
        UniIdResponseBean res;
         
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            //   String url = API_URL;           
            logger.info("API_UNIID_URL =============>: " + this.uniIdUrl);

             HttpGet httpGet = new HttpGet(this.uniIdUrl);             
             String authHeader = "Bearer " + token;
             httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
             httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
             httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
             
            CloseableHttpResponse response = client.execute(httpGet);
            logger.info("statusCode:" + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                retVal = EntityUtils.toString(response.getEntity());
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                res = mapper.readValue(retVal, UniIdResponseBean.class);

                if (res != null) {

                    if ((OK_VAL_UNIID == res.getStatus()) && SUCCESS_ERROR_UNIID.equals(res.getError())) {
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jobj = (JSONObject) jsonParser.parse(retVal);

                        logger.info("jobj================>" + jobj.toJSONString());

                        Object dataObject = jobj.get(DATA);

                        if (dataObject instanceof JSONArray) {
                            JSONArray jsArray = (JSONArray) jobj.get(DATA);

                            for (int i = 0; i < jsArray.size(); i++) {
                                JSONObject objItem = (JSONObject) jsArray.get(i);

                                logger.info("obj=======AAAAAAAAAAAAAA=========>" + objItem.toJSONString());
                                logger.info("user phone ================>" + objItem.get(PHONE).toString());

                                token = (String) objItem.get(PHONE);
                            }
                        } else {
                            JSONObject obj = (JSONObject) dataObject;

                            logger.info("obj=======BBBBBBBBBBBBB=========>" + obj.toJSONString());
                            logger.info("user phone =====BBBBBBBBBBBBB===========>" + obj.get(PHONE).toString());

                            token = (String) obj.get(PHONE);
                        }
                    }
                }

            } else {
                logger.error("Error Call UniID  " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception ex) {

            logger.error("Error Call Call UniID verify " + ex);

        }
        return token;
    }
        
    
}
