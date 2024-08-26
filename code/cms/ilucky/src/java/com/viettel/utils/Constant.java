package com.viettel.utils;

public class Constant {

    public final static int QUERY_TIMEOUT = 50;
    public static String SCHEMA_NAME = "STL_LUCKY";
    public static String CONFIG = "CONFIG_SYSTEM";
    public static final String cnst_prefix_file = "vie_";
    public static final String cnst_suffix_file = ".wav";
    public static final String cnst_suffix_file_mulaw = ".mulaw";
    public static final String cnst_thong_tin_the_thao = "thong_tin_the_thao";
    public static final String cnst_lich_thi_dau_trong_tuan = "lich_thi_dau_trong_tuan";
    public static final String cnst_ket_qua_tran_dau = "ket_qua_tran_dau";
    public static final String cnst_ty_le_dat_cuoc = "ty_le_dat_cuoc";
    public static final String cnst_thong_tin_giai_tri = "thong_tin_giai_tri";

    public static final String USER_TYPE_ADMIN = "1";
    public static final String USER_TYPE_CC = "2";
    public static final String USER_TYPE_VAS = "3";
    public static final String USER_TYPE_CP = "4";
    public static final String USER_TYPE_CC_VAS = "5";
    
    public static final String STRING_NOCHOICE = "-1";
    public static final String STRING_EMPTY = "";
    public static final String STRING_SESSIONTIMEOUT = "sessionTimeout";
    public static final String STRING_USERTOKEN = "userToken";
    public static final String STRING_SUCCESS = "SUCCESS";
    
    public static final String STATUS_OK = "0";
    public static final String COMMAND_REGISTER = "REGISTER";
    public static final String COMMAND_CANCEL = "CANCEL";

    public static final String HTTP_MENU_WEBSERVICE_TYPE = "SOAP";
    public static final long HTTP_MENU_TIME_OUT = 30000;
    public static final String HTTP_MENU_METHOD = "POST";
    public static final String HTTP_MENU_LINE_DOWN = "\\t";
    public static final long RECORD_PER_PAGE = 3;
    public static final String HTTP_MENU_NEXT_CHAR = "9";
    public static final String HTTP_MENU_BACK_CHAR = "0";
    public static final String HTTP_MENU_NEXT_CONTENT = "9. next";
    public static final String HTTP_MENU_BACK_CONTENT = "0. back";
    public static final String HTTP_MENU_SPLIT_MENU = ".";
    public static final String HTTP_MENU_RETURN_SUCCESS_VALUE = "0";
    public static final String HTTP_MENU_MENU_PARENT = "0";

    public static final String SQL_MENU_TIMEOUT = "30000";
    public static final int SQL_MENU_STATUS = 1;
    public static final Long SQL_MENU_RECORD_PER_PAGE = 3L;
    public static final String SQL_MENU_LINE_DOWN = "\\t";
    public static final String SQL_MENU_NEXT_CHAR = "9";
    public static final String SQL_MENU_BACK_CHAR = "0";
    public static final String SQL_MENU_NEXT_CONTENT = "9. next";
    public static final String SQL_MENU_BACK_CONTENT = "0. back";
    public static final String SQL_MENU_SPLIT_MENU = ".";
    
    public static void LogNo(Exception ex){}
    public static void LogNo(Throwable ex){}

}
