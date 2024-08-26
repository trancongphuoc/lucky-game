/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.util.Date;


/**
 *
 * @author hanhnv62
 */
public class Sub_ServicesManagerEditForm {

    String sub_service_code;
    String sub_service_name;
    String oldsub_service_name;
    String descriptions;
    String service_name;
    String is_subscriptions;
    String status;
    String is_promo;
    String time_create;
    String fix_time_charge_monfee;
    String day_charge_monfee;
    String group_charge_type;
    String subs_type;
    String subs_price;
    String promo_price;
    Date promo_datetime_begin;
    Date promo_datetime_end;
    String day_debit_allowed;
    String monfee_ws_id;
    String register_ws_id;
    String cancel_ws_id;
    String public_cp;
    String public_vt_cp;
    String private_cp;
    String private_vt_cp;  
    String url_return;    
    String timeout_trans;//time out giao dich (check giao dich)
    String notify_ws_id;

    public String getNotify_ws_id() {
        return notify_ws_id;
    }

    public void setNotify_ws_id(String notify_ws_id) {
        this.notify_ws_id = notify_ws_id;
    }
    
//    String stop_on_error;//check dung qua trinh charge khi gap loi
//    String monfee_hour;//gio charge monfee trong ngay
//    String require_mps_user;//check yeu cau co account mps ko
//    String check_conflict;//kiem tra dk nhieu goi cuoc
//    String recheck_before_monfee;//check truoc khi monfee

    public Sub_ServicesManagerEditForm() {
    }

    public String getTimeout_trans() {
        return timeout_trans;
    }

    public void setTimeout_trans(String timeout_trans) {
        this.timeout_trans = timeout_trans;
    }

    public String getPublic_cp() {
        return public_cp;
    }

    public void setPublic_cp(String public_cp) {
        this.public_cp = public_cp;
    }

    public String getPublic_vt_cp() {
        return public_vt_cp;
    }

    public void setPublic_vt_cp(String public_vt_cp) {
        this.public_vt_cp = public_vt_cp;
    }

    public String getPrivate_cp() {
        return private_cp;
    }

    public void setPrivate_cp(String private_cp) {
        this.private_cp = private_cp;
    }

    public String getPrivate_vt_cp() {
        return private_vt_cp;
    }

    public void setPrivate_vt_cp(String private_vt_cp) {
        this.private_vt_cp = private_vt_cp;
    }

    public String getUrl_return() {
        return url_return;
    }

    public void setUrl_return(String url_return) {
        this.url_return = url_return;
    }

    public String getSubs_price() {
        return subs_price;
    }

    public void setSubs_price(String subs_price) {
        this.subs_price = subs_price;
    }

    public String getSub_service_code() {
        return sub_service_code;
    }

    public void setSub_service_code(String sub_service_code) {
        this.sub_service_code = sub_service_code;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getIs_subscriptions() {
        return is_subscriptions;
    }

    public void setIs_subscriptions(String is_subscriptions) {
        this.is_subscriptions = is_subscriptions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getIs_promo() {
        return is_promo;
    }

    public void setIs_promo(String is_promo) {
        this.is_promo = is_promo;
    }
    
    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getGroup_charge_type() {
        return group_charge_type;
    }

    public void setGroup_charge_type(String group_charge_type) {
        this.group_charge_type = group_charge_type;
    }

    public String getSubs_type() {
        return subs_type;
    }

    public void setSubs_type(String subs_type) {
        this.subs_type = subs_type;
    }

    public String getPromo_price() {
        return promo_price;
    }

    public void setPromo_price(String promo_price) {
        this.promo_price = promo_price;
    }

    public String getFix_time_charge_monfee() {
        return fix_time_charge_monfee;
    }

    public void setFix_time_charge_monfee(String fix_time_charge_monfee) {
        this.fix_time_charge_monfee = fix_time_charge_monfee;
    }

    public String getDay_charge_monfee() {
        return day_charge_monfee;
    }

    public void setDay_charge_monfee(String day_charge_monfee) {
        this.day_charge_monfee = day_charge_monfee;
    }

    public Date getPromo_datetime_begin() {
        return promo_datetime_begin;
    }

    public void setPromo_datetime_begin(Date promo_datetime_begin) {
        this.promo_datetime_begin = promo_datetime_begin;
    }

    public Date getPromo_datetime_end() {
        return promo_datetime_end;
    }

    public void setPromo_datetime_end(Date promo_datetime_end) {
        this.promo_datetime_end = promo_datetime_end;
    }

    
    public String getDay_debit_allowed() {
        return day_debit_allowed;
    }

    public void setDay_debit_allowed(String day_debit_allowed) {
        this.day_debit_allowed = day_debit_allowed;
    }

    public String getOldsub_service_name() {
        return oldsub_service_name;
    }

    public void setOldsub_service_name(String oldsub_service_name) {
        this.oldsub_service_name = oldsub_service_name;
    }

    public String getMonfee_ws_id() {
        return monfee_ws_id;
    }

    public void setMonfee_ws_id(String monfee_ws_id) {
        this.monfee_ws_id = monfee_ws_id;
    }

    public String getRegister_ws_id() {
        return register_ws_id;
    }

    public void setRegister_ws_id(String register_ws_id) {
        this.register_ws_id = register_ws_id;
    }

    public String getCancel_ws_id() {
        return cancel_ws_id;
    }

    public void setCancel_ws_id(String cancel_ws_id) {
        this.cancel_ws_id = cancel_ws_id;
    }
    
    
}
