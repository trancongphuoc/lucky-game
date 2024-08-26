/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.bean;

/**
 *
 */
public class SqlMenu {

    private String id;
    private String name;
    private String code;
    private String description;
    private String selectSql;
    private int timeout;
    private int status;
    private String databaseCode;
    private int recordPerPage;
    private String lineDown;
    private String nextChar;
    private String backChar;
    private String nextContent;
    private String backContent;
    private String splitMenu;

    public SqlMenu(String id, String name, String code, String description, 
            String selectSql, int timeout, int status, String databaseCode, 
            int recordPerPage, String lineDown, String nextChar, String backChar, 
            String nextContent, String backContent, String splitMenu) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.selectSql = selectSql;
        this.timeout = timeout;
        this.status = status;
        this.databaseCode = databaseCode;
        this.recordPerPage = recordPerPage;
        this.lineDown = lineDown;
        this.nextChar = nextChar;
        this.backChar = backChar;
        this.nextContent = nextContent;
        this.backContent = backContent;
        this.splitMenu = splitMenu;
    }

    public String getId() {
        return id;
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

    public String getSelectSql() {
        return selectSql;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getStatus() {
        return status;
    }

    public String getDatabaseCode() {
        return databaseCode;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public String getLineDown() {
        return lineDown;
    }

    public String getNextChar() {
        return nextChar;
    }

    public String getBackChar() {
        return backChar;
    }

    public String getNextContent() {
        return nextContent;
    }

    public String getBackContent() {
        return backContent;
    }

    public String getSplitMenu() {
        return splitMenu;
    }

    @Override
    public String toString() {
        return code; 
    }
    
    
}
