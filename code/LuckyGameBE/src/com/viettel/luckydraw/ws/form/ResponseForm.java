/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

/**
 *
 * @author manhnv23
 */
public class ResponseForm {

    /**
     * @return the giftMsg
     */
    public String getGiftMsg() {
        return giftMsg;
    }

    /**
     * @param giftMsg the giftMsg to set
     */
    public void setGiftMsg(String giftMsg) {
        this.giftMsg = giftMsg;
    }
    String isdn;
    String time;
    String giftCode;
    String giftDesc;
    String giftAccId;
    String giftValue;
    String result;
    String playId;
    private String giftType;
    private String giftMsg;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGiftAccId() {
        return giftAccId;
    }

    public void setGiftAccId(String giftAccId) {
        this.giftAccId = giftAccId;
    }

    public String getGiftValue() {
        return giftValue;
    }

    public void setGiftValue(String giftValue) {
        this.giftValue = giftValue;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    @Override
    public String toString() {
        return "ResponseForm{" + "isdn=" + isdn + ", time=" + time + ", giftCode=" + giftCode + ", giftDesc=" + giftDesc + ", giftAccId=" + giftAccId + ", giftValue=" + giftValue + ", result=" + result + ", playId=" + playId + '}';
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

   
   
    
}
