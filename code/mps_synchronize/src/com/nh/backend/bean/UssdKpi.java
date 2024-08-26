/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

import com.nh.GlobalConfig;
import java.sql.Timestamp;
import java.util.UUID;

/**
 *
 */
public class UssdKpi {
    public static final String ERROR_CODE_OK = "OK";
    public static final String ERROR_CODE_FAIL = "FAIL";
    public static final String ERROR_CODE_KPI = "KPI";
    public static final String ERROR_CODE_ERROR = "ERROR";
    
    public static final String CHANNEL_DATABASE = "DATABASE";
    public static final String CHANNEL_HTTP = "HTTP";
    public static final String CHANNEL_MPS = "MPS";

    private String id ;
    private String transId;
    private String moduleCode;
    private String channel;
    private String msisdn;
    private String action;
    private long duration;
    private String url;
    private String body;
    private String response;
    private String description;
    private String errorCode;
    private Timestamp responseTime;
    private Timestamp requestTime;
    private int stateId;
    private String steps;
    private String nodeIp;

    public UssdKpi(String transId, String msisdn) {
        this.transId = transId;
        this.msisdn = msisdn;
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();
        this.nodeIp = GlobalConfig.getIPServer();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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
        if (response != null && response.length() > 2000) {
            response = response.substring(0, 2000);
        }
        this.response = response;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
    
    public String getIpNode(){
        return nodeIp;
    }

    @Override
    public String toString() {
        return getTransId()+"|"+getMsisdn();
    }
    
    
}
