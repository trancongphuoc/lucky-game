/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.utils;

/**
 *
 * @author Ebaymark
 */
public class SqlConstants {

    public static final String SQL_INSERT_USER_INFO = ""
            + " INSERT INTO TBL_USERS(USER_ID, USERNAME, PASSWORD, EMAIL, PHONE, STATUS) VALUES(?, ?, ?, ?, ?, ?) ";

    public static final String SQL_SELECT_USER_INFO = ""
            + " SELECT USER_ID, USERNAME, PASSWORD, EMAIL, PHONE, STATUS "
            + " FROM TBL_USERS "
            + " WHERE USER_ID = ? "
            + " ORDER BY USER_ID ";
//    public static final String SQL_SELECT_USER_INFO_ROLE = "";
    public static final String SQL_SELECT_USER_LIST = ""
            + " SELECT USER_ID, USERNAME, PASSWORD, EMAIL, PHONE, STATUS "
            + " FROM TBL_USERS "
            + " ORDER BY USER_ID ";
    public static final String SQL_SELECT_USER_INFO_ROLES = ""
            + " SELECT users.USER_ID, USERNAME, PASSWORD, EMAIL, PHONE, STATUS, userrole.ROLE_ID "
            + " FROM TBL_USERS users, TBL_USER_ROLES userrole "
            + " WHERE users.USER_ID = ? AND "
            + " users.USER_ID = userrole.USER_ID(+) ";
    public static final String SQL_UPDATE_USER = ""
            + " UPDATE TBL_USERS SET PASSWORD=?, EMAIL=?, PHONE=?, STATUS=? WHERE USER_ID = ? ";
    public static final String SQL_DELETE_USER = ""
            + " DELETE FROM TBL_USERS WHERE USER_ID = ? ";
    // ************** TBL_USER_ROLES ***********************
    public static final String SQL_SELECT_USER_ROLES = ""
            + " SELECT * FROM TBL_USER_ROLES ORDER BY USER_ID";
    public static final String SQL_INSERT_USER_ROLE = ""
            + " INSERT INTO TBL_USER_ROLES VALUES(?,?) ";
    public static final String SQL_DELETE_USER_ROLE = ""
            + " DELETE FROM TBL_USER_ROLES WHERE USER_ID = ? AND ROLE_ID = ? ";
    // ************** TBL_ROLES ****************************
    public static final String SQL_SELECT_ROLE_INFO = ""
            + " SELECT * FROM TBL_ROLES WHERE ROLE_ID = ? ";
    public static final String SQL_SELECT_ROLE_LIST = ""
            + " SELECT * FROM TBL_ROLES ";
    public static final String SQL_INSERT_ROLE = ""
            + " INSERT INTO TBL_ROLES VALUES (?, ?, ?) ";
    public static final String SQL_UPDATE_ROLE = ""
            + " UPDATE TBL_ROLES SET ROLE_NAME = ?, ROLE_DESCRIPTION = ? ";
    public static final String SQL_DELETE_ROLE = ""
            + " DELETE FROM TBL_ROLES WHERE ROLE_ID = ? ";
}
