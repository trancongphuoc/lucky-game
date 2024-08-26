/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.bean;

/**
 *
 */
public class SqlExcutor {
//    public static final String TYPE_SELECT = "SELECT";
//    public static final String TYPE_INSERT = "INSERT";
//    public static final String TYPE_UPDATE = "UPDATE";
//    public static final String TYPE_PROCEDURE = "PROCEDURE";
//    public static final String TYPE_FUNCTION = "FUNCTION";
    
    private String id                ;
    private String code              ;
    private String name              ;
//    private String type              ;
    private String sql               ;
    private int timeout              ;
//    private String method            ;
    private String databaseCode         ;
    private int status               ;

    public SqlExcutor(String id, String code, String name, String sql, 
            int timeout,  int status , String databaseCode) {
        this.id = id;
        this.code = code;
        this.name = name;
//        this.type = type;
        this.sql = sql;
        this.timeout = timeout;
        this.status = status;
//        this.method = method;
        this.databaseCode = databaseCode;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

//    public String getType() {
//        return type;
//    }

    public String getSql() {
        return sql;
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

    @Override
    public String toString() {
        return code; //To change body of generated methods, choose Tools | Templates.
    }
    
}
