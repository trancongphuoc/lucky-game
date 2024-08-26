/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author ADMIN
 */
public class ServiceLanguageEditForm {

    private String id;
    private String serviceName;
    private String messageWong;
    private String oldServiceName;
    private String language_code;
    
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessageWong() {
        return messageWong;
    }

    public void setMessageWong(String messageWong) {
        this.messageWong = messageWong;
    }

    public String getOldServiceName() {
        return oldServiceName;
    }

    public void setOldServiceName(String oldServiceName) {
        this.oldServiceName = oldServiceName;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
