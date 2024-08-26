/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import com.viettel.utils.DateTimeUtils;
import java.util.Date;


/**
 *
 * @author 
 */
public class TransactionsManagerForm {
    String trans_id;
    String item_name;
    String service_name;
    String catagory_name;
    String msisdn;
    String provider_name;
    Date request_time;
    Date response_time;
    String cmd;
    String sub_service_name;

    public TransactionsManagerForm() {
        request_time = DateTimeUtils.getPrevious7Date();
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTran_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getCatagory_name() {
        return catagory_name;
    }

    public void setCatagory_name(String catagory_name) {
        this.catagory_name = catagory_name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public Date getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Date request_time) {
        this.request_time = request_time;
    }

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }
    
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }
    
    
}
