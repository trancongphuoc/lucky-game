/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author hanhnv62
 */
public class Service_category_priceManagerEditForm {
    String sub_service_name;    
    String category;
    String default_price;
    Timestamp create_time;   
    String is_promotion;
    Date begin_promotion_time;
    Date end_promotion_time;
    String promotion_price;
    String status;
    String cmd;   
    String oldcategory;
    String cp_status;
    String kd_status;
    String admin_status;
    
    //lamnt22
    String promotion_msg;
    String add_monfee_cricle_day;
    String check_register;
    String test_msisdn_list;
    String check_sv_promo_list;
    
    
    public String getCp_status() {
        return cp_status;
    }

    public void setCp_status(String cp_status) {
        this.cp_status = cp_status;
    }

    public String getKd_status() {
        return kd_status;
    }

    public void setKd_status(String kd_status) {
        this.kd_status = kd_status;
    }

    public String getAdmin_status() {
        return admin_status;
    }

    public void setAdmin_status(String admin_status) {
        this.admin_status = admin_status;
    }

    public Service_category_priceManagerEditForm() {
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDefault_price() {
        return default_price;
    }

    public void setDefault_price(String default_price) {
        this.default_price = default_price;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(String is_promotion) {
        this.is_promotion = is_promotion;
    }

    public Date getBegin_promotion_time() {
        return begin_promotion_time;
    }

    public void setBegin_promotion_time(Date begin_promotion_time) {
        this.begin_promotion_time = begin_promotion_time;
    }

    public Date getEnd_promotion_time() {
        return end_promotion_time;
    }

    public void setEnd_promotion_time(Date end_promotion_time) {
        this.end_promotion_time = end_promotion_time;
    }
    
    public String getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(String promotion_price) {
        this.promotion_price = promotion_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getOldcategory() {
        return oldcategory;
    }

    public void setOldcategory(String oldcategory) {
        this.oldcategory = oldcategory;
    }

    /**
     * @return the promotion_msg
     */
    public String getPromotion_msg() {
        return promotion_msg;
    }

    /**
     * @param promotion_msg the promotion_msg to set
     */
    public void setPromotion_msg(String promotion_msg) {
        this.promotion_msg = promotion_msg;
    }

    /**
     * @return the add_monfee_cricle_day
     */
    public String getAdd_monfee_cricle_day() {
        return add_monfee_cricle_day;
    }

    /**
     * @param add_monfee_cricle_day the add_monfee_cricle_day to set
     */
    public void setAdd_monfee_cricle_day(String add_monfee_cricle_day) {
        this.add_monfee_cricle_day = add_monfee_cricle_day;
    }

    /**
     * @return the check_register
     */
    public String getCheck_register() {
        return check_register;
    }

    /**
     * @param check_register the check_register to set
     */
    public void setCheck_register(String check_register) {
        this.check_register = check_register;
    }

    /**
     * @return the test_msisdn_list
     */
    public String getTest_msisdn_list() {
        return test_msisdn_list;
    }

    /**
     * @param test_msisdn_list the test_msisdn_list to set
     */
    public void setTest_msisdn_list(String test_msisdn_list) {
        this.test_msisdn_list = test_msisdn_list;
    }

    /**
     * @return the check_sv_promo_list
     */
    public String getCheck_sv_promo_list() {
        return check_sv_promo_list;
    }

    /**
     * @param check_sv_promo_list the check_sv_promo_list to set
     */
    public void setCheck_sv_promo_list(String check_sv_promo_list) {
        this.check_sv_promo_list = check_sv_promo_list;
    }


}
