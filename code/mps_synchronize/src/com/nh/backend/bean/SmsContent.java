/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "SMS_CONTENT")
public class SmsContent  extends PO{
    
    
    private String content;
    
    
    private String status;
   
    public SmsContent() {
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return id + "|" + content;
    }
    
}
