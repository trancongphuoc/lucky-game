/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author hanhnv62
 */
public class sub_services_featuresManagerForm {
    String sub_service_name;
    String service_name;
    String captcha;
    String immediate_charge; 
    String send_notify;
    String send_notify_before_cancel;
    private String day_send_notify_before_cancel;
    String send_notify_auto_cancel;

    public sub_services_featuresManagerForm() {
        
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getImmediate_charge() {
        return immediate_charge;
    }

    public void setImmediate_charge(String immediate_charge) {
        this.immediate_charge = immediate_charge;
    }

    public String getSend_notify() {
        return send_notify;
    }

    public void setSend_notify(String send_notify) {
        this.send_notify = send_notify;
    }

    public String getSend_notify_before_cancel() {
        return send_notify_before_cancel;
    }

    public void setSend_notify_before_cancel(String send_notify_before_cancel) {
        this.send_notify_before_cancel = send_notify_before_cancel;
    }
    
    
    public String getSend_notify_auto_cancel() {
        return send_notify_auto_cancel;
    }

    public void setSend_notify_auto_cancel(String send_notify_auto_cancel) {
        this.send_notify_auto_cancel = send_notify_auto_cancel;
    }

    public String getDay_send_notify_before_cancel() {
        return day_send_notify_before_cancel;
    }

    public void setDay_send_notify_before_cancel(String day_send_notify_before_cancel) {
        this.day_send_notify_before_cancel = day_send_notify_before_cancel;
    }
    
    
}
