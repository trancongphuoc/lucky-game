/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author MrCAP
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniIdResponseBean {

    private int status;
    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UniIdResponseBean() {
    }

    
}
