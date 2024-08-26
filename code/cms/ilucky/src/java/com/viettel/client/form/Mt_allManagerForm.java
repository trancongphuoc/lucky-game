/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import com.viettel.utils.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author haind25
 */
public class Mt_allManagerForm {
    
    String short_code;
    String msisdn;
    String contents;
    String trans_id;
    Date datetime;
    Date from_datetime;

    public Mt_allManagerForm() {
        datetime = DateTimeUtils.getPrevious7Date();
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
