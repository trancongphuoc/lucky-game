/**
 *
 * handsome boy
 */

package com.nh.soap;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 *
 */
public class SoapRequest {
    public static final int TIME_OUT = 45000;//45 seconds
    private String uri;
    private String method;
    private String rawXml;
    private String clientIp;
    private Properties header;
    private Map<String,String> datas;
    private HttpSender sender;
    private final String soapRequestId;
    private final Long requestTime;
    
    private String handlerClazzName;
    
    public SoapRequest(String method, String uri, Properties header, Map<String,String> datas, HttpSender sender){
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

    public String getRawXml() {
        return rawXml;
    }

    public void setRawXml(String rawXml) {
        this.rawXml = rawXml;
    }

    public String getValue(String key, String defaultValue) {
        String value = defaultValue;
        String tm = datas.get(key);
        if (tm == null) {
            return defaultValue;
        }
        value = tm;
        
        return value ;
    }
    
}
