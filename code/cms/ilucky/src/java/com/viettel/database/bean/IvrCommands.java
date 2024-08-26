/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.bean;

/**
 *
 * @author hanhnv62
 */
public class IvrCommands {
    private String id;
    private String sub_service_name;
    private String status;
   

    public IvrCommands(String id, String sub_service_name, String status) {
        this.id = id;
        this.sub_service_name = sub_service_name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
