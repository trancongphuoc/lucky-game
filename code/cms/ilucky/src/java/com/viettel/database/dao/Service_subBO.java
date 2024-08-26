/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.sql.Timestamp;

/**
 *
 * @author hanhnv62
 */
public class Service_subBO {
    
    
    private String sub_service_code;
    private String service_name;
    private String provider_name;
    private String sub_service_name;    
    private Timestamp cancel_time;
    private String msisdn;
    private String status;
    private Timestamp register_time;
    private String descriptions;
    private Timestamp last_monfee_charge_time;
    private Timestamp next_monfee_charge_time;
    private String charge_status;
    private String is_promotion;    

    public Service_subBO(String sub_service_code, String service_name, String provider_name, String sub_service_name, Timestamp cancel_time, String msisdn, String status, Timestamp register_time, String descriptions, Timestamp last_monfee_charge_time, Timestamp next_monfee_charge_time, String charge_status, String is_promotion) {
        this.sub_service_code = sub_service_code;
        this.service_name = service_name;
        this.provider_name = provider_name;
        this.sub_service_name = sub_service_name;
        this.cancel_time = cancel_time;
        this.msisdn = msisdn;
        this.status = status;
        this.register_time = register_time;
        this.descriptions = descriptions;
        this.last_monfee_charge_time = last_monfee_charge_time;
        this.next_monfee_charge_time = next_monfee_charge_time;
        this.charge_status = charge_status;
        this.is_promotion = is_promotion;
    }

    public String getSub_service_code() {
        return sub_service_code;
    }

    public void setSub_service_code(String sub_service_code) {
        this.sub_service_code = sub_service_code;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
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

    public Timestamp getLast_monfee_charge_time() {
        return last_monfee_charge_time;
    }

    public void setLast_monfee_charge_time(Timestamp last_monfee_charge_time) {
        this.last_monfee_charge_time = last_monfee_charge_time;
    }

    public Timestamp getNext_monfee_charge_time() {
        return next_monfee_charge_time;
    }

    public void setNext_monfee_charge_time(Timestamp next_monfee_charge_time) {
        this.next_monfee_charge_time = next_monfee_charge_time;
    }

    public String getCharge_status() {
        return charge_status;
    }

    public void setCharge_status(String charge_status) {
        this.charge_status = charge_status;
    }

    public String getIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(String is_promotion) {
        this.is_promotion = is_promotion;
    }

    
}
