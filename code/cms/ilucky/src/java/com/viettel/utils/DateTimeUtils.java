/*
 * DateTimeUtils.java
 *
 * Created on August 6, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.viettel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Vu Thi Thu Huong
 */
public class DateTimeUtils {

    public DateTimeUtils() {
    }

    public static Date convertStringToTime(String date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
        }
        return null;
    }

    public static Date convertStringToDate(String date) throws Exception {
        //String pattern = "dd/MM/yyyy";
        String pattern = "yyyy-MM-dd";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDate(String date, String pattern) {
        return convertStringToTime(date, pattern);
    }

    public static String convertDateToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static String convertDateToString(Date date, String format) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static String getSysdate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        return convertDateToString(calendar.getTime());
    }

    public static String getSysDateTime() throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static String getSysDateTime(String pattern) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static Date convertStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDateTime(String date, String pattern) throws Exception {

        return convertStringToTime(date, pattern);
    }

    public static String convertDateTimeToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.format(date);
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }
    
    public static String convertDateTimeToString2(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.format(date);
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static String convertDateTimeToString(Date date, SimpleDateFormat dateFormat) throws Exception {
        try {
            return dateFormat.format(date);
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static java.sql.Date convertToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    public static String formatDateString(String date) {
        String strDateReturn = "";
        try {
            strDateReturn = date.substring(6) + date.substring(3, 5) + date.substring(0, 2);
        } catch (Exception ex) {Constant.LogNo(ex);
            strDateReturn = "";
        }
        return strDateReturn;
    }

    /**
     * @param monthInput
     * @anhlt - Get the first day on month selected.
     * @return
     */
    public static String parseDate(int monthInput) {
        String dateReturn = "01/01/";
        Calendar cal = Calendar.getInstance();
        switch (monthInput) {
            case 1:
                dateReturn = "01/01/";
                break;
            case 2:
                dateReturn = "01/02/";
                break;
            case 3:
                dateReturn = "01/03/";
                break;
            case 4:
                dateReturn = "01/04/";
                break;
            case 5:
                dateReturn = "01/05/";
                break;
            case 6:
                dateReturn = "01/06/";
                break;
            case 7:
                dateReturn = "01/07/";
                break;
            case 8:
                dateReturn = "01/08/";
                break;
            case 9:
                dateReturn = "01/09/";
                break;
            case 10:
                dateReturn = "01/10/";
                break;
            case 11:
                dateReturn = "01/11/";
                break;
            case 12:
                dateReturn = "01/12/";
                break;
        }
        return dateReturn + cal.get(Calendar.YEAR);
    }
    
    public static Date getPreviousMonthDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -30);
        return cal.getTime();
    }
    
    public static Date getPrevious7Date() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -7);
        return cal.getTime();
    }
    
    public static Date getPreviousDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    
    public static Date getToDay() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
}
