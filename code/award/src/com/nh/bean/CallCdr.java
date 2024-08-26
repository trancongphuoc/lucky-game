/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

import java.util.Date;

/**
 *
 * @author hadc
 */
public class CallCdr {
    private String id;
    private long busycallTime;
    
    private String called;
    private String calling;
    private Date startDate;
    private String callDay;
    private int duration;
    private int promCost;
    private int cost;
    private int directionId;
    private String cdrFileName;
    private String cdrLine;

    public CallCdr(String calling, String called, Date startDate,int cost,int duration) {
        this.called = called;
        this.calling = calling;
        this.startDate = startDate;
        this.cost = cost;
        this.duration = duration;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getCalling() {
        return calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCdrFileName() {
        return cdrFileName;
    }

    public void setCdrFileName(String cdrFileName) {
        this.cdrFileName = cdrFileName;
    }

    public String getCallDay() {
        return callDay;
    }

    public void setCallDay(String callDay) {
        this.callDay = callDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBusycallTime() {
        return busycallTime;
    }

    public void setBusycallTime(long busycallTime) {
        this.busycallTime = busycallTime;
    }

    public String getCdrLine() {
        return cdrLine;
    }

    public void setCdrLine(String cdrLine) {
        this.cdrLine = cdrLine;
    }

    public int getPromCost() {
        return promCost;
    }

    public void setPromCost(int promCost) {
        this.promCost = promCost;
    }

    public int getDirectionId() {
        return directionId;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }
    
    
    @Override
    public String toString() {
        return " calling: " + calling 
                + " ; called: " + called 
                + " ; cost: " + cost 
                + " ; promCost: " + promCost 
                + " ; duration: " + duration 
                + "; startDate: " + startDate
                + "; directionId: " + directionId
                ;
        
    }
    
    
}
