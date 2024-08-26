/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * @author hadc
 */
@Table(name = "Advertise_His")
public class AdvertiseHis extends PO{
    
    public static final String CHANNEL_USSD = "USSD";
    public static final String CHANNEL_IVR= "IVR";
    public static final String CHANNEL_SMS = "SMS";
    
    @Column(name = "MSISDN")
    private String msisdn;
    
    @Column(name = "CONTENT")
    private String content;
    
    @Column(name = "CHANNEL")
    private String channel;

    public AdvertiseHis(){
        super();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    
    
    @Override
    public String toString() {
        return msisdn + "|" + content;
    }
    
}
