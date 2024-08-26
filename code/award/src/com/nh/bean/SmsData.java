/**
 *
 * handsome boy
 */
package com.nh.bean;

import java.util.UUID;

/**
 *
 * @since Dec 12, 2015
 * @mail hadc
 */
public class SmsData {
    private String subServiceName;
    private String url, user, pass, msisdn, content, shortCode, alias, smsType;
    private long sendTime; //affter this time, System will send sms
    private String id;
    private String contentId;
    
    public SmsData(String url, String user, String pass, String msisdn,
            String content, String shortCode, String alias, String smsType) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.msisdn = msisdn;
        this.content = content;
        this.shortCode = shortCode;
        this.alias = alias;
        this.smsType = smsType;
        sendTime = System.currentTimeMillis();
    }

    public String getId(){
        return id;
    }
    
    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getContent() {
        return content;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getAlias() {
        return alias;
    }

    public String getSmsType() {
        return smsType;
    }

    public long getSendTime() {
        return sendTime;
    }
    
    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(msisdn)
                .append("|").append(shortCode)
                .append("|").append(smsType)
                .append("|").append(user)
                .append("|").append(content);
        return sb.toString();
    }

    public void setId(String id) {
        this.id = id;
    }
}
