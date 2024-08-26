/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

/**
 *
 * @author manhnv23
 */
public class RequestBccsForm {
    String requestTime;
    String requestType;
    String msisdn;
    String accId;
    String value;
    String reset;
    String addExpire;

    public String getAddExpire() {
        return addExpire;
    }

    public void setAddExpire(String addExpire) {
        this.addExpire = addExpire;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getMsisdn() {
        return msisdn;
        
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
