/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.bo;

import java.io.Serializable;

public class LuckyGamePlayTurn
        implements Serializable
{
    private String playId;
    private String playType;
    private String playStatus;
    private String playRequestDate;
    private String msisdn;
    private String friendId;
    private String groupType;
    private String giftCode;
    private String giftDesc;
    private String giftDate;
    private String giftOrder;
    private String lang;
    private String errorCode;
    private String errorDesc;
    private String updateTime;
    private String giftType;
    private long fee;
    private String giftValue;

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public String getPlayRequestDate() {
        return playRequestDate;
    }

    public void setPlayRequestDate(String playRequestDate) {
        this.playRequestDate = playRequestDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    public String getGiftDate() {
        return giftDate;
    }

    public void setGiftDate(String giftDate) {
        this.giftDate = giftDate;
    }

    public String getGiftOrder() {
        return giftOrder;
    }

    public void setGiftOrder(String giftOrder) {
        this.giftOrder = giftOrder;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MiniGame2018PlayTurn{" + "playId=" + playId + ", playType=" + playType + ", playStatus=" + playStatus + ", playRequestDate=" + playRequestDate + ", msisdn=" + msisdn + ", friendId=" + friendId + ", groupType=" + groupType + ", giftCode=" + giftCode + ", giftDesc=" + giftDesc + ", giftDate=" + giftDate + ", giftOrder=" + giftOrder + ", lang=" + lang + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc + ", updateTime=" + updateTime + ", giftType=" + giftType + '}';
    }

    
    
    /**
     * @return the giftType
     */
    public String getGiftType() {
        return giftType;
    }

    /**
     * @param giftType the giftType to set
     */
    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    /**
     * @return the fee
     */
    public long getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(long fee) {
        this.fee = fee;
    }

    /**
     * @return the giftValue
     */
    public String getGiftValue() {
        return giftValue;
    }

    /**
     * @param giftValue the giftValue to set
     */
    public void setGiftValue(String giftValue) {
        this.giftValue = giftValue;
    }

   
  
  
}
