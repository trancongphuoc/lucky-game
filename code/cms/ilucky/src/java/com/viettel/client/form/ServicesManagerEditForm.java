/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.sql.Timestamp;

/**
 *
 * @author hanhnv62
 */
public class ServicesManagerEditForm {

    String service_code;
    String provider_name;
    String service_name;
    String oldservice_name;
    Timestamp time_create;
    String descriptions;
    String status;    
    String short_code;
    String default_sms;
    String default_sms_price;
    
    //lamnt22
    String smsgw_url;
    String smsgw_user;
    String smsgw_password;

    public String getSmsgw_url() {
        return smsgw_url;
    }

    public void setSmsgw_url(String smsgw_url) {
        this.smsgw_url = smsgw_url;
    }

    public String getSmsgw_user() {
        return smsgw_user;
    }

    public void setSmsgw_user(String smsgw_user) {
        this.smsgw_user = smsgw_user;
    }

    public String getSmsgw_password() {
        return smsgw_password;
    }

    public void setSmsgw_password(String smsgw_password) {
        this.smsgw_password = smsgw_password;
    }
    

    public ServicesManagerEditForm() {
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getOldservice_name() {
        return oldservice_name;
    }

    public void setOldservice_name(String oldservice_name) {
        this.oldservice_name = oldservice_name;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Timestamp getTime_create() {
        return time_create;
    }

    public void setTime_create(Timestamp time_create) {
        this.time_create = time_create;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }    

    public String getDefault_sms() {
        return default_sms;
    }

    public void setDefault_sms(String default_sms) {
        this.default_sms = default_sms;
    }

    public String getDefault_sms_price() {
        return default_sms_price;
    }

    public void setDefault_sms_price(String default_sms_price) {
        this.default_sms_price = default_sms_price;
    }
    
        
}
