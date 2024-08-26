/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "CREDIT_CONDITION")
public class CreditCondition extends PO{
    
    public static final String OPERATOR_EQUAL = "=";
    public static final String OPERATOR_SMALLER = "<";
    public static final String OPERATOR_BIGGER = ">";
    public static final String OPERATOR_IN = "IN";
    
    public static final String DATA_TYPE_STRING = "STRING";
    public static final String DATA_TYPE_DOUBLE = "DOUBLE";
    public static final String DATA_TYPE_DATE = "DATE";
    
    public static final String VALUES_SPLIT = ",";
    
    public static final String TYPE_CDR_FILTER = "CDR";
    public static final String TYPE_OCS_REGISTER = "OCS_REGISTER";
    public static final String TYPE_OCS_CHECK = "OCS_CHECK";
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "piority")
    private String piority;
    
    @Column(name = "KEY")
    private String key;
    
    @Column(name = "operator")
    private String operator;
    
    @Column(name = "data_type")
    private String dataType;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "value")
    private String value ;

    public CreditCondition(){
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPiority() {
        return piority;
    }

    public void setPiority(String piority) {
        this.piority = piority;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return id;
    }
    
}
