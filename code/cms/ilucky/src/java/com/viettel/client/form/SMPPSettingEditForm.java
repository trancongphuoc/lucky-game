/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author quyettc
 */
public class SMPPSettingEditForm {

    private String shortCode;
    private String table;
    private String column;
    private String value;
    private String fakeShortCode;

    public SMPPSettingEditForm() {
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFakeShortCode() {
        return fakeShortCode;
    }

    public void setFakeShortCode(String fakeShortCode) {
        this.fakeShortCode = fakeShortCode;
    }
}
