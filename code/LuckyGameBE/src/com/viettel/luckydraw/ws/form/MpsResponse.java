/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

/**
 *
 * @author MrCAP
 */
public class MpsResponse {

    private String response;
    private String errorCode;
    private int httpStatus;
    private String desciption;
    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus the httpStatus to set
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @return the desciption
     */
    public String getDesciption() {
        return desciption;
    }

    /**
     * @param desciption the desciption to set
     */
    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
   
    
}
