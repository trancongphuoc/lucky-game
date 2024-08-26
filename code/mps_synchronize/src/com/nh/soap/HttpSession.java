package com.nh.soap;

import com.nh.Main;
import com.nh.soap.handle.AbstractHandler;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import org.apache.axis.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Handles one session, i.e. parses the HTTPS request
 * and returns the response.
 */
public class HttpSession implements Runnable, HttpSender {

     protected final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HttpSession.class);
    /**
     * GMT date formatter
     */
    private static final java.text.SimpleDateFormat gmtFrmt;
    private final Socket mySocket;
    
 
    static {
        gmtFrmt = new java.text.SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
//    private Queue rx = null;

    /**
     * create new HttpSession with connected socket
     * @param s current connected socket
     */

    public HttpSession(Socket s) {
        this.mySocket = s;
//        this.rx = rx;
    }
    
    
    
    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
    
    
    public SOAPMessage getSoapBody(String content) {
        org.apache.axis.Message message = new Message(content);
//        String value = message.getContentDescription();
        MimeHeaders headers = message.getMimeHeaders();
        headers.addHeader("Accept-Charset", "utf-8");
        headers.addHeader("Content-Type", "text/xml");
        headers.addHeader("Accept", "application/soap+xml");
        headers.addHeader("Content-Length", String.valueOf(content.length()));
        return message;
    }

    InputStream is = null;
    /**
     * Main process when receiver request from VAS CP.
     *
     */
    @Override
    public void run() {
        
        String s = null;
        try {
            is = mySocket.getInputStream();
            if (is == null) {
                return;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            s = in.readLine();
            if(s==null) 
            {
                Thread.sleep(200);
                s = in.readLine();
            }
            String ipclient = ((InetSocketAddress)mySocket.getRemoteSocketAddress()).getAddress().getHostAddress();
            if (s== null) {
                System.out.println("ip send null package: " + ipclient);
                return;
            }
            StringTokenizer st = new StringTokenizer(s);
            if (!st.hasMoreTokens()) {
                sendError(HttpResponse.HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
            }
            String method = st.nextToken();
            if (!st.hasMoreTokens()) {
                sendError(HttpResponse.HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
            }
            String _uri;
            try {
                _uri = st.nextToken();
            } catch (java.util.NoSuchElementException ex) {
                sendError(HttpResponse.HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                return;
            }
            String uri = _uri;
            Map<String,String> parms = new HashMap<>();
            int qmi = uri.indexOf('?');
            if (qmi >= 0) {
                decodeParms(uri.substring(qmi + 1), parms);
                uri = decodePercent(uri.substring(0, qmi));
            }
            /************************************ PROCESS TO CLEINT ******************************/
            // If there's another token, it's protocol version,
            // followed by HTTP headers. Ignore version but parse headers.
            // NOTE: this now forces header names uppercase since they are
            // case insensitive and vary by client.
            Properties header = new Properties();
            if (st.hasMoreTokens()) {
                String line = in.readLine();
                while (line.trim().length() > 0) {
                    int p = line.indexOf(':');
                    header.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
                    line = in.readLine();
                }
            }
            
            String contentType = header.getProperty("content-type");
            boolean json = HttpResponse.MIME_JSON.equalsIgnoreCase(contentType);  
                
            String postLine = "";
            if (method.equalsIgnoreCase("POST")) {
                int size = 0x7FFF;
                String contentLength = header.getProperty("content-length");
                if (contentLength != null) {
                    try {
                        size = Integer.parseInt(contentLength);
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(HttpSession.class.getName()).log(Level.SEVERE, null, ex);
                        //ignore this exception
                    }
                }
                char buf[] = new char[size];
                int read = in.read(buf);
                while (read >= 0 && size > 0 && !postLine.endsWith("\r\n")) {
                    size -= read;
                    postLine += String.valueOf(buf, 0, read);
                    if (postLine.trim().endsWith(":Envelope>")
                            || postLine.trim().endsWith(":envelope>")) { //ket thuc bang </S:Envelope>
                        break;
                    }
                    if (size > 0) {
                        read = in.read(buf);
                    }
                }
                postLine = postLine.trim();
//              
                log.info("receive: " + postLine + ", content-type: " + contentType);
                decodeParmPosts(postLine, parms, json);
                boolean uriFound = false;
                for(String url: Main.mapHandle().keySet()) {
                    if (uri.startsWith(url)) {
                        String handlerClazzName = Main.mapHandle().get(url);
                        SoapRequest request = new SoapRequest(method, uri, header, parms, this);
                        request.setClientIp(ipclient);
                        request.setRawXml(postLine);
                        request.setHandlerClazzName(handlerClazzName);
                        uriFound = true;
                        try {
                            Class<?> clazz =  Class.forName(handlerClazzName);
                            Constructor<?> cto = clazz.getConstructor(SoapRequest.class);
                            AbstractHandler hander = (AbstractHandler) cto.newInstance(request);
                            String value = hander.process(request);
//                            sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header , result);
                            String result = value;
                            if (!value.startsWith("<") && !value.startsWith("{")) {
                                result = json ? RESPONSE_JSON : RESPONSE_XML;
                                result = result.replace("#RETURN", value);
                                sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header , result);
                                return;
                            }  
                            sendRequestResultToClient(result, header ,json);
                        } catch (Exception ex) {
                            Logger.getLogger(HttpSession.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        break;
                    }
                }
                
                if (!uriFound) {
                    sendError(HttpResponse.HTTP_NOTFOUND, "OPP! not found");
                }
            } else if (method.equals("GET")) {
                boolean uriFound = false;
                for(String url: Main.mapHandle().keySet()) {
                    if (uri.startsWith(url)) {
                        String handlerClazzName = Main.mapHandle().get(url);
                        SoapRequest request = new SoapRequest(method, uri, header, parms, this);
                        request.setClientIp(ipclient);
                        request.setHandlerClazzName(handlerClazzName);
                        uriFound = true;
                        request.setRawXml(url);
                        try {
                            Class<?> clazz =  Class.forName(handlerClazzName);
                            Constructor<?> cto = clazz.getConstructor(SoapRequest.class);
                            AbstractHandler hander = (AbstractHandler) cto.newInstance(request);
                            String result = hander.process(request);
//                            sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header , result);
                            result = sendResultResponseToClient(result);
                            sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header , result);
                        } catch (Exception ex) {
                            Logger.getLogger(HttpSession.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        
                        break;
                    }
                }
                
                if (!uriFound) {
                    sendError(HttpResponse.HTTP_NOTFOUND, "OPP! not found");
                }
            } else {
                sendError(HttpResponse.HTTP_BADREQUEST, "not support");
            }

        } catch (IOException | InterruptedException ioe) {
            try {
                sendError(HttpResponse.HTTP_SOAP_ERROR, "SERVER INTERNAL LOG_ERROR, logName: IOException: " + ioe.getMessage());
            } catch (Exception ex) {
            }

        } catch (Exception ex) {
            Logger.getLogger(HttpSession.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
     
    public void decodeParmPosts(String content, Map<String, String> datas, boolean json) {
        if (json) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject jsondata = (JSONObject) parser.parse(content);

                java.util.Set<String> keys = jsondata.keySet();
                for (String tmkey : keys) {
                    Object obj = jsondata.get(tmkey);
                    if (obj != null) {
                        String value = String.valueOf(obj);
                        datas.put(tmkey, value);
                    }
                }
            } catch (Exception e) {
            }
        } else {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;
                db = dbf.newDocumentBuilder();
                InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
                Document document = db.parse(inputStream);

                Element root = document.getDocumentElement();

                convertSOAPBodyToProperties2(root, datas);
                inputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    private void convertSOAPBodyToProperties(Node element, Map<String,String> pro) {
        NodeList nodeList = element.getChildNodes();
        int length = nodeList.getLength();
        for (int j = 0; j < length; j++) {
            Node node = nodeList.item(j);
            if (node == null) {
                continue;
            }
            String name = node.getNodeName();
            String value = node.getTextContent();
            boolean isValue = node.getChildNodes().getLength() == 1;
            
            if (Node.ELEMENT_NODE == node.getNodeType() && isValue && name != null && value != null) {
                    pro.put(name, value);
            } else {
                convertSOAPBodyToProperties(node, pro);
            }
        }
    }
    
    private static void convertSOAPBodyToProperties2(Node element, Map<String,String> pro) {
        NodeList nodeList = element.getChildNodes();
        int length = nodeList.getLength();
        for (int j = 0; j < length; j++) {
            Node node = nodeList.item(j);
            if (node == null) {
                continue;
            }
            String name = node.getNodeName();
            String value = node.getTextContent();
            boolean isValue = false;
            if (node.getChildNodes() == null 
                    || node.getChildNodes().getLength() == 0) {
                isValue = true;
            } else if(node.getChildNodes().getLength() == 1
                    && Node.TEXT_NODE == node.getChildNodes().item(0).getNodeType()) {
               isValue = true;
            }
                    
            if (Node.ELEMENT_NODE == node.getNodeType() && isValue && name != null && value != null) {
//                    String tmValue = StringEscapeUtils.unescapeHtml4(value);
                    pro.put(name, value);
            } else {
                convertSOAPBodyToProperties2(node, pro);
            }
        }
    }

    /**
     * Decodes the percent encoding scheme. <br/>
     * For example: "an+example%20string" -> "an example string"
     * @param str <code>String</code> value will be decoded
     * @return value after decode
     * @throws InterruptedException when processing in String value, may be out of index bound,...
     */
    private String decodePercent(
            String str) throws InterruptedException {
        try {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i <
                    str.length(); i++) {
                char c = str.charAt(i);
                switch (c) {
                    case '+':
                        sb.append(' ');
                        break;
                    case '%':
                        sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                        i +=
                                2;
                        break;

                    default:

                        sb.append(c);
                        break;
                }
            }
            return new String(sb.toString().getBytes());
        } catch (Exception e) {
            sendError(HttpResponse.HTTP_BADREQUEST, "BAD REQUEST: Bad percent-encoding.");
            return null;
        }

    }

    /**
     * Decodes parameters in percent-encoded URI-format
     * ( e.g. "name=Jack%20Daniels&pass=Single%20Malt" ) and
     * adds them to given Properties.
     * @param parms param string will be parse
     * @param p stored properties to store value
     * @throws InterruptedException when processing in String value, may be out of index bound,...
     */
    private void decodeParms(String parms, Map<String,String> p)
            throws InterruptedException {
        if (parms == null) {
            return;
        }
        StringTokenizer st = new StringTokenizer(parms, "&");
        while (st.hasMoreTokens()) {
            String e = st.nextToken();
            int sep = e.indexOf('=');
            if (sep >= 0) {
//                p.put(decodePercent(e.substring(0, sep)).trim(),
//                        decodePercent(e.substring(sep + 1)));
                p.put(e.substring(0, sep).trim(),e.substring(sep + 1));
            }
        }
    }

    /**
     * Returns an error message as a HTTP response and
     * throws InterruptedException to stop furhter request processing.
     * @param status status of http processing
     * @param msg http message content
     */
    @Override
    public void sendError(String status, String msg) {
        sendResponse(status, HttpResponse.MIME_HTML, null, new ByteArrayInputStream(msg.getBytes()));
    }

    /**
     * Sends given response to the socket.
     * @param status status of http processing
     * @param  mime content type of response
     * @param header header values
     * @param data <code>InputStream</code> stored content of response
     */
    @Override
    public void sendResponse(String status, String mime, Properties header, InputStream data) {
        try {
            if (mySocket.isClosed()) {
                return;
            }
            if (status == null) {
                throw new Error("sendResponse(): Status can't be null.");
            }
            OutputStream out = mySocket.getOutputStream();
            PrintWriter pw = new PrintWriter(out);
            pw.print("HTTP/1.0 " + status + " \r\n");
            if (mime != null) {
            }
            if (header == null || header.getProperty("Date") == null) {
                pw.print("Date: " + gmtFrmt.format(new Date().getTime()) + "\r\n");
            }
            if (header != null) {
                Enumeration e = header.keys();                
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    String value = header.getProperty(key);
                    pw.print(key + ": " + value + "\r\n");
                }                
            }
            pw.print("\r\n");
            pw.flush();
            if (data != null) {
                byte[] buff = new byte[2048];
                StringBuffer sbuf = new StringBuffer();
                while (true) {
                    int read = data.read(buff, 0, 2048);
                    if (read <= 0) {
                        break;
                    }

                    int i = 0;
                    for (i = 0; i <
                            buff.length; i++) {
                        if (buff[i] == 0) {
                            break;
                        }
                    }
                    if (i > 0) {
                        sbuf.append(new String(buff, 0, i));
                    }
                    out.write(buff, 0, read);
                }
            }
            out.flush();
            out.close();
            if (data != null) {
                data.close();
            }
        } catch (IOException ioe){
            Logger.getLogger(HttpSession.class.getName()).log(Level.SEVERE, null, ioe);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                mySocket.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public HttpResponse serve(
            String uri, String method, Properties header, String content) throws Exception {
        Enumeration e = header.propertyNames();
        String host = header.getProperty("host");
        int length = host.indexOf(":");
        String wsdl = host.substring(length + 1);
        
        if (method.equals("GET")) {
            String  hostPort = "localhost" + ":" + wsdl;
            HttpResponse response = new HttpResponse(HttpResponse.HTTP_OK, HttpResponse.MIME_XML, hostPort);
            return response;
        }
        return null;
    }

    protected String sendResultResponseToClient(String result) {
            String resp = "<?xml version=\"1.0\"?>"
                    + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" >"
                    + "<S:Body>"
                    + "<response xmlns=\"http://mocom/xsd\">"
                    + "<return>" + result
                    + "</return>"
                    + "</response>"
                    + "</S:Body>"
                    + "</S:Envelope>";

         return resp; 
    }
    
     private static final String RESPONSE_XML = "<?xml version=\"1.0\"?>"
            + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" >"
            + "<S:Body>"
            + "<response xmlns=\"http://ws/xsd\">"
            + "<return>#RETURN</return>"
            + "</response>"
            + "</S:Body>"
            + "</S:Envelope>";
    private static final String RESPONSE_JSON = "{\"return\": \"#RETURN\"}";
    
    protected void sendRequestResultToClient(String result,Properties header, boolean json) {
        try {
//            log.Debug(": response RESULT to client " + result );

            String resp = json ? RESPONSE_JSON : RESPONSE_XML;
            if (result.endsWith(":Envelope>")) {
                resp = result;
            } else {
                resp = resp.replace("#RETURN", result);
            }
//            String prefix = mPrefix == null ? "": mPrefix;
//            resp = resp.replace("#PREFIX", prefix);
           

            String mimeType = json ? HttpResponse.MIME_JSON : HttpResponse.MIME_XML;

            InputStream inp = new ByteArrayInputStream(resp.getBytes());
            HttpResponse response = new HttpResponse(HttpResponse.HTTP_OK, mimeType, inp);
            response.addHeader("content-length", "" + inp.available());
//            sendResponse(HttpResponse.HTTP_OK, mimeType, response.header, response.data);
            sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header , result);
        } catch (Exception e) {
//            MyLog.Error(": ERROR while sending result to client: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendResponse(String status, String mime, Properties header, String data) {
        String response = String.valueOf(data);

        header.put("content-length", "" + response.getBytes().length);
        sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header,
                new ByteArrayInputStream(response.getBytes()));
    }

    @Override
    public void sendResponse(String msg) {
        Properties header = new Properties();
        byte[] bytes = msg.getBytes();
        header.put("content-length", "" + bytes.length);
        
        sendResponse(HttpResponse.HTTP_OK, HttpResponse.mimeType, header, new ByteArrayInputStream(bytes));
    }

    @Override
    public boolean isClose() {
        return mySocket == null || mySocket.isClosed();
    }
    
}
