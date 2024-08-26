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
 * @author Dell
 */
@Table(name = "LUCKY_AWARD")
public class Award extends PO  {
    private String code            ;
    private String price           ;
    private String name            ;
    private String unit            ;
    private String awardSystem    ;
    private String mpsCmd         ;
    private String mpsCate         ;
    private String status          ;
    
    public Award() {
       
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "PRICE")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "AWARD_SYSTEM")
    public String getAwardSystem() {
        return awardSystem;
    }

    public void setAwardSystem(String awardSystem) {
        this.awardSystem = awardSystem;
    }

    @Column(name = "MPS_CMD")
    public String getMpsCmd() {
        return mpsCmd;
    }

    public void setMpsCmd(String mpsCmd) {
        this.mpsCmd = mpsCmd;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "MPS_CATE")
    public String getMpsCate() {
        return mpsCate;
    }

    public void setMpsCate(String mpsCate) {
        this.mpsCate = mpsCate;
    }

    
    
    @Override
    public String toString() {
        return "Award{" + "code=" + code + ", mpsCmd=" + mpsCmd + '}';
    }
    

}
