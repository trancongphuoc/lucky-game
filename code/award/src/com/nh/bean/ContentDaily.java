/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

/**
 *
 * @author hadc
 */
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "CONTENT_DAILY")
public class ContentDaily  extends PO{
    
    @Column(name = "SUB_SERVICE_NAME")
    public String  SUB_SERVICE_NAME ;
    
    @Column(name = "FROM_DATE")
    public Date FROM_DATE;
    
    @Column(name = "CONTENT_KIRUNDI")
    public String CONTENT_KIRUNDI;
    
    @Column(name = "APPROVED")
    public String APPROVED;
    
    @Column(name = "APPROVER")
    public boolean APPROVER;
    
    @Column(name = "UPDATED")
    public Timestamp UPDATED;
    
    @Column(name = "CONTENT_FRANCE")
    public String CONTENT_FRANCE;
    
    public ContentDaily() {
    }

    public String getSUB_SERVICE_NAME() {
        return SUB_SERVICE_NAME;
    }

    public void setSUB_SERVICE_NAME(String SUB_SERVICE_NAME) {
        this.SUB_SERVICE_NAME = SUB_SERVICE_NAME;
    }

    public Date getFROM_DATE() {
        return FROM_DATE;
    }

    public void setFROM_DATE(Date FROM_DATE) {
        this.FROM_DATE = FROM_DATE;
    }

    public String getCONTENT_KIRUNDI() {
        return CONTENT_KIRUNDI;
    }

    public void setCONTENT_KIRUNDI(String CONTENT_KIRUNDI) {
        this.CONTENT_KIRUNDI = CONTENT_KIRUNDI;
    }

    public String getAPPROVED() {
        return APPROVED;
    }

    public void setAPPROVED(String APPROVED) {
        this.APPROVED = APPROVED;
    }

    public boolean isAPPROVER() {
        return APPROVER;
    }

    public void setAPPROVER(boolean APPROVER) {
        this.APPROVER = APPROVER;
    }

    public Timestamp getUPDATED() {
        return UPDATED;
    }

    public void setUPDATED(Timestamp UPDATED) {
        this.UPDATED = UPDATED;
    }

    public String getCONTENT_FRANCE() {
        return CONTENT_FRANCE;
    }

    public void setCONTENT_FRANCE(String CONTENT_FRANCE) {
        this.CONTENT_FRANCE = CONTENT_FRANCE;
    }

    @Override
    public String toString() {
        return "ContentDaily{" + "SUB_SERVICE_NAME=" + SUB_SERVICE_NAME + ", FROM_DATE=" + FROM_DATE + ", CONTENT_KIRUNDI=" + CONTENT_KIRUNDI + ", APPROVED=" + APPROVED + ", APPROVER=" + APPROVER + ", UPDATED=" + UPDATED + ", CONTENT_FRANCE=" + CONTENT_FRANCE + '}';
    }
    
}
