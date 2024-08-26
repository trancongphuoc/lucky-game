/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.viettel.luckydraw.util.Config;
import com.viettel.luckydraw.util.Constant;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author manhnv23
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGwRequest implements Serializable {

    String sessionId;
    String wsCode;
    String apiKey;

    Map<String, String> wsRequest;

    public ApiGwRequest() {
    }

    public ApiGwRequest(String sessionId, String wsCode, String apiKey, Map<String, String> wsRequest) {
        this.sessionId = sessionId;
        this.wsCode = wsCode;
        this.apiKey = apiKey;
        this.wsRequest = wsRequest;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getWsCode() {
        return wsCode;
    }

    public void setWsCode(String wsCode) {
        this.wsCode = wsCode;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Map<String, String> getWsRequest() {
        return wsRequest;
    }

    public void setWsRequest(Map<String, String> wsRequest) {
        this.wsRequest = wsRequest;
    }

    public String validate(ApiGwRequest req) {
        String res = null;
        String gameCodeLb = "gameCode";
        if(!Constant.WS_GET_ISDN.equals(req.getWsCode())){
          //  System.out.println("Vao day chua ");
            if ( req.getWsRequest().get("isdn") == null || req.getWsRequest().get("isdn").trim().isEmpty() || req.getWsRequest().get("isdn").length() > 20) {
                res = "Invalid isdn";
            } else {
                if (req.getWsRequest().get(gameCodeLb) == null || req.getWsRequest().get(gameCodeLb).trim().isEmpty() || req.getWsRequest().get(gameCodeLb).length() > 50) {
                    res = "Invalid gameCode";
                }
            }
        }

        return res;
    }

    @Override
    public String toString() {
        return "ApiGwRequest{" + "sessionId=" + sessionId + ", wsCode=" + wsCode + ", apiKey=" + apiKey + ", wsRequest=" + wsRequest + '}';
    }


}
