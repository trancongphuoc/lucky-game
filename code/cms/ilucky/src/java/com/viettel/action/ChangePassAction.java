/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.ChangePassForm;
import com.viettel.framework.interceptor.BaseDAOMDBAction;

import javax.servlet.http.*;
import com.viettel.database.bean.Users;
import com.viettel.database.utils.DataStore;
import com.viettel.database.utils.DbCloseConn;
import com.viettel.utils.Constant;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.viettel.util.VGenLog;
import com.viettel.utils.utils;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ChangePassAction extends BaseDAOMDBAction {

    utils utils = new utils();
    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    ChangePassForm changePassForm = new ChangePassForm();

    public ChangePassForm getChangePassForm() {
        return changePassForm;
    }

    public void setChangePassForm(ChangePassForm changePassForm) {
        this.changePassForm = changePassForm;
    }

    public String initCore() {
        String s1 = "";
        try {
            req = getRequest();
            reqSess = req.getSession();
            Users userid = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            String userlog = userid.getUsername();
            String UserType = userid.getUserType();
            if (reqSess == null || reqSess.isNew() || userid == null) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            } else if (userlog.isEmpty() && !UserType.equals(Constant.USER_TYPE_ADMIN)) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error("ERROR: INIT CORE %%% " + ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public String prepareChangePass() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String UserType = userToken.getUserType();
        if (UserType.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            req.setAttribute("warning", " ");
            return "prepareChangePass";
        } else {
            return ERROR;
        }

    }

    public String changePassword() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }

        String oldPass = changePassForm.getOldPass();
        String newPass = changePassForm.getNewPass();

        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        Users userLogin = null;

        String UserType = userToken.getUserType();
        if (!UserType.equalsIgnoreCase(Constant.STRING_EMPTY)) {
            if (userToken != null) {
                Connection myConnect = null;
                ResultSet rs = null;
                PreparedStatement stmt = null;
                try {
                    ArrayList colParams = new ArrayList();
                    String sql = "SELECT * FROM " + Constant.SCHEMA_NAME + ".users where username=? and password=? ";
                    if (myConnect == null || myConnect.isClosed()) {
                        myConnect = DataStore.getConnection();
                    }
                    if (myConnect == null) {
                        req.setAttribute("warning", "wj.ktldkn");
                        return "ChangePassMsg";
                    }
                    stmt = myConnect.prepareStatement(sql);
                    stmt = myConnect.prepareCall(sql);
                    colParams.add(userToken.getUsername());
                    colParams.add(utils.SHA1(oldPass));
                    if (colParams != null && colParams.size() > 0) {
                        for (int i = 0; i < colParams.size(); i++) {
                            stmt.setString(i + 1, colParams.get(i).toString());
                        }
                    }
                    stmt.setQueryTimeout(Constant.QUERY_TIMEOUT);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        userLogin = new Users();
                        userLogin.setStatus(rs.getString("status"));
                        String strUsername = rs.getString("username");
                        userLogin.setUsername(strUsername);
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (userLogin != null) { // Tim thay user
                        String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_]).{8,})";
                        if (newPass.matches(pattern)) {
                            String sql1 = "{ call " + Constant.SCHEMA_NAME + ".pr_users_u_pd(?,?,sysdate,?)}";//test user1
                            stmt = myConnect.prepareStatement(sql1);
                            stmt.setQueryTimeout(Constant.QUERY_TIMEOUT);
                            int i = 0;
                            stmt.setString(++i, userToken.getUsername());
                            stmt.setString(++i, utils.SHA1(newPass));
                            stmt.setInt(++i, 1);
                            try {
                                stmt.execute();
                                //change password success
                                reqSess.setAttribute("warning", "");
                                stmt.close();
                                insertActionLog(getClass().getName(), req.getRemoteAddr(), "Change User: " + userToken.getUsername());
                                if (stmt != null) {
                                    stmt.close();
                                }
                            } catch (Exception ex) {Constant.LogNo(ex);
                                //change password success
                                reqSess.setAttribute("warning", "");
                            }
                        } else {
                            reqSess.setAttribute("warning", "wj.mkkdm");
                        }
                    } else {
                        reqSess.setAttribute("warning", "wj.mkhtkd");
                    }
                    try {
                        if (myConnect != null) {
                            myConnect.close();
                        }
                    } catch (Exception ex) {Constant.LogNo(ex);
                    }
                    return "ChangePassMsg";
                } catch (Exception ex) {Constant.LogNo(ex);
                    reqSess.setAttribute("warning", "wj.kttlknd");
                    VGenLog.error("changePassword-ex:" + ex.toString());
                    return "ChangePassMsg";
                } finally {
                    DbCloseConn.closeQuietly("ChangePassAction", "changePassword()", myConnect, stmt, rs);
                }
            } else {
                reqSess.setAttribute("warning", "wj.kttu");
                return "ChangePassMsg";
            }
        } else {
            return ERROR;
        }
    }
}
