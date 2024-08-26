/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.bean;

/**
 *
 * @author hanhnv62
 */
public class Sub_Services {
    String sub_service_name;
    String descriptions;
    String service_name;
    String status;

    public Sub_Services(String sub_service_name, String descriptions, String service_name, String status) {
        this.sub_service_name = sub_service_name;
        this.descriptions = descriptions;
        this.service_name = service_name;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
