/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class Stats_OnDay implements Serializable {

    private String id;
    private String reportDay;
    private String register;
    private String cancel;
    private String renew;
    private String revenue;
    private String totalSub;
    private String activeSub;
    private String pendingSub;
    private String cancelSub;
    private String cost;
    private String profit;
    private String moneyRegister;
    private String moneyBuy;
    private String moneyRenew;
    private String numPlay;
    private String numPlayer;
    
    public Stats_OnDay() {
    }

    public Stats_OnDay(String id, String reportDay, String register, String cancel, 
            String renew, String revenue, String activeSub, String pendingSub, String cancelSub,
            String cost, String profit, String moneyRegister, String moneyBuy, String moneyRenew, String totalSub) {
        this.id = id;
        this.reportDay = reportDay;
        this.register = register;
        this.cancel = cancel;
        this.renew = renew;
        this.revenue = revenue;
        this.activeSub = activeSub;
        this.pendingSub = pendingSub;
        this.cancelSub = cancelSub;
        this.cost = cost;
        this.profit = profit;
        this.moneyRegister = moneyRegister;
        this.moneyBuy = moneyBuy;
        this.moneyRenew = moneyRenew;
        this.totalSub = totalSub;
    }

    public String getId() {
        return id;
    }

    public String getReportDay() {
        return reportDay;
    }

    public String getRegister() {
        return register;
    }

    public String getCancel() {
        return cancel;
    }

    public String getRenew() {
        return renew;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getTotalSub() {
        return totalSub;
    }

    public String getActiveSub() {
        return activeSub;
    }

    public String getPendingSub() {
        return pendingSub;
    }

    public String getCancelSub() {
        return cancelSub;
    }

    public String getCost() {
        return cost;
    }

    public String getProfit() {
        return profit;
    }

    public String getMoneyRegister() {
        return moneyRegister;
    }

    public String getMoneyBuy() {
        return moneyBuy;
    }

    public String getMoneyRenew() {
        return moneyRenew;
    }

    public String getNumPlay() {
        return numPlay;
    }

    public void setNumPlay(String numPlay) {
        this.numPlay = numPlay;
    }

    public String getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(String numPlayer) {
        this.numPlayer = numPlayer;
    }

    
}
