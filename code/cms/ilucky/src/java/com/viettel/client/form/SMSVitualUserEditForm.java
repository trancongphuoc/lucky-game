/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author quyettc
 */
public class SMSVitualUserEditForm {

    private String shortCode;
    private String vitualShortCode;
    private String alias;
    private String user;
    private String status;
    private String fakeShortCode;
    private String fakeUser;

    public String getFakeShortCode() {
        return fakeShortCode;
    }

    public void setFakeShortCode(String fakeShortCode) {
        this.fakeShortCode = fakeShortCode;
    }

    public String getFakeUser() {
        return fakeUser;
    }

    public void setFakeUser(String fakeUser) {
        this.fakeUser = fakeUser;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getVitualShortCode() {
        return vitualShortCode;
    }

    public void setVitualShortCode(String vitualShortCode) {
        this.vitualShortCode = vitualShortCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
