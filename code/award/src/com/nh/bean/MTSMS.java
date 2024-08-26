/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.bean;

import com.wbxml.WbxmlLibs;


/**
 *
 * @author hoand
 */
public class MTSMS {
    public static final int SMS_TYPE_TEXT = 0;
    public static final int SMS_TYPE_FLASH = 1;
    public static final int SMS_TYPE_BINARY = 2;
    public static final int SMS_TYPE_HEX_TEXT = 3;
    public static final int SMS_TYPE_HEX_FLASH = 4;
    
    private String mDestAddress, mContent, mAlias, mSourceAddress;
    private int mSMSType;
    private String mUserName;
    private String transId;
    private String result;
    private long mCreatedTime;
    private String subServiceName;
    private String contentId;
    
    public MTSMS(String sourceAddress, String destAddress, 
            String mContent, String mAlias,
            int smsType, String username) {
        this.mDestAddress = destAddress;
        this.mContent = mContent;
        this.mAlias = mAlias;
        this.mSourceAddress = sourceAddress;
        mSMSType = smsType;
        mUserName = username;
        mCreatedTime = System.currentTimeMillis();
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }
    
    public String getUsername() {
        return mUserName;
    }
    
    public String getDestAddress() {
        return mDestAddress;
    }

    public String getContent() {
        return mContent;
    }

    public String getAlias() {
        return mAlias;
    }

    public String getSourceAddress() {
        return mSourceAddress;
    }

    public int getSMSType() {
        return mSMSType;
    }
    
    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    
    private String getSMSTypeStr() {
        String result = "Text";
        switch (getSMSType()) {
            case SMS_TYPE_TEXT: result = "Text"; break;
            case SMS_TYPE_FLASH: result = "Flash"; break;
            case SMS_TYPE_BINARY: result = "Binary"; break;
            case SMS_TYPE_HEX_FLASH: result = "Hex Flash"; break;
            case SMS_TYPE_HEX_TEXT: result = "Hex Text"; break;
        }
        return result;
    }
    
    public String getOriginalContent() {
        if (getSMSType() == SMS_TYPE_HEX_TEXT || getSMSType() == SMS_TYPE_HEX_FLASH) {
            return new String(WbxmlLibs.toByteArr(getContent()));
        } else {
            return getContent();
        }
    }
    
    private boolean isHex() {
        return (getSMSType() == SMS_TYPE_HEX_FLASH || getSMSType() == SMS_TYPE_HEX_TEXT);
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
    
    
    @Override
    public String toString() {
        return "(From: " + mSourceAddress + "; " +
               "To: " + mDestAddress + "; " +
//               "Content: " + (isHex() ? new String(WbxmlLibs.toByteArr(getContent())) : getContent()) + "; " +
               "Content: " + getOriginalContent() + "; " +
               "Alias: " + mAlias + "; " +
               "Type: " + getSMSTypeStr() +  ")";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}
