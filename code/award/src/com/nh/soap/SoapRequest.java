/**
 *
 * handsome boy
 */

package com.nh.soap;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.jdbc.support.incrementer.H2SequenceMaxValueIncrementer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author hadc <Apllication Development Department - Viettel Global>
 * @since Jun 12, 2015
 * @mail hadc@viettel.com.vn
 */
public class SoapRequest {
    public static final int TIME_OUT = 45000;//45 seconds
    private String uri;
    private String method;
    private String clientIp;
    private String originData;
    private Properties header;
    private Map<String,String> datas;
    private HttpSender sender;
    private final String soapRequestId;
    private final Long requestTime;
    
    private String handlerClazzName;
    
    public SoapRequest(String method, String uri, Properties header, Map datas, HttpSender sender){
        UUID uuid = UUID.randomUUID();
        soapRequestId = uuid.toString();
        this.method = method;
        this.uri = uri;
        this.header = header;
        this.datas = datas;
        this.sender = sender;
        this.requestTime = System.currentTimeMillis();
    }
    
    public String getSoapRequestId() {
        return soapRequestId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Properties getHeader() {
        return header;
    }

    public void setHeader(Properties header) {
        this.header = header;
    }

    public Map<String,String> getDatas() {
        return datas;
    }
    
    public HttpSender getSender() {
        return sender;
    }

    public void setSender(HttpSender sender) {
        this.sender = sender;
    }

    public String getHandlerClazzName() {
        return handlerClazzName;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getOriginData() {
        return originData;
    }

    public void setOriginData(String originData) {
        this.originData = originData;
    }

    @Override
    public String toString() {
        return this.originData;
    }
    
    public Map<String,String> decode(String content ) {
        Map<String,String> datas = new HashMap<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            Document document = db.parse(inputStream);
            Element root = document.getDocumentElement();
            convertSOAPBodyToProperties(root,datas);
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return datas;
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

}
