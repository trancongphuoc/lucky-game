/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.SearchLogUssdKpiForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.MissedCall;
import com.viettel.database.utils.DBUtil;
import com.viettel.database.utils.DataStore;
import com.viettel.framework.interceptor.BaseDAOMDBAction;
import com.viettel.util.VGenLog;
import com.viettel.utils.Constant;
import com.viettel.utils.DateTimeUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Trieunv
 */
public class SearchLogMissedCall extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    private static final String SEARCH_LOG_USSD_KPI = "SearchLogUssdKpi";
    private static final String SEARCH_LOG_USSD_KPI_LIST = "SearchLogUssdKpiList";
    private static final String SEARCH_LogUssdKpiList = "LogUssdKpiList";
    private static final String SEARCH_SearchLog = "SearchLog";
    private static final String SEARCH_SearchLogMsg = "SearchLogMsg" ;
    
    SearchLogUssdKpiForm searchLogUssdKpiForm = new SearchLogUssdKpiForm();

    public SearchLogUssdKpiForm getSearchLogUssdKpiForm() {
        return searchLogUssdKpiForm;
    }

    public void setSearchLogUssdKpiForm(SearchLogUssdKpiForm searchLogUssdKpiForm) {
        this.searchLogUssdKpiForm = searchLogUssdKpiForm;
    }

    @Override
    public String initCore() {
        String s1;
        try {
            req = getRequest();
            reqSess = req.getSession();
            Users userId = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            int statusLogin = Integer.parseInt(userId.getStatus());
            String userlog = userId.getUsername();
            String UserType = userId.getUserType();
             if ((reqSess == null || reqSess.isNew()) 
                    || (statusLogin != 1 || userlog.isEmpty() && !UserType.equals(Constant.USER_TYPE_ADMIN))) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            } else {
                s1 = "success";
            }
        } catch (NumberFormatException ex) {
            VGenLog.error("ERROR: INIT CORE %%% " + ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public String prepareSearch() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            searchLogUssdKpiForm.setFromDate(new Date());
            searchLogUssdKpiForm.setToDate(new Date());
            searchLogUssdKpiForm.setMsisdn("");
            searchLogUssdKpi();
            return SEARCH_LOG_USSD_KPI;
        } else {
            return ERROR;
        }
    }

    public String searchLogUssdKpi() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        String fromDate;
        String toDate;
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
        List<MissedCall> lst = new ArrayList();

        if (checkValidateSearch()) {
            String msisdn = searchLogUssdKpiForm.getMsisdn().trim();
            fromDate = DateTimeUtils.convertDateTimeToString(searchLogUssdKpiForm.getFromDate());
            toDate = DateTimeUtils.convertDateTimeToString(searchLogUssdKpiForm.getToDate());
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn = DataStore.getConnection();
                ArrayList colParams = new ArrayList();
                String sqlq = "select * from CALL_LOG where  "
                        + "  CREATED >= to_date(? ,'dd/mm/yyyy') "
                        + " and  CREATED < to_date(? ,'dd/mm/yyyy') + 1 ";
                colParams.add(fromDate);
                colParams.add(toDate);
                if (!msisdn.isEmpty()) {
                    sqlq += " and (CALLING like ? or";
                    sqlq += "  CALLED like ? )";
                    colParams.add("%" + msisdn.trim() + "%");
                    colParams.add("%" + msisdn.trim() + "%");
                } else {
                    sqlq += " and rownum <= 100 ";
                }

                sqlq += " order by CREATED desc ";

                stmt = conn.prepareStatement(sqlq);
                for (int i = 0; i < colParams.size(); i++) {
                    stmt.setString(i + 1, colParams.get(i).toString());
                }
                rs = stmt.executeQuery();
                while (rs.next()) {
//                        String transId, String calling, String called, Timestamp requestTime, Timestamp created, String errorCode
                    MissedCall ukb = new MissedCall(rs.getString("TRANS_ID"), rs.getString("CALLING"), rs.getString("CALLED"),
                            rs.getTimestamp("REQUEST_TIME"), rs.getTimestamp("CREATED"),
                            rs.getString("ERROR_CODE"));
                    lst.add(ukb);
                }
                insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List LogUssdKpi");
            } catch (SQLException ex) {
                VGenLog.error(ex);
            } finally {
                DBUtil.closeObject(rs);
                DBUtil.closeObject(stmt);
                DBUtil.closeObject(conn);
            }
        }
        req.setAttribute(SEARCH_LogUssdKpiList, lst);
        reqSess.setAttribute(SEARCH_LogUssdKpiList, lst);
        return SEARCH_LOG_USSD_KPI_LIST;
//        return SEARCH_LOG_USSD_KPI;
    }

    public String pagingSearchLogUssdKpi() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            req.setAttribute(SEARCH_LogUssdKpiList, reqSess.getAttribute(SEARCH_LogUssdKpiList));
            return SEARCH_LOG_USSD_KPI_LIST;
        } else {
            return ERROR;
        }
    }

    public boolean checkValidateSearch() {
        try {
            if (searchLogUssdKpiForm.getFromDate() == null) {
                req.setAttribute(SEARCH_SearchLog, 1);
                req.setAttribute(SEARCH_SearchLogMsg, "sl.fail.fromDate");
                return false;
            }
            if (searchLogUssdKpiForm.getToDate() == null) {
                req.setAttribute(SEARCH_SearchLog, 1);
                req.setAttribute(SEARCH_SearchLogMsg, "sl.fail.toDate");
                return false;
            }
            if (searchLogUssdKpiForm.getFromDate().after(searchLogUssdKpiForm.getToDate())) {
                req.setAttribute(SEARCH_SearchLog, 1);
                req.setAttribute(SEARCH_SearchLogMsg, "sl.fail.fromDateSmallerToDate");
                return false;
            }
        } catch (Exception e) {Constant.LogNo(e);
            req.setAttribute(SEARCH_SearchLog, 1);
            req.setAttribute(SEARCH_SearchLogMsg, "sl.fail.search");
            e.getMessage();
            return false;
        }
        return true;
    }
}
