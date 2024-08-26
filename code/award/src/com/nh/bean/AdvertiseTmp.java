/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author 
 */
@Table(name = "Advertise_tmp")
public class AdvertiseTmp extends PO{
    public static final int STATUS_DRAFT = 1;
    public static final int STATUS_PROCESSED = 2;
    
    private String msisdn;
    private int status;

    public AdvertiseTmp(){
        super();
    }
    
    @Id		
    @Column(name = "ROWID")
    @Override
    public String getId() {
        return this.id;
    }
    
    @Column(name = "MSISDN")
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return msisdn;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
