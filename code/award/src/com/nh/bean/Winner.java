package com.nh.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "WINNER")
public class Winner extends PO {
    
    public static final int STATUS_DRAFT = 1;
    public static final int STATUS_APPROVED = 3;
    public static final int STATUS_PAID = 2;
    public static final int STATUS_ERROR = 4;//error
    
    public static final String DESCRIPTION_ALREADY_PAID = "ALREADY PAID";
    public static final String DESCRIPTION_ERROR_PAID = "ERROR PAID";
    
    /**
     *
     */
    private static final long serialVersionUID = 121231L;

    private String msisdn             ;
    private int point                 ;
    private Timestamp winDay          ;
    private String messageCode        ;
    private String code               ;
    private String awardCode          ;
    private String gameType           ;
    private int rank                  ;
    private int awardType             ;
    private int status                ;

    public Winner() {
       
    }

    @Column(name = "MSISDN")
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Column(name = "POINT")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
    @Column(name = "WIN_DAY")
    public Timestamp getWinDay() {
        return winDay;
    }

    public void setWinDay(Timestamp winDay) {
        this.winDay = winDay;
    }

    @Column(name = "MESSAGE_CODE")
    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "AWARD_CODE")
    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

    @Column(name = "GAME_TYPE")
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Column(name = "RANK")
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Column(name = "AWARD_TYPE")
    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   

}
