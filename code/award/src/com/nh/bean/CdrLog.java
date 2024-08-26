/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "TRANSACTION_LOG")
public class CdrLog extends PO{
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MSISDN).append("|")
                .append(SUB_SERVICE_NAME).append("| response: ")
                .append(RESPONSE_CODE).append("|price: ")
                .append(PRICE).append("|")
                .append(VCGW_REQUEST_ID).append("| cmd: ")
                .append(CMD);
        
        return sb.toString();
    }
 
    @Column(name = "ID")
    public String ID;

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
    @Column(name = "VCGW_REQUEST_ID")
    public String VCGW_REQUEST_ID;

    public void setVCGW_REQUEST_ID(String VCGW_REQUEST_ID) {
        this.VCGW_REQUEST_ID = VCGW_REQUEST_ID;
    }

    public String getVCGW_REQUEST_ID() {
        return VCGW_REQUEST_ID;
    }
    @Column(name = "MSISDN")
    public String MSISDN;

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getMSISDN() {
        return MSISDN;
    }
    @Column(name = "TRANS_ID")
    public String TRANS_ID;

    public void setTRANS_ID(String TRANS_ID) {
        this.TRANS_ID = TRANS_ID;
    }

    public String getTRANS_ID() {
        return TRANS_ID;
    }
    @Column(name = "REQUEST_TIME")
    public Timestamp REQUEST_TIME;

    public void setREQUEST_TIME(Timestamp REQUEST_TIME) {
        this.REQUEST_TIME = REQUEST_TIME;
    }

    public Timestamp getREQUEST_TIME() {
        return REQUEST_TIME;
    }
    @Column(name = "RESPONSE_TIME")
    public Timestamp RESPONSE_TIME;

    public void setRESPONSE_TIME(Timestamp RESPONSE_TIME) {
        this.RESPONSE_TIME = RESPONSE_TIME;
    }

    public Timestamp getRESPONSE_TIME() {
        return RESPONSE_TIME;
    }
    @Column(name = "RESPONSE_CODE")
    public String RESPONSE_CODE;

    public void setRESPONSE_CODE(String RESPONSE_CODE) {
        this.RESPONSE_CODE = RESPONSE_CODE;
    }

    public String getRESPONSE_CODE() {
        if (RESPONSE_CODE.length() > 20) {
            return RESPONSE_CODE.substring(0, 19);
        }
        return RESPONSE_CODE;
    }
    @Column(name = "SERVICE_NAME")
    public String SERVICE_NAME;

    public void setSERVICE_NAME(String SERVICE_NAME) {
        this.SERVICE_NAME = SERVICE_NAME;
    }

    public String getSERVICE_NAME() {
        return SERVICE_NAME;
    }
    @Column(name = "SUB_SERVICE_NAME")
    public String SUB_SERVICE_NAME;

    public void setSUB_SERVICE_NAME(String SUB_SERVICE_NAME) {
        this.SUB_SERVICE_NAME = SUB_SERVICE_NAME;
    }

    public String getSUB_SERVICE_NAME() {
        return SUB_SERVICE_NAME;
    }
    @Column(name = "CMD")
    public String CMD;

    public void setCMD(String CMD) {
        this.CMD = CMD;
    }

    public String getCMD() {
        return CMD;
    }
    @Column(name = "CATEGORY_NAME")
    public String CATEGORY_NAME;

    public void setCATEGORY_NAME(String CATEGORY_NAME) {
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    public String getCATEGORY_NAME() {
        return CATEGORY_NAME;
    }
    @Column(name = "ITEM_NAME")
    public String ITEM_NAME;

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }
    @Column(name = "SUB_CP_NAME")
    public String SUB_CP_NAME;

    public void setSUB_CP_NAME(String SUB_CP_NAME) {
        this.SUB_CP_NAME = SUB_CP_NAME;
    }

    public String getSUB_CP_NAME() {
        return SUB_CP_NAME;
    }
    @Column(name = "CONTENTS")
    public String CONTENTS;

    public void setCONTENTS(String CONTENTS) {
        this.CONTENTS = CONTENTS;
    }

    public String getCONTENTS() {
        return CONTENTS;
    }
    @Column(name = "PROVIDER_NAME")
    public String PROVIDER_NAME;

    public void setPROVIDER_NAME(String PROVIDER_NAME) {
        this.PROVIDER_NAME = PROVIDER_NAME;
    }

    public String getPROVIDER_NAME() {
        return PROVIDER_NAME;
    }
    @Column(name = "PRICE")
    public double PRICE;

    public void setPRICE(double PRICE) {
        this.PRICE = PRICE;
    }

    public double getPRICE() {
        return PRICE;
    }
    @Column(name = "CHARGE_TYPE")
    public int CHARGE_TYPE;

    public void setCHARGE_TYPE(int CHARGE_TYPE) {
        this.CHARGE_TYPE = CHARGE_TYPE;
    }

    public int getCHARGE_TYPE() {
        return CHARGE_TYPE;
    }
    @Column(name = "CHANNEL")
    public String CHANNEL;

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    public String getCHANNEL() {
        return CHANNEL;
    }
}
