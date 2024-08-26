
package com.nh.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "CP_WEBSERVICE_ACCOUNTS")
public class CPWebservice extends PO{
    private String webserviceID;
    private String subserviceName;
    private String userName; 
    private String password;
    private String url; 
    private String params;
    private String webserviceType;
    private String webserviceName;
    private String soapAction;
    private String prefix;
    private String rawXML;
    private String returnTag;
    private String dataSign;
    private int timeout;

    public CPWebservice() {
       
    }
    
    @Override
    @Id		
    @Column(name = "WEBSERVICE_ID")
    public String getId() {
        return webserviceID;
    }

    public void setWebserviceID(String webserviceID) {
        this.webserviceID = webserviceID;
    }

    @Column(name = "SUB_SERVICE_NAME")
    public String getSubserviceName() {
        return subserviceName;
    }

    public void setSubserviceName(String subserviceName) {
        this.subserviceName = subserviceName;
    }

    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "PARAMS")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Column(name = "WEBSERVICE_TYPE")
    public String getWebserviceType() {
        return webserviceType;
    }

    public void setWebserviceType(String webserviceType) {
        this.webserviceType = webserviceType;
    }

    @Column(name = "SOAP_ACTION")
    public String getSoapAction() {
        return soapAction;
    }

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    @Column(name = "TAG_PREFIX")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

     @Column(name = "RAW_XML")
    public String getRawXML() {
        return rawXML;
    }

    public void setRawXML(String rawXML) {
        this.rawXML = rawXML;
    }

     @Column(name = "RETURN_TAG")
    public String getReturnTag() {
        return returnTag;
    }

    public void setReturnTag(String returnTag) {
        this.returnTag = returnTag;
    }

    @Column(name = "DATA_SIGN")
    public String getDataSign() {
        return dataSign;
    }

    public void setDataSign(String dataSign) {
        this.dataSign = dataSign;
    }

    @Column(name = "TIMEOUT")
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        if (timeout < 1000) {
            timeout = 30000; // 30s
        }
        this.timeout = timeout;
    }

    @Column(name = "WEBSERVICE_NAME")
    public String getWebserviceName() {
        return webserviceName;
    }

    public void setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
    }
    
    
    
    @Override
    public String toString() {
        return "SubService: " + getSubserviceName()+ "; " +
               "URL: " + getUrl() + "; " ;
              
    }
}
