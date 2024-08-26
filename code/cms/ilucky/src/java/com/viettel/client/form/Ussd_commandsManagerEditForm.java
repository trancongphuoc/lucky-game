/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author lamnt22
 */
public class Ussd_commandsManagerEditForm implements Serializable{

    String sub_service_name;
    Timestamp datetime;
    String status;
    String description;
    String type;
    String success_message;//tn thành công
    String failed_message;//tn không thành công
    String webservice_id;
    String mo_price;
    String required_registed;
    String not_enough_money_message;
    String charge_cmd;
    String check_required;
    String check_price;
    String id;
    String cmd_price;
    String message_channel;
    String confirm_message;
    String not_register_message;
    String already_register_message;

    public String getNot_register_message() {
        return not_register_message;
    }

    public void setNot_register_message(String not_register_message) {
        this.not_register_message = not_register_message;
    }

    public String getAlready_register_message() {
        return already_register_message;
    }

    public void setAlready_register_message(String already_register_message) {
        this.already_register_message = already_register_message;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNot_enough_money_message() {
        return not_enough_money_message;
    }

    public void setNot_enough_money_message(String not_enough_money_message) {
        this.not_enough_money_message = not_enough_money_message;
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

    public String getCheck_price() {
        return check_price;
    }

    public void setCheck_price(String check_price) {
        this.check_price = check_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//        public String getOldid() {
//        return oldid;
//    }
//
//    public void setOldid(String oldid) {
//        this.oldid = oldid;
//    }
    public String getCmd_price() {
        return cmd_price;
    }

    public void setCmd_price(String cmd_price) {
        this.cmd_price = cmd_price;
    }

    public String getMessage_channel() {
        return message_channel;
    }

    public void setMessage_channel(String message_channel) {
        this.message_channel = message_channel;
    }

    public String getConfirm_message() {
        return confirm_message;
    }

    public void setConfirm_message(String confirm_message) {
        this.confirm_message = confirm_message;
    }

    public Ussd_commandsManagerEditForm() {
    }

    
    
}
