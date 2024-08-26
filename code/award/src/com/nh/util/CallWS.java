/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.util;

import com.nh.GlobalConfig;
import com.nh.bean.CPWebservice;
import com.nh.bean.ContentResult;
import com.wbxml.WbxmlLibs;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author hadc
 */
public class CallWS {
     private static final int RETRY_CHARGE = 1;
    
    public static final int SERVICE_REGISTER = 0;
    public static final int SERVICE_UNREGISTER = 1;
    public static final int SERVICE_PENDING = 2;
    public static final int SERVICE_RESTORE = 3;
    
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAILED = 1;
    public static final int RESULT_TIMEOUT = -1;
    
//    public static final int CHARGE_SUCCESS = 0;
    public static final int CHARGE_FAILED = 1;
    
    public static final int CHARGE_SUCCESS = 0;
    public static final int CHARGE_INVALID_PARAMS = 201;
    public static final int CHARGE_VAS_REQUEST_FAILED = 202;
    public static final int CHARGE_BALANCE_NOT_ENOUGH = 401;
    public static final int CHARGE_SUB_NOT_REGISTER = 402;
    public static final int CHARGE_SUB_NOT_EXIST = 403;
    public static final int CHARGE_MSISDN_NOT_AVAILABLE = 404;
    public static final int CHARGE_TWO_WAY_BLOCK = 409;
    public static final int CHARGE_GENERAL_ERROR = 440;
    public static final int CHARGE_MSISDN_CHANGE_OWNER = 405;
    public static final int CHARGE_MSISDN_NOT_SYNC = 501;
    public static final int CHARGE_MSISDN_TIME_OUT = -1;
    
//    public static final int SERVICE_UNREGISTER = 1;
//    public static final int SERVICE_UNREGISTER = 1;
    public static final String CMD_REGISTER = "REGISTER";
    public static final String CMD_UNREGISTER = "CANCEL";
    public static final String CMD_MO = "MO";
    public static final String CMD_CHARGE = "CHARGE";
    public static final String CMD_MONFEE = "MONFEE";
    
    public static final String WS_MODE_CHECK = "CHECK";
    public static final String WS_MODE_REAL = "REAL";
    
    public static final String CHANNEL_SMS = "SMS";
    public static final String CHANNEL_USSD = "USSD";
    public static final String CHANNEL_WEB = "WEB";
    public static final String CHANNEL_WAP = "WAP";
    public static final String CHANNEL_IVR = "IVR";
    public static final String CHANNEL_SYS = "SYS";
    public static final String CHANNEL_CMS = "CMS";
    //    
    // ***********************************************************************
    // Replacement for calling CP Webservices
    // ***********************************************************************
    public static final String WS_USERNAME = "#USERNAME";
    public static final String WS_PASSWORD = "#PASSWORD";
    public static final String WS_SERVICE_NAME = "#SERVICE_NAME";
    public static final String WS_MSISDN = "#MSISDN";
    public static final String WS_ISDN = "#ISDN";
    public static final String WS_CHARGE_TIME = "#CHARGE_TIME";
    public static final String WS_MODE = "#MODE";
    public static final String WS_PARAMS = "#PARAMS";
    public static final String WS_AMOUNT = "#AMOUNT";
    public static final String WS_COMMAND = "#FULLSMS"; // SMS la noi dung nguoi dung nhan len, doi IVR, SMS la phim vua bam
    public static final String WS_SMS_ITEM = "#SMS_ITEM";
    public static final String WS_TRANS_ID = "#TRANS_ID";
    public static final String WS_SUBSCRIPTION_STATUS = "#SUBSCRIPTION_STATUS"; // check trang thai la sub hay chua, danh cho ivr va ussd
    
    public static final String WS_DATETIME = "#yyyyMMddHHmmss";
    public static final String WS_EXTSMS = "#EXTSMS";
    public static final String WS_DATA_SIGN = "#DATA_SIGN";
    
    public static final int THIRTY_SECOND_TIMEOUT = 30 * 1000; // ms
    public static final int CHARGING_TIMEOUT = 120 * 1000; //ms
    
    public static final int HTTP_TIMEOUT = 30 * 1000; //ms
    private static final Logger log = Logger.getLogger(CallWS.class);

    public static String callWS(String wsURL, String inputXML, String soapAction, int retry, int timeout) {
        String result = "";
        String responseString;

        int i = 0;
        for (i = 0; i < retry; i++) {
            log.debug("Try " + i + " Call WS got XML input: " + inputXML);

            try {
                URL url = new URL(wsURL);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                bout.write(inputXML.getBytes());

                byte[] b = bout.toByteArray();
                // Set the appropriate HTTP parameters.
                httpConn.setReadTimeout(timeout); // ms
                httpConn.setConnectTimeout(timeout); // ms
                httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
                httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                httpConn.setRequestProperty("SOAPAction", soapAction);
                //            httpConn.setRequestProperty("Host", "10.58.51.56:80");

                httpConn.setRequestMethod("POST");
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                OutputStream out = httpConn.getOutputStream();
                //Write the content of the request to the outputstream of the HTTP Connection.
                out.write(b);
                out.close();
                //Ready with sending the request.
                //Read the response.
                InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
                BufferedReader in = new BufferedReader(isr);

                //Write the SOAP message response to a String.
                while ((responseString = in.readLine()) != null) {
                    result += responseString;
                }
            } catch (Exception ex) {
                log.error(ex);
                result = "";
            }

            if (result != null && !result.isEmpty()) {
                break;
            }
        }
        log.debug("Try " + i + " Call WS got Response: '" + result + "'");
        return result;
    }

    public static ContentResult cpWebservice(CPWebservice ws, Properties params, int retryTimes) {
        log.info("Call CP Contents Webservice " + ws + " with " + params);
        ContentResult result = null;
        
        long start = System.currentTimeMillis();
        String xmlInput = parseValue(ws.getRawXML(), params);
        try {
            
            log.info("Call cpWebservice " + ws + "body: " + xmlInput + " -> ok," + (System.currentTimeMillis() - start) + " ms)");
            String tmp = callHttpSoap(ws.getUrl(), xmlInput, retryTimes, ws.getTimeout(), ws.getReturnTag());
            if (tmp == null) {
                return null;
            }
            StringTokenizer tok = new StringTokenizer(tmp, "|");
            int code = Integer.parseInt(tok.nextToken());
            String desc = "";
            if (tok.countTokens() > 0) {
                desc = tok.nextToken();
            }
            int moPrice = 0;
            int cmdPrice = 0;
            
            if (tok.countTokens() >= 2) {
                moPrice = Integer.parseInt(tok.nextToken());
                cmdPrice = Integer.parseInt(tok.nextToken());
            }
            
            result = new ContentResult(code, desc, moPrice, cmdPrice);
        } catch (Exception e) {
            log.error("error when send", e);
        }
        
        log.info("GetContent '" + params + "' -> " + result + " (" + (System.currentTimeMillis() - start) + " ms)");
        return result;
    }
    
    private static String callHttpSoap(String wsURL,String inputXML, int retry, int timeout,String returnTag ){
        int i=0;
        String result = null;
        for (i = 0; i< retry; i++) {
            log.debug("Try " + i + " Call WS (" + wsURL + ") got XML input: " + inputXML);
            SoapClient client = new SoapClient(wsURL.trim());
            Map<String,String> datas = client.sendRequest(inputXML);
            log.info("inputXML = = " +  inputXML);
            log.info("datas = = " +  datas);
            if (datas != null && datas.size() >0) {
                if (datas.containsKey(returnTag)){
                    result = datas.get(returnTag);
                } else {
                    log.error("have result from cp, but do not have tag: " + returnTag);
                    for (Map.Entry<String, String> entry : datas.entrySet()) {
                            String string = entry.getKey();
                            String string1 = entry.getValue();
                            log.error(string + " => " + string1);
                    }
                }
            }
            
            if (result != null && !result.isEmpty()) {
                break;
            }
        }
        
        return result;
    }

    public static String byteArrToHexStr(byte[] var) {
        String tmp = "";
        String result = "";
        for (int i = 0; i < var.length; i++) {
            tmp = Integer.toHexString(var[i]).toUpperCase();
            if (tmp.length() < 2) {
                tmp = "0" + tmp;
            } else if (tmp.length() > 2) {
                tmp = tmp.substring(tmp.length() - 2);
            }
            result = result + tmp;
        }
        return result;
    }


    /**
     * Replace values to variable in POST XML
     *
     * @param xml
     * @param replace
     * @return
     */
    public static String parseValue(String input, Properties replace) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String result = input;

        Enumeration<Object> key = replace.keys();
        while (key.hasMoreElements()) {
//            log.Debug("RESULT: '" + result + "'");
            String k = (String) key.nextElement();
            String val = replace.getProperty(k);
//            log.Debug("Replace '" + k + "' by '" + val + "'");
            result = result.replace(k, val);
        }
        return result;
    }
    
    public static String parseValue(String input, Map<String,String> replace) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String result = input;
        for (Map.Entry<String, String> entry : replace.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            result = result.replace("#"+key, val);
//            result = result.replace(key, val);
        }
       
        return result;
    }
    
    
    public static Map<String,String> viewOcs(String msisdn, String transId) {
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "<S:Body>\n" +
                "<service>\n" +
                "<username>username</username>\n" +
                "<password>password</password>\n" +
                "<requestId>"+transId+"</requestId>\n" +
                "<messageType>1900</messageType>\n" +
                "<processCode>000053</processCode>\n" +
                "<MSISDN>"+msisdn+"</MSISDN>\n" +
                "</service>\n" +
                "</S:Body>\n" +
                "</S:Envelope>" ;
        SoapClient client = new SoapClient(GlobalConfig.getProsoapUrl());
        return client.sendRequest(xml);
    }
    
    public static Map<String,String> addData(String msisdn, String transId) {
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "<S:Body>\n" +
                "<service>\n" +
                "<username>username</username>\n" +
                "<password>password</password>\n" +
                "<requestId>"+transId+"</requestId>\n" +
                "<messageType>1900</messageType>\n" +
                "<processCode>000053</processCode>\n" +
                "<MSISDN>"+msisdn+"</MSISDN>\n" +
                "</service>\n" +
                "</S:Body>\n" +
                "</S:Envelope>" ;
        SoapClient client = new SoapClient(GlobalConfig.getProsoapUrl());
        return client.sendRequest(xml);
    }
    
    
    public static String sendUssd(String url, String username, String password,
            String msisdn, String content,String shortcode, String ussdgwId,
            int type, String transId, int timeout) {
        if (content == null || content.isEmpty()) {
            log.warn("Ussd is called with Empty SMS");
            return "2";
        }
        String result = "1";
        
        InputStream inp = null;
        try {
            log.info("Begin call ws Ussd...");
            long start = System.currentTimeMillis();
            String soapAction = url;
            
            
            String newContent = StringEscapeUtils.escapeXml(content);    
      
            log.info("url: " + url);
            String xmlInput =
                    "<?xml version=\"1.0\" ?>"
                    + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                    + "<S:Body>"
                    + "<smsRequest xmlns=\"http://smsws/xsd\">"
                    + "<ussdgwUsername>" + username + "</ussdgwUsername>"
                    + "<ussdgwPassword>" + password + "</ussdgwPassword>"
                    + "<msisdn>" + msisdn + "</msisdn>"
                    + "<content>" + newContent + "</content>"
                    + "<ussdgwId>" + ussdgwId + "</ussdgwId>"
                    + "<shortcode>" + shortcode + "</shortcode>"
                    + "<timeout>" + timeout + "</timeout>"
                    + "<type>" + type + "</type>"
                     + "<transId>" + transId + "</transId>"
                    + "</smsRequest>"
                    + "</S:Body>"
                    + "</S:Envelope>";
            
            log.info("----xmlInput------: " + xmlInput);
            String xmlOut = callWS(url, xmlInput, soapAction, 1, HTTP_TIMEOUT);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            inp = new ByteArrayInputStream(xmlOut.getBytes());
            Document doc = db.parse(inp);
            
            result = doc.getElementsByTagName("return").item(0).getTextContent();
            log.info("Send USSD OUT to '" + msisdn + "' -> " + result
                    + " (" + (System.currentTimeMillis() - start) + " ms)");
            
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
        }
        
        return result;
    }
    
    
    public static final String PARAMS_SMS_TEXT = "TEXT";
    public static final String PARAMS_SMS_FLASH = "FLASH";
    public static final String PARAMS_SMS_BINARY = "BINARY";
    public static final String PARAMS_SMS_HEX_TEXT = "HEX_TEXT";
    public static final String PARAMS_SMS_HEX_FLASH = "HEX_FLASH";
    
    public static int sendMT(String url, String username, String password, 
                       String msisdn, String content, String shortCode, String alias, String smsType, int retry) {
        int result = -1;
        if (content == null || content.isEmpty()) {
            log.warn("SendMT is called with Empty SMS");
            return result;
        }
        
        long start = System.currentTimeMillis();
        String soapAction = url;
        String newContent = convertContent(content,true);
        
        String xmlInput = 
                "<?xml version=\"1.0\" ?>" + 
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
                "<S:Body>" + 
                    "<smsRequest xmlns=\"http://smsws/xsd\">" + 
                        "<username>" + username + "</username>" + 
                        "<password>" + password + "</password>" + 
                        "<msisdn>" + msisdn + "</msisdn>" + 
                        "<content>" + newContent + "</content>" + 
                        "<shortcode>" + shortCode + "</shortcode>" + 
//                        "<alias>" + alias + "</alias>" + 
                        "<params>" + PARAMS_SMS_HEX_TEXT + "</params>" + 
                    "</smsRequest>" + 
                "</S:Body>" + 
                "</S:Envelope>";
        try {
            log.debug(xmlInput);
            String xmlOut = callWS(url, xmlInput, soapAction, retry, THIRTY_SECOND_TIMEOUT);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            InputStream inp = new ByteArrayInputStream(xmlOut.getBytes());
            Document doc = db.parse(inp);
            
            result = Integer.parseInt(doc.getElementsByTagName("return").item(0).getTextContent());
            log.info("Send SMS to '" + msisdn + "' -> " + result + 
                    " (" + (System.currentTimeMillis() - start) + " ms)");
        } catch (Exception e) {
            log.error("error when send sms",e);
            result = -1;
        }
        return result;
    }
    
    private static String convertContent(String content, boolean useHex) {
        if (useHex) {
            String charSet = CharsetDetector.detectCharsetStr(content);
            if (charSet != null && !charSet.equals("ASCII")) {
                try {
                    return WbxmlLibs.byteArrToHexStr(content.getBytes(charSet));
                } catch (Exception ex) {
                    return WbxmlLibs.byteArrToHexStr(content.getBytes());
                }
            } else {
                return WbxmlLibs.byteArrToHexStr(content.getBytes());
            }
        } else {
            return content;
        }
    }
}
