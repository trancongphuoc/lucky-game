/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class Mo_MultiLanguageForm implements Serializable {

    String id;
    String sub_service_name;
    String cmd;
    String oldcmd;
    String success_message;//tn thành công
    String failed_message;//tn không thành công
    String register_conflict;//tn đăng ký trùng dịch vụ
    String already_register;//tn đã đăng ký
    String not_register;//tn chưa đăng ký
    String not_enough_money;//tn không đủ tiền
    String status;
    String language_code;
    Timestamp datetime;

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public Mo_MultiLanguageForm() {
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getCmd() {
        return cmd;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
