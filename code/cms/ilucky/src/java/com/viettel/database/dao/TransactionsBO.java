/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.sql.Timestamp;

/**
 *
 * @author 
 */
public class TransactionsBO {
    private String id;
    private String vcgw_request_id;
    private String msisdn;    
    private String trans_id;
    private Timestamp request_time;
    private Timestamp response_time;
    private String response_code;
    private String service_name;
    private String sub_service_name;    
    private String cmd;
    private String category_name;
    private String item_name;
    private String sub_cp_name;
    private String contents;
    private String provider_name;
    private String price;
    private String charge_type;
    private String channel;

    public TransactionsBO(String id, String vcgw_request_id, String msisdn, String trans_id, Timestamp request_time, Timestamp response_time, String response_code, String service_name, String sub_service_name, String cmd, String category_name, String item_name, String sub_cp_name, String contents, String provider_name, String price, String charge_type, String channel) {
        this.id = id;
        this.vcgw_request_id = vcgw_request_id;
        this.msisdn = msisdn;
        this.trans_id = trans_id;
        this.request_time = request_time;
        this.response_time = response_time;
        this.response_code = response_code;
        this.service_name = service_name;
        this.sub_service_name = sub_service_name;
        this.cmd = cmd;
        this.category_name = category_name;
        this.item_name = item_name;
        this.sub_cp_name = sub_cp_name;
        this.contents = contents;
        this.provider_name = provider_name;
        this.price = price;
        this.charge_type = charge_type;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVcgw_request_id() {
        return vcgw_request_id;
    }

    public void setVcgw_request_id(String vcgw_request_id) {
        this.vcgw_request_id = vcgw_request_id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public Timestamp getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Timestamp request_time) {
        this.request_time = request_time;
    }

    public Timestamp getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Timestamp response_time) {
        this.response_time = response_time;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSub_cp_name() {
        return sub_cp_name;
    }

    public void setSub_cp_name(String sub_cp_name) {
        this.sub_cp_name = sub_cp_name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
   
    

    
}
