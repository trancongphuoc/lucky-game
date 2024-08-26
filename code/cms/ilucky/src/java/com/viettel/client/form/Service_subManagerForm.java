/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import com.viettel.utils.DateTimeUtils;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author hanhnv62
 */
public class Service_subManagerForm {
    
    String sub_service_code;
    String provider_name;    
    String service_name;  
    String sub_service_name;   
    String descriptions;
    Timestamp cancel_time;
    String msisdn;
    String status;
    Timestamp register_time;
    Date to_time;
    Date from_time;

    public Service_subManagerForm() {
        from_time = DateTimeUtils.getPreviousMonthDate();
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getSub_service_code() {
        return sub_service_code;
    }

    public void setSub_service_code(String sub_service_code) {
        this.sub_service_code = sub_service_code;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public Date getTo_time() {
        return to_time;
    }

    public void setTo_time(Date to_time) {
        this.to_time = to_time;
    }

    public Date getFrom_time() {
        return from_time;
    }

    public void setFrom_time(Date from_time) {
        this.from_time = from_time;
    }

    
    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public Timestamp getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(Timestamp cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Timestamp register_time) {
        this.register_time = register_time;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
