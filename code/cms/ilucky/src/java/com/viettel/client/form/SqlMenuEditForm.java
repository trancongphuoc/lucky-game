/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author TRIEUNGUYEN
 */
public class SqlMenuEditForm {
    private String id;
    private String name;
    private String code;
    private String description;
    private String selectSql;
    private String timeout;
    private int status;
    private String databaseCode;
    private Long recordPerPage;
    private String lineDown;
    private String nextChar;
    private String backChar;
    private String nextContent;
    private String backContent;
    private String splitMenu;

    public SqlMenuEditForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

  

    public String getDatabaseCode() {
        return databaseCode;
    }

    public void setDatabaseCode(String databaseCode) {
        this.databaseCode = databaseCode;
    }

    public Long getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(Long recordPerPage) {
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
    
    
}
