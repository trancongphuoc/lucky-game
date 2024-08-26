/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

/**
 *
 */
public class ModuleInfo {
    
    private String id            ;
    private String code          ;
    private String name          ;
    private String email         ;
    private String msisdn        ;
    private String description   ;
    private String status        ;

    public ModuleInfo(String id, String code, String name, String email, String msisdn, String description, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
        this.msisdn = msisdn;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        return "(code: " + code+ "; " + 
               "email: " + email+ "";
    }
    
}
