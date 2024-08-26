package com.viettel.framework.interceptor;

import com.viettel.utils.Constant;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.database.bean.Users;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import java.sql.Connection;
import java.sql.ResultSet;
import com.viettel.util.VGenLog;
import com.viettel.database.utils.DataStore;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.database.utils.DbCloseConn;

public class BaseDAOMDBAction extends ActionSupport {

    HttpServletRequest req;
    HttpSession reqSess;
    ActionInvocation actionInvocation;

    public String initCore() {
        String s1 = "";
        try {
            req = getRequest();
            reqSess = req.getSession();
            Users userid = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            if (reqSess == null || reqSess.isNew() || userid == null) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            } else {
                s1 = "success";
            }
        } catch (Exception ex) {
            Constant.LogNo(ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public BaseDAOMDBAction() {
        sessions = new HashMap();
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public HashMap getSessions() {
        return sessions;
    }

    public void setSessions(HashMap sessions) {
        try {
            this.sessions = sessions;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    private HashMap sessions;
    private Exception exception;

    ////////////////////////////////////////////////////////////////////////////
    public void insertActionLog(String module, String ip, String action) throws SQLException {

    }
    
     public void insertActionLog2(String module, String ip, String action) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        initCore();
        Connection conn = null;
        try {
            conn = DataStore.getConnection();
            if (conn == null) {
                return;
            }
            try {
                String sql = "{ call " + Constant.SCHEMA_NAME + ".PR_ACTION_LOGS_I(?,? ,?,?,?,?)}";
                stmt = conn.prepareStatement(sql);
                stmt.setQueryTimeout(Constant.QUERY_TIMEOUT);
                BaseDAOMDBAction v = new BaseDAOMDBAction();
                int i = 0;
                stmt.setString(++i, module);
                stmt.setString(++i, v.username());
                stmt.setString(++i, ip);
                stmt.setString(++i, action);
                stmt.setString(++i, "1");
                stmt.setString(++i, v.user_type());

                stmt.execute();
                if (rs != null) {
                    rs.close();
                }
                stmt.close();
                conn.close();
            } catch (Exception ex) {
                Constant.LogNo(ex);
                VGenLog.error(0, "Error class BaseDAOMDBAction: " + ex.getMessage());
            }
        } catch (Exception ex) {
            Constant.LogNo(ex);
            VGenLog.error(0, "Error class BaseDAOMDBAction: " + ex.getMessage());
        } finally {
            DbCloseConn.closeQuietly("BaseDAOMDBAction", "insertActionLog()", conn, stmt, rs);
        }

    }

    public String username() {
        HttpServletRequest req = getRequest();

        HttpSession ses = req.getSession();
        Users usersIdBO = (Users) ses.getAttribute(Constant.STRING_USERTOKEN);
        String username_log = "";
        if (usersIdBO != null) {
            username_log = usersIdBO.getUsername();
        }
        return username_log;
    }

    public String user_type() {
        HttpServletRequest req = getRequest();

        HttpSession ses = req.getSession();
        Users usersIdBO = (Users) ses.getAttribute(Constant.STRING_USERTOKEN);
        String UserType_log = "";
        if (usersIdBO != null) {
            UserType_log = usersIdBO.getUserType();
        }
        return UserType_log;
    }

}
