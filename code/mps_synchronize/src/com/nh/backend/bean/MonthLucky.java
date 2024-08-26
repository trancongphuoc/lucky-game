/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

import java.util.logging.Logger;


public class MonthLucky {
    private int status;
    private String luckyCode;
    private String periodCode;

    public MonthLucky(int status, String luckyCode, String periodCode) {
        this.status = status;
        this.luckyCode = luckyCode;
        this.periodCode = periodCode;
    }
    
    public int getStatus() {
        return status;
    }

    public String getLuckyCode() {
        return luckyCode;
    }


    public String getPeriodCode() {
        return periodCode;
    }

    @Override
    public String toString() {
        return status + "|" + periodCode + "|" + luckyCode;
    }

    
    
}
