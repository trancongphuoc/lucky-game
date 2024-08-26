/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

/**
 *
 */
public class HttpMenu {
    
    private String id;
    private String userName;
    private String password;
    private String url;
    private String webserviceType;
    private String name;
    private String code;
    private String description;
    private String body;
    private int timeout;
    private String method;
    private String status;
    private String moduleCode;
    private String returnTag;
    private String returnSuccessValue;
    private String menuParent;
    private String menuName;
    private String menuValue;
    private String contextName;
    private String contextValue;

    private int recordPerPage ;
    private String lineDown      ;
    private String nextChar      ;
    private String backChar      ;
    private String nextContent   ;
    private String backContent   ;
    private String splitMenu     ;
    
    public HttpMenu(String id, String userName, String password, String url, 
            String webserviceType, String name, String code, String description,
            String body, int timeout, String method, String status, 
            String moduleCode, String returnTag, String returnSuccessValue, 
            String menuParent, String menuName, String menuValue, 
            String contextName, String contextValue) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.webserviceType = webserviceType;
        this.name = name;
        this.code = code;
        this.description = description;
        this.body = body;
        this.timeout = timeout;
        this.method = method;
        this.status = status;
        this.moduleCode = moduleCode;
        this.returnTag = returnTag;
        this.returnSuccessValue = returnSuccessValue;
        this.menuParent = menuParent;
        this.menuName = menuName;
        this.menuValue = menuValue;
        this.contextName = contextName;
        this.contextValue = contextValue;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getWebserviceType() {
        return webserviceType;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getReturnTag() {
        return returnTag;
    }

    public String getReturnSuccessValue() {
        return returnSuccessValue;
    }

    public String getMenuParent() {
        return menuParent;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public String getContextName() {
        return contextName;
    }

    public String getContextValue() {
        return contextValue;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public String getLineDown() {
        return lineDown;
    }

    public void setLineDown(String lineDown) {
        this.lineDown = lineDown;
    }

    public String getNextChar() {
        return nextChar;
    }

    public void setNextChar(String nextChar) {
        this.nextChar = nextChar;
    }

    public String getBackChar() {
        return backChar;
    }

    public void setBackChar(String backChar) {
        this.backChar = backChar;
    }

    public String getNextContent() {
        return nextContent;
    }

    public void setNextContent(String nextContent) {
        this.nextContent = nextContent;
    }

    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public String getSplitMenu() {
        return splitMenu;
    }

    public void setSplitMenu(String splitMenu) {
        this.splitMenu = splitMenu;
    }
    
    
    
    @Override
    public String toString() {
        return "(getName: " + getName()+ "; " + 
               "getName: " + getUrl()+ "";
    }
    
}
