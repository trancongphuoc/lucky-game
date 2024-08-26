package com.viettel.database.bean;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
public class Users implements java.io.Serializable {
    
    public static final String ROLE_ADMIN = "1";
    public static final String ROLE_CC = "2";
    public static final String ROLE_BUSINESS = "3";
    public static final String ROLE_BUSSINESS_CC = "5";
    public static final String ROLE_PARTNER = "4";
    
    // Fields  
    private String username;
    private String password;
    private String status;
    private String descriptions;
    private String userType;

    // Constructors
    /** default constructor */
    public Users() {
    }

    /** minimal constructor */
    public Users(String username, String password, String status, String descriptions, String userType) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.descriptions = descriptions;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }    
    
}