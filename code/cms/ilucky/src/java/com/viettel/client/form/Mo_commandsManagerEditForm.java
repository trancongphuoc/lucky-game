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
public class Mo_commandsManagerEditForm {

    String cmd;
    String oldcmd;
    String sub_service_name;
    Timestamp datetime;
    String status;
    String details;
    String type;
    String success_message;//tn thành công
    String failed_message;//tn không thành công
    String register_conflict;//tn đăng ký trùng dịch vụ
    String already_register;//tn đã đăng ký
    String not_register;//tn chưa đăng ký
    String not_enough_money;//tn không đủ tiền
    String webservice_id;
    String mo_price;
    String required_registed;
    String charge_cmd;
    String check_required;

    public Mo_commandsManagerEditForm() {
    }

    public String getCmd() {
        return cmd;
    }

    public String getSuccess_message() {
        return success_message;
    }

    public void setSuccess_message(String success_message) {
        this.success_message = success_message;
    }

    public String getFailed_message() {
        return failed_message;
    }

    public void setFailed_message(String failed_message) {
        this.failed_message = failed_message;
    }

    public String getRegister_conflict() {
        return register_conflict;
    }

    public void setRegister_conflict(String register_conflict) {
        this.register_conflict = register_conflict;
    }

    public String getAlready_register() {
        return already_register;
    }

    public void setAlready_register(String already_register) {
        this.already_register = already_register;
    }

    public String getNot_register() {
        return not_register;
    }

    public void setNot_register(String not_register) {
        this.not_register = not_register;
    }

    public String getNot_enough_money() {
        return not_enough_money;
    }

    public void setNot_enough_money(String not_enough_money) {
        this.not_enough_money = not_enough_money;
    }
    

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getOldcmd() {
        return oldcmd;
    }

    public void setOldcmd(String oldcmd) {
        this.oldcmd = oldcmd;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebservice_id() {
        return webservice_id;
    }

    public void setWebservice_id(String webservice_id) {
        this.webservice_id = webservice_id;
    }

    public String getMo_price() {
        return mo_price;
    }

    public void setMo_price(String mo_price) {
        this.mo_price = mo_price;
    }

    public String getRequired_registed() {
        return required_registed;
    }

    public void setRequired_registed(String required_registed) {
        this.required_registed = required_registed;
    }

    public String getCharge_cmd() {
        return charge_cmd;
    }

    public void setCharge_cmd(String charge_cmd) {
        this.charge_cmd = charge_cmd;
    }

    public String getCheck_required() {
        return check_required;
    }

    public void setCheck_required(String check_required) {
        this.check_required = check_required;
    }
    
}
