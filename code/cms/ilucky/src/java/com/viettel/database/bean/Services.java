/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.bean;

/**
 *
 * @author hanhnv62
 */
public class Services {
    private String provider_name;
    private String service_name;
    private String descriptions;
    private String status;

    public Services(String provider_name, String service_name, String descriptions, String status) {
        this.provider_name = provider_name;
        this.service_name = service_name;
        this.descriptions = descriptions;
        this.status = status;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    
    
}
