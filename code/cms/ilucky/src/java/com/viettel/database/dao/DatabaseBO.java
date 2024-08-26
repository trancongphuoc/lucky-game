/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;


/**
 * @author quyettc
 */
public class DatabaseBO {

    private String id;
    private String code;
    private String name;
    private String config;
    private String status;
    private String module_code;
    private String driver;
    private String url;
    private String userName;
    private String password;

    public DatabaseBO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public DatabaseBO(String id, String code, String name, String config, String status, String module_code, String driver, String url, String userName, String password) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.config = config;
        this.status = status;
        this.module_code = module_code;
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
