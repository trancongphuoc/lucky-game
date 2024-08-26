/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import com.opensymphony.xwork2.ActionContext;
import com.vas.util.ParamsVT;
import com.vas.util.StringUtilVT;
import com.viettel.utils.Constant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.viettel.database.utils.DataStore;
import com.viettel.database.utils.DbCloseConn;
import com.viettel.util.VGenLog;
import java.text.*;
import com.viettel.client.form.LoginForm;
import com.viettel.database.bean.Users;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.viettel.framework.interceptor.BaseDAOMDBAction;
import com.viettel.utils.utils;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.http.*;

/**
 *
 * @author Meyyappan Muthuraman
 */
public class Login extends BaseDAOMDBAction {

    utils utils = new utils();
    private String username;
    private String password;
    private User checkUser;   

    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }
    HttpServletRequest req;
    HttpSession reqSess;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    LoginForm loginForm = new LoginForm();

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }

    public Login() {
    }

    @Override
    public String initCore() {
        String s1 = "";
        try {
            req = getRequest(); 
            
            reqSess = req.getSession(true);
            VGenLog.info("SESSION: INIT CORE %%% " + reqSess.toString());
//            reqSess.invalidate();
            Users userid = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);            
            if (reqSess == null || reqSess.isNew() || userid == null) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                return Constant.STRING_SESSIONTIMEOUT;
            } else {
                s1 = "success";
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.info("ERROR: INIT CORE %%% " + ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    @Override
    public void validate() {
    }

    private void incSessLogin() {
        if (reqSess.getAttribute("login") == null) {
            reqSess.setAttribute("login", "0");
        } else {
            int intLogin = Integer.parseInt(reqSess.getAttribute("login").toString());            
            intLogin += 1;
            VGenLog.info("intLogin:" + intLogin);
            reqSess.setAttribute("login", String.valueOf(intLogin));
        }
    }

    @Override
    public String execute() {
        try {
            initCore();
            int numcaptCheck;            
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
            Boolean isResponseCorrect = Boolean.FALSE;
            javax.servlet.http.HttpSession session = request.getSession(true);
            
            if (reqSess.getAttribute("login") == null) {
                numcaptCheck = 0;
            } else {
                numcaptCheck = Integer.parseInt(reqSess.getAttribute("login").toString());
            }
            

            String strSecureLetter = "";
            String strSecureLetterSession = "";
            strSecureLetter = StringUtilVT.nvl(request.getParameter("secureletter"), "");
            strSecureLetterSession = (String) session.getAttribute("CAPTCHA");
            if (numcaptCheck >= 3) {
                try {
                    strSecureLetterSession = ParamsVT.enc.decode(strSecureLetterSession);
                } catch (Exception ex) {Constant.LogNo(ex);
                    java.util.logging.Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!strSecureLetterSession.equals(strSecureLetter)) {
//                    req.setAttribute("warning", "* Mã xác thực không chính xác");
                    req.setAttribute("warning", "login.error");
                    return "input";
                }
            }
            if (reqSess.getAttribute("login") == null) {
                reqSess.setAttribute("login", "0");
            }
            if (Integer.parseInt(reqSess.getAttribute("login").toString()) > 5) {            
                req.setAttribute("warning", "login.limit");
                return "input";
            }
            Connection conn = null;
            Users user = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            Users users = null;
            ResultSet rs = null;
            PreparedStatement stmt = null;
            String strLocked = "";
            int strFirstTimelogin = 0;
            Date dtLastUpdate = null;
            String strUsername = "";
            VGenLog.info("----Login:" + username + "|IP:" + req.getRemoteAddr() + "-Starting....");            
            if (user != null) {
                req.setAttribute("error", "notError");
                //session da ton tai.
            } else {
                //Login  lan dau
                if (getUsername() == null || getUsername().length() == 0) {
                    addFieldError("userName", "User Name is required");
                    req.setAttribute("error", "userIsBlank");
                    req.setAttribute("warning", "login.errorname");
                    return "input";
                } else if (getPassword() == null || getPassword().length() == 0) {
                    addFieldError("passWord", getText("passWord.required"));
                    req.setAttribute("error", "passIsBlank");
                    req.setAttribute("warning", "login.errorpass");
                    return "input";
                } else {
                    try {
                        if (conn == null || conn.isClosed()) {
                            conn = DataStore.getConnection();
                        }
                        if (conn == null) {
                            req.setAttribute("error", "userNotExist");
                            req.setAttribute("warning", "login.disconnect");
                            return "input";
                        }
                        String sql = "SELECT username, password, status, descriptions, USER_TYPE, "
                                + "to_char(LAST_UPDATE_PASSWD,'dd-MM-yyyy') as lastupdatepassword, "
                                + "FIRST_TIME_LOGIN FROM "
                                + Constant.SCHEMA_NAME + ".LUCKY_USERS "
                                //                                + " where username=?";
                                + " where username=? and password=? "
//                                + "and (USER_TYPE = 3 or USER_TYPE = 4 or username in ('admin','thangtq11','hungtv45','haind25','tiennt11','chiemhv','tamnh7','quanna')) ";// check admin/CP
                                + "and USER_TYPE in (1, 2 , 3, 4) ";// check admin/CP
//                        VGenLog.info(sql);
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, username.replace("'", ""));
                        stmt.setString(2, utils.SHA1(password.replace("'", "")));
//                        VGenLog.info(utils.SHA1(password.replace("'", "")));
                        stmt.setQueryTimeout(Constant.QUERY_TIMEOUT);
                        rs = stmt.executeQuery();
                        while (rs.next()) {
                            users = new Users();
                            strLocked = rs.getString("status");
                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                dtLastUpdate = formatter.parse(rs.getString("lastupdatepassword"));
                                if (isExpiredPasswordwarning(dtLastUpdate)) {
                                    reqSess.setAttribute("informationLogin", "Alarm: Account over 90 days, Please change password!!!");
                                } else {
                                    reqSess.setAttribute("informationLogin", null);
                                }
                                if (isExpiredPassword(dtLastUpdate)) {
                                    reqSess.setAttribute("loginUpdatepass", "0");
                                    reqSess.setAttribute("warning", "Alarm: Account over 90 days, Please change password!!!");
                                } else {
                                    reqSess.setAttribute("loginUpdatepass", "1");
                                }
                            } catch (Exception e) {Constant.LogNo(e);//                                                                 
                                reqSess.setAttribute("loginUpdatepass", "0"); //warning
                                reqSess.setAttribute("warning", "Alarm: Account over 90 days, Please change password!!!");//                               
                                VGenLog.error(e);
                            }
                            strFirstTimelogin = rs.getInt("FIRST_TIME_LOGIN");
                            if (strFirstTimelogin == 0) {
                                reqSess.setAttribute("warning", "login.first");
                                reqSess.setAttribute("changePass", "0");
                            } else {
                                reqSess.setAttribute("changePass", "1");
                            }
                            users.setStatus(rs.getString("status"));
                            users.setUserType(rs.getString("USER_TYPE"));
                            strUsername = rs.getString("username");
                            users.setUsername(strUsername);
                        }
                        if (rs != null) {
                            rs.close();
                        }
                        stmt.close();
                        conn.close();
                    } catch (Exception e) {Constant.LogNo(e);
                        //e.printStackTrace();
                        VGenLog.error("----Login:" + username + "---- IP:" + req.getRemoteAddr() + "Err:" + e.toString());
                    } finally {
                        DbCloseConn.closeQuietly("Login", "execute()", conn, stmt, rs);
                    }
                    if (users == null) {
                        VGenLog.info("----Login:" + username + "|IP:" + req.getRemoteAddr() + "-Cannot get user");
                        addFieldError("userName", "Invalid User");
                        addFieldError("passWord", "Invalid PassWord");
                        req.setAttribute("error", "userNotExist");
                        req.setAttribute("warning", "login.fail");
                        incSessLogin();
                        return "input";
                    } else {
                        if (!strLocked.trim().equals(Constant.USER_TYPE_ADMIN)) {
                            req.setAttribute("error", "userNotExist");
                            req.setAttribute("warning", "login.lockuser");
                            incSessLogin();
                            return "input";
                        }
                        if (users.getStatus() == null || users.getStatus().length() == 0) {
                            incSessLogin();
                            return "input";
                        }
                        req.setAttribute("error", "notError");
                        reqSess.setAttribute(Constant.STRING_USERTOKEN, users);
                        //-----------check dang nhap 1 user----
                        checkUser = (User) reqSess.getAttribute("user");
                        if (checkUser == null) {//check user name da ton tai chua
                            checkUser = new User(username);
                            reqSess.setAttribute("user", checkUser);
                        } else {
                            VGenLog.info("----Login:----" + strUsername + " IP:" + req.getRemoteAddr() + "|Login exist!");
                            return "input";
                        }
                        //-----end check dang nhap 1 user                           
                        req.setAttribute("warning", "");
                        if ("0".equals(reqSess.getAttribute("changePass").toString()) || "0".equals(reqSess.getAttribute("loginUpdatepass").toString())) {
                            return "loginChangePass";
                        }
                        //ghi log
                        insertActionLog(getClass().getName(),users.getUsername(), req.getRemoteAddr(), "STARTED A NEW SESSION", users.getUserType());                        
                        VGenLog.info("----Login:" + strUsername + "---- IP:" + req.getRemoteAddr() + "|Login successful!");
                    }
                }
            }
        } catch (Exception ex) {Constant.LogNo(ex);
//            ex.printStackTrace();
            return "input";
        }
//        reqSess.invalidate();
        return "success";
    }

    private boolean isExpiredPassword(Date dtDate) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        Calendar calendar = new GregorianCalendar(year, month, day);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, -90);
//        System.out.println(formatter.format(calendar.getTime()));
        Date dt1 = calendar.getTime();
        if (dtDate.compareTo(dt1) < 0) {
            VGenLog.info("da qua 90 ngay");
            return true;
        } else {
            return false;
        }
    }

    private boolean isExpiredPasswordwarning(Date dtDate) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        Calendar calendar = new GregorianCalendar(year, month, day);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, -90);
//        System.out.println(formatter.format(calendar.getTime()));
        Date dt1 = calendar.getTime();
        if (dtDate.compareTo(dt1) <= 0) {
            VGenLog.info("da den 90 ngay");
            return true;
        } else {
            return false;
        }
    }

    public void insertActionLog2(String module ,String user, String ip, String action, String user_type) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            if (conn == null) {
                return;
            }

            String sql = "{ call " + Constant.SCHEMA_NAME + ".PR_ACTION_LOGS_I(?,?,?,?,?,?)}";
            stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(Constant.QUERY_TIMEOUT);
            int i = 0;
            stmt.setString(++i, module);
            stmt.setString(++i, user);
            stmt.setString(++i, ip);
            stmt.setString(++i, action);
            stmt.setString(++i, "1");
            stmt.setString(++i, user_type);

            stmt.execute();
            if (rs != null) {
                rs.close();
            }
            stmt.close();
            conn.close();
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error(0, "Error: " + ex.getMessage());
        } finally {
            DbCloseConn.closeQuietly("Login", "insertActionLog()", conn, stmt, rs);
        }
    }
    
    public void insertActionLog(String module ,String user, String ip, String action, String user_type) throws SQLException {
       
    }

    /*LOGOUT*/
    public String logout() throws SQLException {
        try {
//            System.out.println("--------Logout----");
            req = getRequest();
            reqSess = req.getSession();
            if (reqSess.getAttribute("login") != null) {
                reqSess.removeAttribute("login");
            }
            reqSess.removeAttribute(Constant.STRING_USERTOKEN);
            if (reqSess != null && !reqSess.isNew()) {//Renew Session Id -> logout
                reqSess.invalidate();
            }
        } catch (Exception ex) {Constant.LogNo(ex);
//            System.out.println(ex.toString());
        }
        return "login";
    }
}

