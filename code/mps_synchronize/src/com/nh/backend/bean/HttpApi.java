/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

/**
 *
 */
public class HttpApi {
    public static final String WEBSERVICE_TYPE_SOAP = "SOAP";
    public static final String WEBSERVICE_TYPE_RESTFUL = "RESTFUL";
    
    public static final String LOAD_CONDITION_NOLOAD = "0";
    public static final String LOAD_CONDITION_ON_SUCCESS = "1";
    public static final String LOAD_CONDITION_LOAD_ANY = "2";
    
    private String id;
    private String userName;
    private String password;
    private String url;
    private String params;
    private String webserviceType;
    private String code;
    private String descriptions;
    private String body;
    private String returnTag;
    private int timeout;
    private int returnAuto;
    private String method;
    private String name;
    private String returnSuccessValue;
    private String loadCondition;
    private String loadField;
    private String moduleCode;
    

    public HttpApi() {
        
    }

    public HttpApi(String id, String userName, String password, String url,
            String params, String webserviceType, String code, String descriptions, 
            String body, String returnTag, int timeout, String method, 
            String name, String returnSuccessValue, String loadCondition,
            String loadField, String moduleCode) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.params = params;
        this.webserviceType = webserviceType;
        this.code = code;
        this.descriptions = descriptions;
        this.body = body;
        this.returnTag = returnTag;
        this.timeout = timeout;
        this.method = method;
        this.name = name;
        this.returnSuccessValue = returnSuccessValue;
        this.loadCondition = loadCondition;
        this.loadField = loadField;
        this.moduleCode = moduleCode;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getWebserviceType() {
        return webserviceType;
    }

    public void setWebserviceType(String webserviceType) {
        this.webserviceType = webserviceType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReturnTag() {
        return returnTag;
    }

    public void setReturnTag(String returnTag) {
        this.returnTag = returnTag;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnSuccessValue() {
        return returnSuccessValue;
    }

    public void setReturnSuccessValue(String returnSuccessValue) {
        this.returnSuccessValue = returnSuccessValue;
    }

    public String getLoadCondition() {
        return loadCondition;
    }

    public void setLoadCondition(String loadCondition) {
        this.loadCondition = loadCondition;
    }

    public String getLoadField() {
        return loadField;
    }

    public void setLoadField(String loadField) {
        this.loadField = loadField;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public int getReturnAuto() {
        return returnAuto;
    }

    public void setReturnAuto(int returnAuto) {
        this.returnAuto = returnAuto;
    }
    
    @Override
    public String toString() {
        return "(getName: " + getName()+ "; " + 
               "getName: " + getUrl()+ "";
    }
    
}
