/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author quyettc
 */
public class SMPPEditForm {

    private String shortCodeId;
    private String shortCode;
    private String user;
    private String password;
    private String status;
    private String id;
    private String fakeId;
    private String fakeShortCode;

    public String getFakeId() {
        return fakeId;
    }

    public void setFakeId(String fakeId) {
        this.fakeId = fakeId;
    }

    public String getFakeShortCode() {
        return fakeShortCode;
    }

    public void setFakeShortCode(String fakeShortCode) {
        this.fakeShortCode = fakeShortCode;
    }


    public SMPPEditForm() {
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortCodeId() {
        return shortCodeId;
    }

    public void setShortCodeId(String shortCodeId) {
        this.shortCodeId = shortCodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
