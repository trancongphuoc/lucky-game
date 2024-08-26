/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * @author hadc
 */
@Table(name = "MT_LOG")
public class MTlog extends PO{
    
    @Column(name = "USER_ID")
    public String  USER_ID;
    
    @Column(name = "SERVICE_ID")
    public String SERVICE_ID;
    
    @Column(name = "MOBILE_OPERATOR")
    public String MOBILE_OPERATOR = "LUMITEL";
    
    @Column(name = "COMMAND_CODE")
    public String COMMAND_CODE;
    
    //21 la nhan tin hang ngay, 0 la tin dang ky
    @Column(name = "CONTENT_TYPE")
    public int CONTENT_TYPE = 21;
    
    @Column(name = "INFO")
    public String INFO;
    
//    @Column(name = "SUBMIT_DATE")
//    public Timestamp SUBMIT_DATE = new Timestamp(System.currentTimeMillis());
    
//    @Column(name = "DONE_DATE")
//    public Timestamp DONE_DATE = new Timestamp(System.currentTimeMillis());
    
    @Column(name = "PROCESS_RESULT")
    public int PROCESS_RESULT = 0;
    
    @Column(name = "MESSAGE_TYPE")
    public int MESSAGE_TYPE = 0;
    
    @Column(name = "REQUEST_ID")
    public BigDecimal REQUEST_ID;
    
    @Column(name = "MESSAGE_ID")
    public String MESSAGE_ID = "0";
    
    @Column(name = "TOTAL_SEGMENTS")
    public int TOTAL_SEGMENTS = 0;
    
    @Column(name = "RETRIES_NUM")
    public int RETRIES_NUM = 0;
    
    @Column(name = "NOTES")
    public String NOTES;
    
    @Column(name = "CP_MO")
    public int CP_MO = 1;
    
    @Column(name = "CP_MT")
    public int CP_MT = 1;
    
    @Column(name = "AMOUNT")
    public BigDecimal AMOUNT = BigDecimal.ZERO;

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
        sb.append(USER_ID).append("|")
                .append(SERVICE_ID).append("|")
                .append(REQUEST_ID).append("|")
                .append(INFO);
        
        return sb.toString();
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getSERVICE_ID() {
        return SERVICE_ID;
    }

    public void setSERVICE_ID(String SERVICE_ID) {
        this.SERVICE_ID = SERVICE_ID;
    }

    public String getMOBILE_OPERATOR() {
        return MOBILE_OPERATOR;
    }

    public void setMOBILE_OPERATOR(String MOBILE_OPERATOR) {
        this.MOBILE_OPERATOR = MOBILE_OPERATOR;
    }

    public String getCOMMAND_CODE() {
        return COMMAND_CODE;
    }

    public void setCOMMAND_CODE(String COMMAND_CODE) {
        this.COMMAND_CODE = COMMAND_CODE;
    }

    public int getCONTENT_TYPE() {
        return CONTENT_TYPE;
    }

    public void setCONTENT_TYPE(int CONTENT_TYPE) {
        this.CONTENT_TYPE = CONTENT_TYPE;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public int getPROCESS_RESULT() {
        return PROCESS_RESULT;
    }

    public void setPROCESS_RESULT(int PROCESS_RESULT) {
        this.PROCESS_RESULT = PROCESS_RESULT;
    }

    public int getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public void setMESSAGE_TYPE(int MESSAGE_TYPE) {
        this.MESSAGE_TYPE = MESSAGE_TYPE;
    }

    public BigDecimal getREQUEST_ID() {
        return REQUEST_ID;
    }

    public void setREQUEST_ID(BigDecimal REQUEST_ID) {
        this.REQUEST_ID = REQUEST_ID;
    }

    public String getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public void setMESSAGE_ID(String MESSAGE_ID) {
        this.MESSAGE_ID = MESSAGE_ID;
    }

    public int getTOTAL_SEGMENTS() {
        return TOTAL_SEGMENTS;
    }

    public void setTOTAL_SEGMENTS(int TOTAL_SEGMENTS) {
        this.TOTAL_SEGMENTS = TOTAL_SEGMENTS;
    }

    public int getRETRIES_NUM() {
        return RETRIES_NUM;
    }

    public void setRETRIES_NUM(int RETRIES_NUM) {
        this.RETRIES_NUM = RETRIES_NUM;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public int getCP_MO() {
        return CP_MO;
    }

    public void setCP_MO(int CP_MO) {
        this.CP_MO = CP_MO;
    }

    public int getCP_MT() {
        return CP_MT;
    }

    public void setCP_MT(int CP_MT) {
        this.CP_MT = CP_MT;
    }

    public BigDecimal getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(BigDecimal AMOUNT) {
        this.AMOUNT = AMOUNT;
    }
    
    
    
}
