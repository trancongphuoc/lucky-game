/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author haind25
 */
public class PromotionCommandsBO implements Serializable{


    public static final int CHECK_PROMOTION = 1; // gui soap service toi cp xem co duoc khuyen mai ko
    
    
    /*
    * kenh se ap dung khuyen mai, mac dinh ALL, khi ap dung se ap dung cho 
    * tat ca cac kenh su dung goi cuoc do
    */
    public static final String COMMAND_ALL = "ALL";
    public static final String COMMAND_SMS = "SMS";
    public static final String COMMAND_USSD = "USSD";
    public static final String COMMAND_WEB = "WEB";
    public static final String COMMAND_WAP = "WAP";
    public static final String COMMAND_IVR = "IVR";
    public static final String COMMAND_SYS = "SYS";
    public static final String COMMAND_RENEW = "RENEW";
    
    
    /*
    * MESSAGE_CHANNEL ket qua tra ve cho nguoi dung
    * MESSAGE_CHANNEL_SELF la tra ve cho nguoi dung bang kenh ho dang su dung,
    * vi du nhu dang dung USSD thi nhan lai bang ussd, sms thi nhan bang sms
    */
    public static final int MESSAGE_CHANNEL_SELF = 10;
    
    /**
     * loai khuyen mai, cac dot chinh sach khuyen mai cua thi truong se tuy 
     * chinh tu day. voi moi loai khuyen mai khac nhau se chinh sua chuong trinh
     * mot cach khac nhau
     */
    public static final String PROMOTION_TYPE_FIRST_REGISTER = "FIRST_REGISTER";
    
    
    private String id;
    private String subServiceName; //SUB_SERVICE_NAME
    private String code; //ma khuyen mai la duy nhat
    private int promotionChannel; //kenh duoc huong khuyen mai, COMMAND_SMS, ...
    private int status;
    private Date fromDate;
    private Date toDate;
    private String type;
    
    private String description;
    private String successMessage;
    private String confirmMessage;
    private String failMessage;
    private String chargeCommand;
    
    private int webserviceId;
    private int checkRequired;// CHECK_REQUIRED;
    private int freeDay;
    private int cmdPrice;//CMD_Price;
    private int messageChannel;
   
    public PromotionCommandsBO(){
        
    }
    
    public PromotionCommandsBO(String id, String subServiceName, String code, int status, 
            int promotionChannel, String description, String successMessage,
            String failMessage, String chargeCommand, String type,
            int webserviceId, int checkRequired, int freeDay, 
            int cmdPrice, int messageChannel, Date fromDate, Date toDate) {
        this.id = id;
        this.subServiceName = subServiceName;
        this.code = code;
        this.status = status;
        this.promotionChannel = promotionChannel;
        this.description = description;
        this.successMessage = successMessage;
        this.failMessage = failMessage;
        this.chargeCommand = chargeCommand;
        this.type = type;
        this.webserviceId = webserviceId;
        this.checkRequired = checkRequired;
        this.freeDay = freeDay;
        this.cmdPrice = cmdPrice;
        this.messageChannel = messageChannel;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPromotionChannel() {
        return promotionChannel;
    }

    public void setPromotionChannel(int promotionChannel) {
        this.promotionChannel = promotionChannel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getChargeCommand() {
        return chargeCommand;
    }

    public void setChargeCommand(String chargeCommand) {
        this.chargeCommand = chargeCommand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWebserviceId() {
        return webserviceId;
    }

    public void setWebserviceId(int webserviceId) {
        this.webserviceId = webserviceId;
    }

    public int getCheckRequired() {
        return checkRequired;
    }

    public void setCheckRequired(int checkRequired) {
        this.checkRequired = checkRequired;
    }

    public int getFreeDay() {
        return freeDay;
    }

    public void setFreeDay(int freeDay) {
        this.freeDay = freeDay;
    }

    public int getCmdPrice() {
        return cmdPrice;
    }

    public void setCmdPrice(int cmdPrice) {
        this.cmdPrice = cmdPrice;
    }

    public int getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(int messageChannel) {
        this.messageChannel = messageChannel;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getConfirmMessage() {
        return confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }
    
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
    public String getMessage(boolean isPromotion) {
        if (isPromotion) {
            return  getSuccessMessage();
        }
       
        return getFailMessage();
    }
    
    Timestamp datetime;
    public void setDatetime(Timestamp timestamp) {
        datetime = timestamp;
    }
    
}