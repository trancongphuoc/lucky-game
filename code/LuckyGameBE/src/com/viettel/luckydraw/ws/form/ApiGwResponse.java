/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.viettel.luckydraw.util.Views;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author manhnv23
 */
public class ApiGwResponse implements Serializable {

    @JsonView(Views.Manager.class)
    Map<String, Object> wsResponse;
    
    @JsonView(Views.List.class)
    @JsonProperty(value = "wsResponseList")
    List<Map<String, Object>> wsResponseList;
    @JsonView(Views.Normal.class)
    String errorCode;
    @JsonView(Views.Normal.class)
    String message;
    @JsonView(Views.Normal.class)
    String object;

    public Map<String, Object> getWsResponse() {
        return wsResponse;
    }

    public void setWsResponse(Map<String, Object> wsResponse) {
        this.wsResponse = wsResponse;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Map<String, Object>> getWsResponseList() {
        return wsResponseList;
    }

    public void setWsResponseList(List<Map<String, Object>> wsResponseList) {
        this.wsResponseList = wsResponseList;
    }

    @Override
    public String toString() {
        return "ApiGwResponse{" + "wsResponse=" + wsResponse + ", wsResponseList=" + wsResponseList + ", errorCode=" + errorCode + ", message=" + message + ", object=" + object + '}';
    }

    

}
