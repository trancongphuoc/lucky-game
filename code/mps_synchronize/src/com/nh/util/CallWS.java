/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xml.sax.SAXException;

/**
 *
 */
public class CallWS {
    public static final int HTTP_TIMEOUT = 30 * 1000; //ms
    private static final Logger log = Logger.getLogger(CallWS.class);
    
    public static final String WS_USERNAME = "#USERNAME";
    public static final String WS_PASSWORD = "#PASSWORD";
    public static final String WS_SERVICE_NAME = "#SERVICE_NAME";
    public static final String WS_MSISDN = "#MSISDN";
    public static final String WS_ISDN = "#ISDN";
    public static final String WS_CHARGE_TIME = "#CHARGE_TIME";
    public static final String WS_MODE = "#MODE";
    public static final String WS_PARAMS = "#PARAMS";
    public static final String WS_AMOUNT = "#AMOUNT";
    public static final String WS_COMMAND = "#FULLSMS";
    public static final String WS_SMS_ITEM = "#SMS_ITEM";
    public static final String WS_TRANS_ID = "#TRANS_ID";
    public static final String WS_RECENT = "#RECENT";
    
    
    public static final String MIME_PLAINTEXT = "text/plain", MIME_HTML = "text/html", MIME_DEFAULT_BINARY = "application/octet-stream", MIME_XML = "text/xml";
    public static final String MIME_JSON = "application/json";
    public static final String WEBSERVICE_TYPE_SOAP = "SOAP";
    
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAILED = 1;
    public static final int RESULT_TIMEOUT = -1;
    
     // KEY COMMAND
//    public static final String END_LINE = "\n";
//    public static final String CMD_AND = "^";
    public static final String CMD_START_CONTEXT = "#";
//    public static final String CMD_START = "@";
//    public static final String CMD_CTX = "?";
//    public static final String CMD_COLON = ":";
//    public static final String CMD_COMMA = ",";
    

//    public static final String CMD_SQL = "@SQL";
//    public static final String CMD_WS = "@WS";
//    public static final String CMD_JS = "@JS";
//    public static final String CMD_SMS = "@SMS";

    //  KEY COMMAND
//    public static final String CMD_SAVE = "@SAVE";
//    public static final String CMD_HTTP = "@HTTP";
//    public static final String CMD_HTTP_MENU = "@HTTP_MENU";
//    public static final String CMD_MENU = "@MENU";
//    public static final String CMD_SQLX = "@SQLX";
//    public static final String CMD_SQL_LOAD = "@SQL_LOAD";
//    public static final String CMD_SQL_MENU = "@SQL_MENU";
//    public static final String CMD_HEX = "@HEX";
//    public static final String CMD_CLEAR = "@CLEAR";

    // STATUS USSD RESPONSE
    public static final String RESPONSE_OK = "OK";
    public static final String RESPONSE_NOK = "NOK";
    public static final String RESPONSE_ERROR = "ERROR";

    public static final String WS_MODE_CHECK = "CHECK";
    public static final String WS_MODE_REAL = "REAL";

    /*danh cho OCS huy sub, gui cdr cho MPS*/
    public static final String CHANNEL_OCS = "OCS";
    //    
    // ***********************************************************************
    // Replacement for calling CP Webservices
    // ***********************************************************************
    public static final String WS_PROMOTION = "#PROMOTION";
    public static final String WS_CHANEL = "#CHANNEL"; // check trang thai la sub hay chua, danh cho ivr va ussd
    public static final String WS_SUB_NEW = "#SUB_NEW"; // true lan dau dang ky, false no

    public static final String WS_DATETIME = "#yyyyMMddHHmmss";
    
    public static final int THIRTY_SECOND_TIMEOUT = 30 * 1000; // ms
    public static final int CHARGING_TIMEOUT = 120 * 1000; //ms

    private static String callWS(String wsURL, String inputXML, String soapAction, int retry, int timeout) {
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

    /**
     * Auto convert content to HEX or not
     *
     * @param content
     * @return
     */
    private static String convertContent(String content, boolean useHex) {
        if (useHex) {                      
                String charSet = CharsetDetector.detectCharsetStr(content);
                if (charSet != null && !charSet.equals("ASCII")) {
                    try {
                        return byteArrToHexStr(content.getBytes(charSet));
                    } catch (Exception ex) {
                        return byteArrToHexStr(content.getBytes());
                    }
                } else {
                    return byteArrToHexStr(content.getBytes());
                }
        } else {
            return content;
        }
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
                        "<alias>" + alias + "</alias>" + 
                        "<params>" + PARAMS_SMS_HEX_TEXT + "</params>" + 
                    "</smsRequest>" + 
                "</S:Body>" + 
                "</S:Envelope>";
        try {
            String xmlOut = callWS(url, xmlInput, soapAction, retry, 30000);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            InputStream inp = new ByteArrayInputStream(xmlOut.getBytes());
            Document doc = db.parse(inp);
            
            result = Integer.parseInt(doc.getElementsByTagName("return").item(0).getTextContent());
            log.debug("Send SMS to '" + msisdn + "' -> " + result + 
                    " (" + (System.currentTimeMillis() - start) + " ms)");
        } catch (Exception e) {
            log.error("error when send sms",e);
            result = -2;
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
            String k = entry.getKey();
            String val = entry.getValue();
            result = result.replace(k, val);
        }
        return result;
    }
    
    
    public static String callHttpWS(String wsURL, String body, int retry, int timeout, String mimeType, Map<String,String> headers) {
        String result = "";
        String responseString;

        int i = 0;
        for (i = 0; i < retry; i++) {
            MyLog.Debug("Try callHttpWS " + i + " Call WS (" + wsURL + ") got XML input: " + body);
            OutputStream out = null;
            try {
                URL url = new URL(wsURL);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                bout.write(body.getBytes());

                byte[] b = bout.toByteArray();
                // Set the appropriate HTTP parameters.
                httpConn.setReadTimeout(timeout); // ms
                httpConn.setConnectTimeout(timeout); // ms
                httpConn.setRequestProperty("Accept", "application/json");
                httpConn.setRequestProperty("Accept-Language","en");
                httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
                httpConn.setRequestProperty("Content-Type", mimeType);
                if (headers != null && !headers.isEmpty()) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        httpConn.setRequestProperty(key, value);
                    }
                }
//                httpConn.setRequestProperty("SOAPAction", soapAction);

                httpConn.setRequestMethod("POST");
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                out = httpConn.getOutputStream();
                //Write the content of the request to the outputstream of the HTTP Connection.
                out.write(b);

                //Ready with sending the request.
                //Read the response.
                InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
                BufferedReader in = new BufferedReader(isr);

                //Write the SOAP message response to a String.
                while ((responseString = in.readLine()) != null) {
                    result += responseString;
                }
            } catch (Exception ex) {
                MyLog.Error(body);
                MyLog.Error(ex.getMessage(),ex);
                result = "";
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception e) {
                    MyLog.Error(e);
                }
            }

            if (!result.isEmpty()) {
                break;
            }
        }
        MyLog.Debug("Try " + i + " Call WS got Response: '" + result + "'");

        return result;
    }
    
      // <editor-fold defaultstate="collapsed" desc="getValueObject"> 
    public static Object getValueObject(String data, boolean json) {
        Object returnValue = null;
        if (json) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject jsondata = (JSONObject) parser.parse(data);
                return jsondata;
            } catch (Exception ex) {
                MyLog.Error("Exception decode params. ", ex);
            }
        } else {
            InputStream inp = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
                dbf.setFeature(FEATURE, true);

                FEATURE = "http://xml.org/sax/features/external-general-entities";
                dbf.setFeature(FEATURE, false);

                FEATURE = "http://xml.org/sax/features/external-parameter-entities";
                dbf.setFeature(FEATURE, false);

                FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
                dbf.setFeature(FEATURE, false);

                dbf.setXIncludeAware(false);
                dbf.setExpandEntityReferences(false);

                DocumentBuilder db = dbf.newDocumentBuilder();
                // parse using builder to get DOM representation of the XML file
                inp = new ByteArrayInputStream(data.getBytes());
                Document doc = db.parse(inp);
                doc.getDocumentElement().normalize();
                returnValue = doc;
            } catch (ParserConfigurationException ex) {
                MyLog.Error("ParserConfigurationException decode params. ", ex);
            } catch (SAXException ex) {
                MyLog.Error("SAXException decode params. ", ex);
            } catch (Exception ex) {
                MyLog.Error("Exception decode params. ", ex);
            } finally {
                try {
                    if (inp != null) {
                        inp.close();
                    }
                } catch (Exception ex) {
                    MyLog.Error(ex);
                }
            }
        }

        return returnValue;
    }
    // </editor-fold>
    
    public static String getValue(Object map, String key, String defaulValue) {
        return getValue(map, key, defaulValue, null);
    }
    
    public static String getValue(Object map, String key, String defaulValue, Map<String,String> response) {
        if (map instanceof JSONObject) {
            JSONObject jsondata = (JSONObject) map;
            Object obj = jsondata.get(key);
            if (obj != null) {
                String value = String.valueOf(obj);
                return value;
            }
            if (response != null) {
                java.util.Set<String> keys = jsondata.keySet();
                for (String tmkey : keys) {
                    obj = jsondata.get(tmkey);
                    if (obj != null) {
                        String value = String.valueOf(obj);
                        response.put(key, value);
                    }
                }
            }
        } else if (map instanceof Document) {
            Document doc = (Document) map;
            if (doc.getElementsByTagName(key) != null
                    && doc.getElementsByTagName(key).item(0) != null
                    && doc.getElementsByTagName(key).item(0).hasChildNodes()) {
//                String value = doc.getElementsByTagName(key).item(0).getFirstChild().getTextContent();
                String value = doc.getElementsByTagName(key).item(0).getFirstChild().getNodeValue();
                if (value != null) {
                    value = value.trim();
                }
                return value;
            }
        }

        return defaulValue;
    }
    
    
    private static String callHttpSoap(String wsURL, String inputXML, int retry, int timeout, String returnTag) {
         return callHttpSoap(wsURL, inputXML, retry, timeout, returnTag, null);
    }

    private static String callHttpSoap(String wsURL, String inputXML, int retry, int timeout, String returnTag, Map<String, String> response) {
        int i = 0;
        String result = null;
        for (i = 0; i < retry; i++) {
            MyLog.Debug("Try " + i + " Call WS (" + wsURL + ") got XML input: " + inputXML);
            SoapClient client = new SoapClient(wsURL.trim());
            Map<String, String> datas = client.sendRequest(inputXML, timeout);
            MyLog.Debug("inputXML = = " + inputXML);
            MyLog.Debug("datas = " + datas);
            if (datas != null && datas.size() > 0) {
                if (datas.containsKey(returnTag)) {
                    result = datas.get(returnTag);
                } else {
                    MyLog.Error("have result from cp, but do not have tag: " + returnTag);
                    for (Map.Entry<String, String> entry : datas.entrySet()) {
                        String string = entry.getKey();
                        String string1 = entry.getValue();
                        MyLog.Error(string + " => " + string1);
                    }
                }
                
                if (response != null) {
                    for (Map.Entry<String, String> entry : datas.entrySet()) {
                        String string = entry.getKey();
                        String string1 = entry.getValue();
                        response.put(string, string1);
                    }
                }
            }

            if (result != null && !result.isEmpty()) {
                break;
            }
        }

        return result;
    }

}
