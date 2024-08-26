/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "WHITE_LIST")
public class WhiteList  extends PO{
    
    
    private String msisdn;
    
    
    private String status;
   
    public WhiteList() {
    }

    @Column(name = "MSISDN")
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "|" + msisdn;
    }
    
}
