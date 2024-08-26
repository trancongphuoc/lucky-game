package com.viettel.utils;

import com.viettel.database.bean.Users;

import javax.servlet.http.HttpSession;

/**
 * @author HuongNV-Telsoft
 */
public class Util {
    public final static String USER_TOKEN = Constant.STRING_USERTOKEN;

    public static String nvl(Object objInput, String strNullValue) {
        if (objInput == null) {
            return strNullValue;
        }
        return objInput.toString();
    }

    public static String nvl(String objInput) {
        return nvl(objInput, "");
    }

    public static String nvl(String objInput, String strNullValue) {
        if (objInput == null) {
            return strNullValue;
        }
        return objInput.trim();
    }

    public static boolean isNullOrEmpty(String objInput) {
        if (objInput == null) {
            return true;
        }
        if ("".equals(objInput.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isAdmin(HttpSession reqSess) {
        Users userToken = (Users) reqSess.getAttribute(USER_TOKEN);
        String user_type = userToken.getUserType();
        String username = userToken.getUsername();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN) && !username.isEmpty()) {
            return true;
        }
        return false;
    }
}
