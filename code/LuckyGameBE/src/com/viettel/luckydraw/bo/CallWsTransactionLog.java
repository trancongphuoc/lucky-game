package com.viettel.luckydraw.bo;

import java.io.Serializable;


import java.util.Date;

public class CallWsTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String channel;

//	@Column(name = "\"COMMAND\"")
    private String command;

//	@Column(name = "ERROR_CODE")
    private String errorCode;

    private String msisdn;

    private String request;

//	@Temporal(TemporalType.DATE)
//	@Column(name = "REQUEST_TIME")
    private Date requestTime;

    private String response;

    private Date responseTime;

//	@Column(name = "RESULT_VALUE")
    private String resultValue;

//        @Column(name = "EXTRA_INFO")
    private String extraInfo;

    private  long duration;

    public CallWsTransactionLog() {
    }

    public String getChannel() {
        return this.channel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsisdn() {
        return this.msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRequest() {
        return this.request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Date getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResultValue() {
        return this.resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
