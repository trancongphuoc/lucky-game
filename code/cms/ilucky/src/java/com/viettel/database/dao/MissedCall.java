/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author TRIEUNGUYEN
 */
public class MissedCall implements Serializable{
    private String transId;
    private String calling;
    private String called;
    private Timestamp requestTime;
    private Timestamp created;
    private String errorCode;

    public MissedCall(String transId, String calling, String called, Timestamp requestTime, Timestamp created, String errorCode) {
        this.transId = transId;
        this.calling = calling;
        this.called = called;
        this.requestTime = requestTime;
        this.created = created;
        this.errorCode = errorCode;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getCalling() {
        return calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    
    
}
