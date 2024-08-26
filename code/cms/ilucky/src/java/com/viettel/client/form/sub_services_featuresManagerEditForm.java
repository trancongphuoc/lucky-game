/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author hanhnv62
 */
public class sub_services_featuresManagerEditForm {

    String sub_service_name;
    String service_name;
    String captcha;
    String immediate_charge;
    String stop_on_error;//check dung qua trinh charge khi gap loi
    String monfee_hour;//gio charge monfee trong ngay
    String require_mps_user;//check yeu cau co account mps ko
    String check_conflict;//kiem tra dk nhieu goi cuoc
    String recheck_before_monfee;//check truoc khi monfee
    //hungtv45
    String send_notify;//gui thong bao hay khong?
    String send_notify_before_cancel;
    String day_send_notify_before_cancel;
    String send_notify_auto_cancel;
    String soft_name;//tên phần mềm
    
    String notify_content;//nội dung thông báo
    String notify_before_cancel;
    String notify_auto_cancel;
    
    private String auto_cancel_conflict;
    private String renew_check_mode;

    private String pending_content    ;
    private String pending_notify         ;
    
    private String recharge_before_notify ;
    private String recharge_before_day    ;
    private String recharge_before_content;
    
    private String same_price; 
     //Added by ThoTV-TelSoft 18/03/2019
    private String micro_charging_price;
    private String micro_charging_add_date;
    
    public String getSend_notify() {
        return send_notify;
    }

    public String getSend_notify_before_cancel() {
        return send_notify_before_cancel;
    }

    public void setSend_notify_before_cancel(String sent_notify_before_cancel) {
        this.send_notify_before_cancel = sent_notify_before_cancel;
    }

    public String getDay_send_notify_before_cancel() {
        return day_send_notify_before_cancel;
    }

    public void setDay_send_notify_before_cancel(String day_send_notify_before_cancel) {
        this.day_send_notify_before_cancel = day_send_notify_before_cancel;
    }
    
    public String getSend_notify_auto_cancel() {
        return send_notify_auto_cancel;
    }

    public void setSend_notify_auto_cancel(String sent_notify_auto_cancel) {
        this.send_notify_auto_cancel = sent_notify_auto_cancel;
    }
    
    
    public void setSend_notify(String send_notify) {
        this.send_notify = send_notify;
    }

    public String getSoft_name() {
        return soft_name;
    }

    public void setSoft_name(String soft_name) {
        this.soft_name = soft_name;
    }

    public String getNotify_content() {
        return notify_content;
    }

    public void setNotify_content(String notify_content) {
        this.notify_content = notify_content;
    }

    public String getNotify_before_cancel() {
        return notify_before_cancel;
    }

    public void setNotify_before_cancel(String notify_before_cancel) {
        this.notify_before_cancel = notify_before_cancel;
    }

    public String getNotify_auto_cancel() {
        return notify_auto_cancel;
    }

    public void setNotify_auto_cancel(String notify_auto_cancel) {
        this.notify_auto_cancel = notify_auto_cancel;
    }

    
    public String getStop_on_error() {
        return stop_on_error;
    }

    public void setStop_on_error(String stop_on_error) {
        this.stop_on_error = stop_on_error;
    }

    public String getMonfee_hour() {
        return monfee_hour;
    }

    public void setMonfee_hour(String monfee_hour) {
        this.monfee_hour = monfee_hour;
    }

    public String getRequire_mps_user() {
        return require_mps_user;
    }

    public void setRequire_mps_user(String require_mps_user) {
        this.require_mps_user = require_mps_user;
    }

    public String getCheck_conflict() {
        return check_conflict;
    }

    public void setCheck_conflict(String check_conflict) {
        this.check_conflict = check_conflict;
    }

    public String getRecheck_before_monfee() {
        return recheck_before_monfee;
    }

    public void setRecheck_before_monfee(String recheck_before_monfee) {
        this.recheck_before_monfee = recheck_before_monfee;
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

    public sub_services_featuresManagerEditForm() {
    }

    public void setAuto_cancel_conflict(String auto_cancel_conflict) {
        this.auto_cancel_conflict = auto_cancel_conflict;
    }

    public String getAuto_cancel_conflict() {
        return auto_cancel_conflict;
    }

    public void setRenew_check_mode(String renew_check_mode) {
        this.renew_check_mode = renew_check_mode;
    }

    public String getRenew_check_mode() {
        return renew_check_mode;
    }

    public String getPending_content() {
        return pending_content;
    }

    public void setPending_content(String pending_content) {
        this.pending_content = pending_content;
    }

    public String getPending_notify() {
        return pending_notify;
    }

    public void setPending_notify(String pending_notify) {
        this.pending_notify = pending_notify;
    }

    public String getRecharge_before_notify() {
        return recharge_before_notify;
    }

    public void setRecharge_before_notify(String recharge_before_notify) {
        this.recharge_before_notify = recharge_before_notify;
    }

    public String getRecharge_before_day() {
        return recharge_before_day;
    }

    public void setRecharge_before_day(String recharge_before_day) {
        this.recharge_before_day = recharge_before_day;
    }

    public String getRecharge_before_content() {
        return recharge_before_content;
    }

    public void setRecharge_before_content(String recharge_before_content) {
        this.recharge_before_content = recharge_before_content;
    }

    public String getSame_price() {
        return same_price;
    }

    public void setSame_price(String same_price) {
        this.same_price = same_price;
    }
    
    public String getMicro_charging_price() {
        return micro_charging_price;
    }

    public void setMicro_charging_price(String micro_charging_price) {
        this.micro_charging_price = micro_charging_price;
    }

    public String getMicro_charging_add_date() {
        return micro_charging_add_date;
    }

    public void setMicro_charging_add_date(String micro_charging_add_date) {
        this.micro_charging_add_date = micro_charging_add_date;
    }
}
