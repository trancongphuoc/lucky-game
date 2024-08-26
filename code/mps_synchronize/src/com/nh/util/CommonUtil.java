/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hadc
 */
public class CommonUtil {
    
    public static int convert2Int(Object tmp) {
        int resultRoutLet;
        if (tmp instanceof BigDecimal) {
            resultRoutLet = ((BigDecimal) tmp).intValue();
        } else if (tmp instanceof Double) {
            resultRoutLet = ((Double) tmp).intValue();
        } else if (tmp instanceof Float) {
            resultRoutLet = ((Float) tmp).intValue();
        } else {
            resultRoutLet = (int) tmp;
        }
        return resultRoutLet;
    }
    
    public Date convertToDate(String dateStr) {
        String pattern1 = "ddMMyyyy";
        try {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
            Date date = simpleDateFormat1.parse(dateStr);
            return date;
        }catch (Exception ex) {
        }
        
        pattern1 = "yyyyMMdd";
        try {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
            Date date = simpleDateFormat1.parse(dateStr);
            return date;
        }catch (Exception ex) {
        }
        

        return null;
    }
    
    public static String getError(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        getError(ex, sb, 0);
        return sb.toString();
    }
    
    private  static void getError(Throwable ex, StringBuilder errorStr, int deep) {
//        ex.getCause().get;
        errorStr.append(ex.getMessage());
        StackTraceElement e[] = ex.getStackTrace();
        for (int i = 0; i < e.length; i++) {
            errorStr.append("\n").append(e[i].toString());
        }
        Throwable thw = ex.getCause();
        if (deep < 3 && thw != null) {
            getError(ex.getCause(), errorStr, deep + 1);
        }
    }
    
    public static String getCurrentDateTime() {
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        return f.format(new java.sql.Date(System.currentTimeMillis()));
    }
}
