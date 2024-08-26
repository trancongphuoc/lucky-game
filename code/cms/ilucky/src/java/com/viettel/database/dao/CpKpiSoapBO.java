/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author TRIEUNGUYEN
 */
public class CpKpiSoapBO implements Serializable{
    private String id;
    private String providerName;
    private String subServiceName;
    private String channel;
    private String transId;
    private String msisdn;
    private String action;
    private Long duration;
    private String url;
    private String body;
    private String response;
    private String sentTime;
    private Timestamp created;
    private String description;
    private String wsMode;
    private String errorCode;
    private Date requestTime;

    public CpKpiSoapBO() {
    }

    public CpKpiSoapBO( String subServiceName, String providerName, String channel, String transId, String msisdn, String action, Long duration, String url, String body, String response, Date requestTime, String description,String errorCode ) {     
        this.providerName = providerName;
        this.channel = channel;
        this.transId = transId;
        this.msisdn = msisdn;
        this.action = action;
        this.duration = duration;
        this.url = url;
        this.body = body;
        this.response = response;      
        this.description = description;
        this.errorCode = errorCode;
        this.requestTime = requestTime;
        this.subServiceName = subServiceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWsMode() {
        return wsMode;
    }

    public void setWsMode(String wsMode) {
        this.wsMode = wsMode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    
   
}
