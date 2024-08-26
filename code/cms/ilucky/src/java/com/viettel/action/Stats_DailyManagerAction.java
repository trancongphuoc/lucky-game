/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.Stats_DailyManagerForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.Stats_OnDay;
import com.viettel.database.utils.DataStore;
import com.viettel.database.utils.DbCloseConn;
import com.viettel.framework.interceptor.BaseDAOMDBAction;
import com.viettel.util.VGenLog;
import com.viettel.utils.Constant;
import com.viettel.utils.DateTimeUtils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hanhnv62
 */
public class Stats_DailyManagerAction extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    Stats_DailyManagerForm Stats_DailyManagerForm = new Stats_DailyManagerForm();
    private NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMAN);

    public Stats_DailyManagerForm getStats_DailyManagerForm() {
        return Stats_DailyManagerForm;
    }

    public void setStats_DailyManagerForm(Stats_DailyManagerForm Stats_DailyManagerForm) {
        this.Stats_DailyManagerForm = Stats_DailyManagerForm;
    }

    @Override
    public String initCore() {
        String s1 = "success";
        try {
            req = getRequest();
            reqSess = req.getSession();
            Users userid = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            int statusLogin = Integer.parseInt(userid.getStatus());
            String userlog = userid.getUsername();
            String UserType = userid.getUserType();
            if (reqSess == null || reqSess.isNew() 
                    || (statusLogin != 1 || userlog.isEmpty() || UserType.equals(Constant.USER_TYPE_CP))) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            }
        } catch (Exception ex) {
            VGenLog.error("ERROR: INIT CORE %%% ",ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public String prepareStats_DailyManager() throws SQLException {
        if (Constant.STRING_SESSIONTIMEOUT.equals(initCore())) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        String username = userToken.getUsername();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP) && !username.isEmpty()) {
            return "prepareStats_DailyManager";
        } else {
            return ERROR;
        }
    }

    public String searchStats_DailyManager(Date fromDate, Date todate) throws SQLException, Exception {
        if (Constant.STRING_SESSIONTIMEOUT.equals(initCore())) {
            return Constant.STRING_SESSIONTIMEOUT;
        }

//        PreparedStatement stmt = null;
        ResultSet rs = null;
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
//        String user_name = userToken.getUsername();
        Connection conn = null;
        CallableStatement calStat = null;
        if (Constant.USER_TYPE_CP.equalsIgnoreCase(user_type)) {
            return ERROR;
        }

        try {
            conn = DataStore.getConnection();
            String strFromDate = DateTimeUtils.convertDateTimeToString2(fromDate);
            String strtoDate = DateTimeUtils.convertDateTimeToString2(todate);

            List<Stats_OnDay> Stats_DailyList = getList(strFromDate, strtoDate);
//             
            req.setAttribute("Stats_DailyList", Stats_DailyList);
            reqSess.setAttribute("Stats_DailyList", Stats_DailyList);
            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List Stats_Daily");
        } catch (Exception ex) {
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("Stats_DailyManagerAction", "searchStats_DailyManager()", conn, calStat, rs);
        }
        return "Stats_DailyManagerList";
    }
    
    public static List<Stats_OnDay> getList(String fromDate, String todate) {
        ResultSet rs = null;
        Connection conn = null;
        CallableStatement calStat = null;
        List<Stats_OnDay> Stats_DailyList = null;
        
        try {
            conn = DataStore.getConnection();
            String sql_his = "Select * from " + Constant.SCHEMA_NAME + ".V_SUMMERY_REPORT where "
                    + " REPORT_DAY >= TO_DATE(?,'yyyy-MM-dd')  "
                    + " and REPORT_DAY <= TO_DATE(?,'yyyy-MM-dd') + 1  "
                    ;
            calStat = conn.prepareCall(sql_his);
            calStat.setString(1, fromDate);
            calStat.setString(2, todate);
            calStat.setQueryTimeout(30);
            rs = calStat.executeQuery();
            Stats_DailyList = new ArrayList<Stats_OnDay>();
            
            while (rs.next()) {
                String id                    = rs.getString("ID") ;
                String reportDay             = rs.getString("REPORT_DAY") ;
                String register             = rs.getString("REGISTER") ;
                String cancel      = rs.getString("CANCEL") ;
                String renew         = rs.getString("RENEW") ;
                String revenue                 = rs.getString("MONEY") ;
                String totalSub                = rs.getString("TOTALSUBS") ;
                String activeSub          = rs.getString("SUB_ACTIVE") ;
                String pendingSub          = rs.getString("SUB_PENDING") ;
                String cancelSub          = rs.getString("CANCELSUBS") ;
                String cost          = rs.getString("COST") ;
                String profit          = rs.getString("PROFIT") ;
                String moneyRegister          = rs.getString("MONEY_REGISTER") ;
                String moneyBy          = rs.getString("MONEY_BUY") ;
                String moneyRenew          = rs.getString("MONEY_RENEW") ;
                String numPlay          = rs.getString("NUM_PLAY") ;
                String numPlayer          = rs.getString("NUM_PLAYER") ;
                
                Stats_OnDay s = new Stats_OnDay(id, reportDay, register, cancel, renew, revenue, activeSub, pendingSub, cancelSub,
                        cost, profit, moneyRegister, moneyBy, moneyRenew, totalSub);
                s.setNumPlay(numPlay);
                s.setNumPlayer(numPlayer);
                
                Stats_DailyList.add(s);
            }
        } catch (Exception ex) {
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("Stats_DailyManagerAction", "searchStats_DailyManager()", conn, calStat, rs);
        }
        
        return Stats_DailyList;
    }


    public String getStats_Daily() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }

        Date fromDate = Stats_DailyManagerForm.getDate();
        Date toDate = Stats_DailyManagerForm.getToDate();
        searchStats_DailyManager(fromDate, toDate);

        return "Stats_DailyManagerList";
    }

    public String pagingStats_Daily() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP)) {
            req.setAttribute("Stats_DailyList", reqSess.getAttribute("Stats_DailyList"));
            return "Stats_DailyManagerList";
        } else {
            return ERROR;
        }
    }

    
}
