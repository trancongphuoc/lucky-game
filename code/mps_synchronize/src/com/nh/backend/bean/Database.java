/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

/**
 *
 */
public class Database {
    public final static int MODE_READONLY = 1;
    private String id         ;
    private String code       ;
    private String name       ;
    private String config     ;
    private int status     ;
    private int readOnly     ;
    private String moduleCode;

    private String driver   ;
    private String url      ;
    private String username ;
    private String password ;
    
    public Database(String id, String code, String name, String config, int status, String module_code) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.config = config;
        this.status = status;
        this.moduleCode = module_code;
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

    public String getConfig() {
        return config;
    }

    public int getStatus() {
        return status;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    @Override
    public String toString() {
        return code; //To change body of generated methods, choose Tools | Templates.
    }

    public void setReadOnly(int readOnly) {
        this.readOnly = readOnly;
    }

    public int getReadOnly() {
        return readOnly;
    }
    
}
