/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.bo;

/**
 *
 * @author HALT14
 */
public class ActionLog {
    private String request;
    private String response;
    private String isdn;
    private String type;
    private long duration;
    private String method;

    public ActionLog(String request, String response, String isdn, long duration, String method) {
        this.request = request;
        this.response = response;
        this.isdn = isdn;
        this.duration = duration;
        this.method = method;
    }

    

    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }

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
     * @return the isdn
     */
    public String getIsdn() {
        return isdn;
    }

    /**
     * @param isdn the isdn to set
     */
    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }
    
}
