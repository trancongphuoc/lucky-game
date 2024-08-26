/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import com.viettel.utils.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author TRIEUNGUYEN
 */
public class SearchLogUssdKpiForm {
 
    String msisdn;
    Date fromDate;
    Date toDate;
    String channel;
    String type;

    public SearchLogUssdKpiForm() {
        fromDate = DateTimeUtils.getToDay();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

//    public String getFromDate() {
//        return fromDate;
//    }
//
//    public void setFromDate(String fromDate) {
//        this.fromDate = fromDate;
//    }
//
//    public String getToDate() {
//        return toDate;
//    }
//
//    public void setToDate(String toDate) {
//        this.toDate = toDate;
//    }
    
}
