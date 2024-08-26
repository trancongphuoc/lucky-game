package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.vas.security.CheckSelectBox;
import com.viettel.client.form.UserChangePassForm;
import com.viettel.client.form.UserManagerEditForm;
import com.viettel.client.form.UserManagerForm;
import com.viettel.database.dao.UserBO;
import com.viettel.framework.interceptor.BaseDAOMDBAction;
import javax.servlet.http.*;
import com.viettel.database.bean.Users;
import com.viettel.database.utils.DataStore;
import com.viettel.database.utils.DbCloseConn;
import com.viettel.utils.Constant;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.viettel.util.VGenLog;
import com.viettel.utils.utils;

/**
 *
 * @author quyettc
 */
public class UserManagerAction extends BaseDAOMDBAction {

    utils utils = new utils();
    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    UserManagerForm userManagerForm = new UserManagerForm();
    UserManagerEditForm userManagerEditForm = new UserManagerEditForm();
    UserChangePassForm userChangePassForm = new UserChangePassForm();
    CheckSelectBox checkSelectBox = new CheckSelectBox();

    public UserManagerForm getUserManagerForm() {
        return userManagerForm;
    }

    public void setUserManagerForm(UserManagerForm userManagerForm) {
        this.userManagerForm = userManagerForm;
    }

    public UserManagerEditForm getUserManagerEditForm() {
        return userManagerEditForm;
    }

    public void setUserManagerEditForm(UserManagerEditForm userManagerEditForm) {
        this.userManagerEditForm = userManagerEditForm;
    }

    public UserChangePassForm getUserChangePassForm() {
        return userChangePassForm;
    }

    public void setUserChangePassForm(UserChangePassForm userChangePassForm) {
        this.userChangePassForm = userChangePassForm;
    }

    @Override
    public String initCore() {
        String s1 = "";
        try {
            req = getRequest();
            reqSess = req.getSession();
            Users userid = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            int statusLogin = Integer.parseInt(userid.getStatus());
            String userlog = userid.getUsername();
            String UserType = userid.getUserType();
            if (reqSess == null || reqSess.isNew() || userid == null) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            } else if (statusLogin != 1 || userlog.isEmpty() && !UserType.equals(Constant.USER_TYPE_ADMIN)) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            } else {
                s1 = "success";
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error("ERROR: INIT CORE %%% " + ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public String preChangePassUser() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String userName = req.getParameter("username");
            CallableStatement calStat = null;
            ResultSet rs = null;
            Connection conn = null;

            if (!userName.equalsIgnoreCase(Constant.STRING_EMPTY)) {
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                    String sql = "SELECT * FROM " + Constant.SCHEMA_NAME + ".users WHERE username=?";
                    calStat = conn.prepareCall(sql);
                    calStat.setString(1, userName.trim());
                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                    rs = calStat.executeQuery();
                    while (rs.next()) {
                        userChangePassForm.setUsername(rs.getString("username"));
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                        if (calStat != null) {
                            calStat.close();
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    } catch (Exception ex) {Constant.LogNo(ex);
                        VGenLog.error("preChangePassUser|" + ex.toString());
                    }
                } catch (Exception ex) {Constant.LogNo(ex);
                    VGenLog.error(ex.toString());
                } finally {
                    DbCloseConn.closeQuietly("UserManagerAction", "preChangePassUser()", conn, calStat, rs);
                }
            } else {
                userManagerEditForm.setOldusername(userName);
            }
            return "preChangePassUser";
        } else {
            return ERROR;
        }
    }

    public String updateChangePass() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }

        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            Connection conn = null;
            CallableStatement calStat = null;
            ResultSet rs = null;
            try {
                String userName = userChangePassForm.getUsername();
                String newPass = userChangePassForm.getPassword();
                VGenLog.info("username:" + userName + "|newPass:" + newPass);
                if (conn == null || conn.isClosed()) {
                    conn = DataStore.getConnection();
                }
                String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_]).{8,})";
                String strQuery = "select * from " + Constant.SCHEMA_NAME + ".users where username=?";
                calStat = conn.prepareCall(strQuery);
                calStat.setString(1, userName);
                rs = calStat.executeQuery();
                if (rs.next()) {
                    if (!newPass.matches(pattern)) {
                        req.setAttribute("Ret_updateUser", 1);
//                        req.setAttribute("Ret_updateUserMsg", "Thông báo: Mật khẩu không đủ mạnh!");
                        req.setAttribute("Ret_updateUserMsg", "cj.tbmkkdm");
                        req.setAttribute("Ret_updateUserMsg_param", pattern);
                    } else {
                        if (com.viettel.utils.utils.SHA1(newPass).equalsIgnoreCase(rs.getString("password"))) {
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Thông báo: Mật khẩu mới phải khác mật khẩu cũ!");
                            req.setAttribute("Ret_updateUserMsg", "cj.tbmkmpckmkc");
                            req.setAttribute("Ret_updateUserMsg_param", pattern);
                        } else {//                            
                            String strUpdate = "{call " + Constant.SCHEMA_NAME + ".pr_users_u_pd(?,?,sysdate,?)}";
                            calStat = conn.prepareCall(strUpdate);
                            int i = 0;
                            calStat.setString(++i, userName);
                            calStat.setString(++i, utils.SHA1(newPass));
                            calStat.setInt(++i, 1);
                            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                            calStat.execute();
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Thay đổi mật khẩu thành công!");
                            req.setAttribute("Ret_updateUserMsg", "cj.tdmktc");
                            req.setAttribute("Ret_updateUserMsg_param", pattern);
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                                if (calStat != null) {
                                    calStat.close();
                                }
                                if (rs != null) {
                                    rs.close();
                                }
                            } catch (Exception ex) {Constant.LogNo(ex);
                                VGenLog.error("updateChangePass|" + ex.toString());
                            }
                        }
                    }
                }
            } catch (Exception ex) {Constant.LogNo(ex);
                req.setAttribute("Ret_updateUser", 1);
//                req.setAttribute("Ret_updateUserMsg", "Đã xảy ra lỗi khi thay đổi mật khẩu!");
                req.setAttribute("Ret_updateUserMsg", "wj.dclktdmk");
                req.setAttribute("Ret_updateUserMsg_param", ex);
                VGenLog.error("updateChangePass|Error: " + ex.getMessage());
            } finally {
                DbCloseConn.closeQuietly("UserManagerAction", "updateChangePass()", conn, calStat, rs);
            }
            searchUser();
            return "UserManagerList";
        } else {
            return ERROR;
        }
    }

    public String prepareUserManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        String username = userToken.getUsername();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN) && !username.isEmpty()) {
//            searchUser();
//            req.setAttribute("token",req.getParameter("token"));
            return "prepareUserManager";
        } else {
            return ERROR;
        }

    }

    public String preEditUser() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String userName = req.getParameter("username");
            CallableStatement calStat = null;
            ResultSet rs = null;
            Connection conn = null;

            if (!userName.equalsIgnoreCase(Constant.STRING_EMPTY)) {
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                    String sqlUserEdit = "SELECT * FROM " + Constant.SCHEMA_NAME + ".users WHERE username=?";
                    calStat = conn.prepareCall(sqlUserEdit);
                    calStat.setString(1, userName);
                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                    rs = calStat.executeQuery();
                    while (rs.next()) {
                        userManagerEditForm.setUsername(rs.getString("username"));
                        userManagerEditForm.setOldusername(rs.getString("username"));
                        userManagerEditForm.setPassword(rs.getString("password"));
                        userManagerEditForm.setDescriptions(rs.getString("descriptions"));
                        userManagerEditForm.setStatus(rs.getString("status"));
                        userManagerEditForm.setUser_type(rs.getString("user_type"));
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                        if (calStat != null) {
                            calStat.close();
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    } catch (Exception ex) {Constant.LogNo(ex);
                        VGenLog.error("preEditUser|" + ex.toString());
                    }
                } catch (Exception ex) {Constant.LogNo(ex);
                    VGenLog.error(ex.toString());
                } finally {
                    DbCloseConn.closeQuietly("UserManagerAction", "preEditUser()", conn, calStat, rs);
                }
            } else {
                userManagerEditForm.setOldusername(userName);
            }
            return "UserManagerDlg";
        } else {
            return ERROR;
        }
    }

    public String preAddUser() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {

            return "UserManagerDlg";
        } else {
            return ERROR;
        }
    }

    public String searchUser() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String userName = userManagerForm.getUserName();
            String status = userManagerForm.getStatus();
            Connection conn = null;
            CallableStatement calStat = null;
            ResultSet rs = null;

            try {
                if (conn == null || conn.isClosed()) {
                    conn = DataStore.getConnection();
                }
                String sqlq = "";
                if ("-1".equals(status)) {
                    if ((userName != null) && (!userName.equalsIgnoreCase(Constant.STRING_EMPTY))) {
                        sqlq = "select * from " + Constant.SCHEMA_NAME + ".users "
                                + "where users.username like ? order by username";
                        calStat = conn.prepareCall(sqlq);
                        calStat.setString(1, "%" + userName.trim() + "%");
                    } else {
                        sqlq = "select * from " + Constant.SCHEMA_NAME + ".users order by username";
                        calStat = conn.prepareCall(sqlq);
                    }
                } else if (status == null) {
                    if ((userName != null) && (!userName.equalsIgnoreCase(Constant.STRING_EMPTY))) {
                        sqlq = "select * from " + Constant.SCHEMA_NAME + ".users "
                                + "where users.username like ? order by username";
                        calStat = conn.prepareCall(sqlq);
                        calStat.setString(1, "%" + userName.trim() + "%");

                    } else {
                        sqlq = "select * from " + Constant.SCHEMA_NAME + ".users order by username";
                        calStat = conn.prepareCall(sqlq);
                    }
                } else {
                    if ((userName != null) && (!userName.equalsIgnoreCase(Constant.STRING_EMPTY))) {
                        sqlq = "select * from " + Constant.SCHEMA_NAME + ".users "
                                + "where users.username like ? and users.status=? order by username";
                        calStat = conn.prepareCall(sqlq);
                        calStat.setString(1, "%" + userName.trim() + "%");
                        calStat.setString(2, status);

                    } else {
                        sqlq = "select * from " + Constant.SCHEMA_NAME + ".users "
                                + "where users.status=? order by username";
                        calStat = conn.prepareCall(sqlq);
                        calStat.setString(1, status);
                    }
                }
                rs = calStat.executeQuery();
                List<UserBO> lst = new ArrayList<UserBO>();
                while (rs.next()) {
                    lst.add(new UserBO(rs.getString("username"), rs.getString("password"),
                            rs.getString("status"), rs.getString("descriptions"), rs.getString("user_type")));
                }
                req.setAttribute("userList", lst);
                reqSess.setAttribute("userList", lst);
                insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List User");
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (calStat != null) {
                        calStat.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception ex) {Constant.LogNo(ex);
                    VGenLog.error("searchUser|" + ex.toString());
                }
            } catch (Exception ex) {Constant.LogNo(ex);
                VGenLog.error(ex);
            } finally {
                DbCloseConn.closeQuietly("UserManagerAction", "searchUser()", conn, calStat, rs);
            }
            return "UserManagerList";
        } else {
            return ERROR;
        }
    }

    public String pagingUserManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            req.setAttribute("userList", reqSess.getAttribute("userList"));
//            searchUser();
            return "UserManagerList";
        } else {
            return ERROR;
        }
    }

    public String updateInsertUser() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String oldUserName = userManagerEditForm.getOldusername();
            Connection conn = null;
            CallableStatement calStat = null;
            ResultSet rs = null;
            if ("".equals(oldUserName.toString())) { //new
                String userName = userManagerEditForm.getUsername();
                String password = userManagerEditForm.getPassword();
                String descriptions = userManagerEditForm.getDescriptions();
                String user_type1 = userManagerEditForm.getUser_type();
                String status = userManagerEditForm.getStatus();
                if (!userName.isEmpty()) {
                    try {
                        // bo sung chekc status xem dung ko
                        boolean checkStatus = checkSelectBox.checkStatus(Integer.valueOf(status));
                        boolean checkAuthor = checkSelectBox.checkUserType(Integer.valueOf(user_type1));
                        String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_]).{8,})";
                        if (!checkStatus) {
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Lỗi: Tên đăng nhập " + userName + " đã tồn tại trong CSDL!");
                            req.setAttribute("Ret_updateUserMsg", "c.sinv");
                            req.setAttribute("Ret_updateUserMsg_param", status);
                        } else if (!checkAuthor) {
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Lỗi: Tên đăng nhập " + userName + " đã tồn tại trong CSDL!");
                            req.setAttribute("Ret_updateUserMsg", "c.usnvl");
                            req.setAttribute("Ret_updateUserMsg_param", user_type1);
                        } else if (checkUser(userName)) {//Check xem ton tai username chua?
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Lỗi: Tên đăng nhập " + userName + " đã tồn tại trong CSDL!");
                            req.setAttribute("Ret_updateUserMsg", "cj.ltdndtt");
                            req.setAttribute("Ret_updateUserMsg_param", userName);
                        } else if (!password.matches(pattern)) {
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Thông báo: Mật khẩu không đủ mạnh!");
                            req.setAttribute("Ret_updateUserMsg", "cj.tbmkkdm");
                            req.setAttribute("Ret_updateUserMsg_param", password);
                        } else {
                            if (conn == null || conn.isClosed()) {
                                conn = DataStore.getConnection();
                            }
                            if (conn == null) {
                                return null;
                            }
                            String strsql = "{call " + Constant.SCHEMA_NAME + ".pr_users_i(?,?,?,?,?)}";
                            calStat = conn.prepareCall(strsql);
                            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                            int i = 0;
                            calStat.setString(++i, userName);
                            calStat.setString(++i, utils.SHA1(password));
                            calStat.setString(++i, status);
                            calStat.setString(++i, descriptions);
                            calStat.setString(++i, user_type1);
                            calStat.execute();
                            req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Thêm mới người dùng thành công!");
                            req.setAttribute("Ret_updateUserMsg", "cj.tmndtc");
                            req.setAttribute("Ret_updateUserMsg_param", userName);
                            insertActionLog(getClass().getName(), req.getLocalAddr(), "Insert user: " + userName);
                            try {
                                if (rs != null) {
                                    rs.close();
                                }
                                if (calStat != null) {
                                    calStat.close();
                                }
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (Exception ex) {Constant.LogNo(ex);
                                VGenLog.error("updateInsertUser|" + ex.toString());
                            }
                        }
                    } catch (Exception ex) {Constant.LogNo(ex);
                        req.setAttribute("Ret_updateUser", 1);
//                        req.setAttribute("Ret_updateUserMsg", "Có lỗi xảy ra trong quá trình thêm người dùng!");
                        req.setAttribute("Ret_updateUserMsg", "cj.clxrtqttnd");
                        req.setAttribute("Ret_updateUserMsg_param", userName);
                        VGenLog.error(ex.toString());
                    } finally {
                        DbCloseConn.closeQuietly("UserManagerAction", "updateInsertUser()", conn, calStat, rs);
                    }
                } else {
                    req.setAttribute("Ret_updateUser", 1);
//                    req.setAttribute("Ret_updateUserMsg", "Có lỗi xảy ra trong quá trình cập nhật người dùng! Không được để trống");
                    req.setAttribute("Ret_updateUserMsg", "cj.clxrtqtcnndkddt");
                    req.setAttribute("Ret_updateUserMsg_param", userName);
                }
            } else { //update
                String userName = userManagerEditForm.getUsername();
                String descriptions = userManagerEditForm.getDescriptions();
                String user_type1 = userManagerEditForm.getUser_type();
                String status = userManagerEditForm.getStatus();
                boolean checkStatus = checkSelectBox.checkStatus(Integer.valueOf(status));
                boolean checkAuthor = checkSelectBox.checkUserType(Integer.valueOf(user_type1));
                if (!checkStatus) {
                    req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Lỗi: Tên đăng nhập " + userName + " đã tồn tại trong CSDL!");
                    req.setAttribute("Ret_updateUserMsg", "c.sinv");
                    req.setAttribute("Ret_updateUserMsg_param", status);
                } else if (!checkAuthor) {
                    req.setAttribute("Ret_updateUser", 1);
//                            req.setAttribute("Ret_updateUserMsg", "Lỗi: Tên đăng nhập " + userName + " đã tồn tại trong CSDL!");
                    req.setAttribute("Ret_updateUserMsg", "c.usnvl");
                    req.setAttribute("Ret_updateUserMsg_param", user_type1);
                } else if (!userName.isEmpty()) {
                    try {
                        if (conn == null || conn.isClosed()) {
                            conn = DataStore.getConnection();
                        }
                        if (conn == null) {
                            return null;
                        }
                        calStat = conn.prepareCall("{call " + Constant.SCHEMA_NAME + ".pr_users_u(?,?,?,?)}");
                        calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                        calStat.setString(1, userName);
                        calStat.setString(2, status);
                        calStat.setString(3, descriptions);
                        calStat.setString(4, user_type1);
                        calStat.executeQuery();
                        req.setAttribute("Ret_updateUser", 1);
//                        req.setAttribute("Ret_updateUserMsg", "Cập nhật người dùng thành công!");
                        req.setAttribute("Ret_updateUserMsg", "cj.cnndtc");
                        req.setAttribute("Ret_updateUserMsg_param", userName);
                        insertActionLog(getClass().getName(), req.getLocalAddr(), "Update user: " + userName);
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (calStat != null) {
                                calStat.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (Exception ex) {Constant.LogNo(ex);
                            VGenLog.error("updateInsertUser|" + ex.toString());
                        }
                    } catch (Exception ex) {Constant.LogNo(ex);
                        req.setAttribute("Ret_updateUser", 1);
//                        req.setAttribute("Ret_updateUserMsg", "Có lỗi xảy ra trong quá trình cập nhật người dùng!");
                        req.setAttribute("Ret_updateUserMsg", "cj.clxrtqtcnnd");
                        req.setAttribute("Ret_updateUserMsg_param", userName);
                        VGenLog.error(ex.toString());
                    } finally {
                        DbCloseConn.closeQuietly("UserManagerAction", "updateInsertUser()", conn, calStat, rs);
                    }
                } else {
                    req.setAttribute("Ret_updateUser", 1);
//                    req.setAttribute("Ret_updateUserMsg", "Có lỗi xảy ra trong quá trình cập nhật người dùng! Không được để trống");
                    req.setAttribute("Ret_updateUserMsg", "cj.clxrtqtcnndkddt");
                    req.setAttribute("Ret_updateUserMsg_param", userName);
                }
            }
            //Load list
            searchUser();
            return "UserManagerList";
        } else {
            return ERROR;
        }
    }

    public String deleteUser() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String username = req.getParameter("username");
            ResultSet rs = null;
            Connection conn = null;
            CallableStatement calStat = null;

            if (!"".equals(username)) { //update
                VGenLog.info("deleteUser|username:" + username);
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                    calStat = conn.prepareCall("select * from " + Constant.SCHEMA_NAME + ".users where username=? ");
                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                    calStat.setString(1, username);
                    rs = calStat.executeQuery();

                    if (rs.next()) {
                        calStat = conn.prepareCall("{call " + Constant.SCHEMA_NAME + ". pr_users_d(?)}");
                        calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                        calStat.setString(1, username);
                        calStat.execute();
                        req.setAttribute("Ret_updateUser", 1);
//                        req.setAttribute("Ret_updateUserMsg", "Xóa người dùng thành công!");
                        req.setAttribute("Ret_updateUserMsg", "login.xndtc");
                        insertActionLog(getClass().getName(), req.getRemoteAddr(), "Delete User: " + username);
                    }
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (calStat != null) {
                            calStat.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (Exception ex) {Constant.LogNo(ex);
                        VGenLog.error("deleteUser|" + ex.toString());
                    }
                } catch (Exception ex) {Constant.LogNo(ex);
                    req.setAttribute("Ret_updateUser", 1);
//                    req.setAttribute("Ret_updateUserMsg", "Có lỗi xảy ra trong quá trình xóa người dùng!");
                    req.setAttribute("Ret_updateUserMsg", "cj.clxrtqtxnd");
                    req.setAttribute("Ret_updateUserMsg_param", username);
                    VGenLog.error(ex.toString());
                } finally {
                    DbCloseConn.closeQuietly("UserManagerAction", "deleteUser()", conn, calStat, rs);
                }
            } else { //new
                req.setAttribute("Ret_updateUser", 1);
//                req.setAttribute("Ret_updateUserMsg", "Có lỗi xảy ra trong quá trình xóa người dùng!");
                req.setAttribute("Ret_updateUserMsg", "cj.clxrtqtxnd");
                req.setAttribute("Ret_updateUserMsg_param", username);
            }
            //Load list
            searchUser();
            return "UserManagerList";
        } else {
            return ERROR;
        }
    }

    public Boolean checkUser(String username) throws SQLException {
        Connection conn = null;
        List<Users> result = new ArrayList<Users>();
        CallableStatement calStat = null;
        ResultSet rs = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            if (conn == null) {
                return null;
            }
            ArrayList colParams = new ArrayList();
            String sql = "select username, password, status, descriptions,user_type FROM " + Constant.SCHEMA_NAME + ".users ";
            sql += " WHERE 1=1 ";
            if (username.trim().length() > 0) {
                sql += "AND USERNAME = ?";//'" + username.toUpperCase() +"'";
                colParams.add(username);
            }
            calStat = conn.prepareCall(sql);
            calStat.clearParameters();
            if (colParams != null && colParams.size() > 0) {
                for (int i = 0; i < colParams.size(); i++) {
                    calStat.setString(i + 1, colParams.get(i).toString());
                }
            }
            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);

            rs = calStat.executeQuery();

            while (rs.next()) {
                result.add(new Users(rs.getString("username"), rs.getString("password"), rs.getString("STATUS"), rs.getString("descriptions"), rs.getString("user_type")));
            }
            try {
                if (rs != null) {
                    rs.close();
                }
                if (calStat != null) {
                    calStat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {Constant.LogNo(ex);
                VGenLog.error("checkUser|" + ex.toString());
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error("Error: " + ex.getMessage());
        } finally {
            DbCloseConn.closeQuietly("UserManagerAction", "checkUser()", conn, calStat, rs);
        }
        if (result != null && result.size() > 0) {
            return true;
        }
        return false;
    }
}
