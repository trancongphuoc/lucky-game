/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author TRIEUNGUYEN
 */
public class SqlMenuForm {
    private String status;
    private String name;
    private String code;

    public SqlMenuForm() {
    }

    
    public SqlMenuForm(String status, String name, String code) {
        this.status = status;
        this.name = name;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
}
