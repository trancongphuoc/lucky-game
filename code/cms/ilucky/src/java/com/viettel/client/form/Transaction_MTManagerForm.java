/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import com.viettel.utils.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author hanhnv62
 */
public class Transaction_MTManagerForm {
    
    String service_name; 
    String sub_service_name;    
    String short_code;
    String msisdn;
    String contents;
    String trans_id;
    Date datetime;
    Date from_datetime;

    public Transaction_MTManagerForm() {
        datetime = DateTimeUtils.getPreviousMonthDate();
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    
    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getFrom_datetime() {
        return from_datetime;
    }

    public void setFrom_datetime(Date from_datetime) {
        this.from_datetime = from_datetime;
    }

    
    
}
