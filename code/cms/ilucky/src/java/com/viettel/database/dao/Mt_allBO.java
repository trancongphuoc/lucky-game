/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.sql.Timestamp;

/**
 *
 * @author 
 */
public class Mt_allBO {
        
    private String id                 ;
    private String msisdn             ;
    private int point                 ;
    private Timestamp created         ;
    private Timestamp win_day         ;
    private String description        ;
    private String message_code       ;
    private String code               ;
    private String award_code         ;
    private String game_type          ;
    private int rank                  ;
    private String award_type         ;
    private int status                ;

    public Mt_allBO() {
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getWin_day() {
        return win_day;
    }

    public void setWin_day(Timestamp win_day) {
        this.win_day = win_day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage_code() {
        return message_code;
    }

    public void setMessage_code(String message_code) {
        this.message_code = message_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAward_code() {
        return award_code;
    }

    public void setAward_code(String award_code) {
        this.award_code = award_code;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getAward_type() {
        return award_type;
    }

    public void setAward_type(String award_type) {
        this.award_type = award_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
