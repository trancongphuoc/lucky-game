/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

/**
 *
 * @author hanhnv62
 */
public class Users_servicesBO {
    
    private String username;
    private String service_name;
    private String status;

    public Users_servicesBO(String username, String service_name, String status) {
        this.username = username;
        this.service_name = service_name;
        this.status = status;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
