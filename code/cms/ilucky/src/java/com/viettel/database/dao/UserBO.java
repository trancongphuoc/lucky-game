/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;


/**
 *
 * @author quyettc
 */
public class UserBO {

    private String username;
    private String password;
    private String status;
    private String descriptions;
    private String userType;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserBO(String username, String password, String status, String descriptions, String userType) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.descriptions = descriptions;
        this.userType = userType;
    }
}
