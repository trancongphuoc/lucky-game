/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.SearchCpKpiSoapForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.CpKpiSoapBO;
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
public class SearchCpKpiSoapAction extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    private static final String SEARCH_CP_KPI_SOAP = "SearchCpKpiSoap";
    private static final String SEARCH_CP_KPI_SOAP_LIST = "SearchCpKpiSoapList";
    private static final String CP_KPI_SOAP_LIST = "CpKpiSoapList" ;
    private static final String SEARCH_CP = "SearchCp" ;
    private static final String Search_CpKpiSoapMsg = "SearchCpKpiSoapMsg" ;
    
    SearchCpKpiSoapForm searchCpKpiSoapForm = new SearchCpKpiSoapForm();

    public SearchCpKpiSoapForm getSearchCpKpiSoapForm() {
        return searchCpKpiSoapForm;
    }

    public void setSearchCpKpiSoapForm(SearchCpKpiSoapForm searchCpKpiSoapForm) {
        this.searchCpKpiSoapForm = searchCpKpiSoapForm;
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

    public String prepareSearchCpKpiSoap() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();

        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            searchCpKpiSoapForm.setFromDate(new Date());
            searchCpKpiSoapForm.setToDate(new Date());
            searchCpKpiSoapForm.setMsisdn("");
            searchCpKpiSoap();
            return SEARCH_CP_KPI_SOAP;
        } else {
            return ERROR;
        }
    }

    public String searchCpKpiSoap() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        String fromDate;
        String toDate;
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equals(Constant.USER_TYPE_ADMIN)) {
            List<CpKpiSoapBO> lst = new ArrayList();
            if (checkValidateSearch()) {
                String msisdn = searchCpKpiSoapForm.getMsisdn().trim();
                fromDate = DateTimeUtils.convertDateTimeToString(searchCpKpiSoapForm.getFromDate());
                toDate = DateTimeUtils.convertDateTimeToString(searchCpKpiSoapForm.getToDate());
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                    ArrayList colParams = new ArrayList();
                    String sqlq = "select * from KPI_CP_SOAP where  "
                            + "  CREATED >= to_date(? ,'dd/mm/yyyy') "
                            + " and  CREATED < to_date(? ,'dd/mm/yyyy') + 1 ";
                    colParams.add(fromDate);
                    colParams.add(toDate);
                    if (!msisdn.isEmpty()) {
                        sqlq += " and Upper(KPI_CP_SOAP.msisdn) like ? ";
                        colParams.add("%" + msisdn.toUpperCase() + "%");
                    } else {
                        sqlq += " and KPI_CP_SOAP.msisdn = '' "; }
                    sqlq += " order by id asc ";

                    stmt = conn.prepareStatement(sqlq);
                    for (int i = 0; i < colParams.size(); i++) {
                        stmt.setString(i + 1, colParams.get(i).toString());
                    }
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        CpKpiSoapBO ukb = new CpKpiSoapBO(rs.getString("SUB_SERVICE_NAME"), rs.getString("PROVIDER_NAME"), rs.getString("CHANNEL"),
                                rs.getString("TRANS_ID"),
                                rs.getString("MSISDN"), rs.getString("ACTION"),
                                rs.getLong("DURATION"), rs.getString("URL"),
                                rs.getString("BODY"),
                                rs.getString("RESPONSE"), rs.getDate("REQUEST_TIME"),
                                rs.getString("DESCRIPTION"), rs.getString("ERROR_CODE"));
                        if (ukb.getResponse() != null) {
                            char[] array = ukb.getResponse().toCharArray();
                            if (array.length > 2000) {
                                String str = "";
                                int i;
                                for (i = 0; i < 2000; i++) {
                                    str += array[i];
                                }
                                ukb.setResponse(str); }}
                        lst.add(ukb);
                    }
                    insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List CpKpiSoapList");
                } catch (SQLException ex)  {Constant.LogNo(ex);
                    VGenLog.error(ex);
                } finally {
                    DBUtil.closeObject(rs);
                    DBUtil.closeObject(stmt);
                    DBUtil.closeObject(conn);
                }
            }
            req.setAttribute(CP_KPI_SOAP_LIST, lst);
            reqSess.setAttribute(CP_KPI_SOAP_LIST, lst);
            return SEARCH_CP_KPI_SOAP_LIST;
        }
        return ERROR;
    }

    public String pagingSearchCpKpiSoap() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            req.setAttribute(CP_KPI_SOAP_LIST, reqSess.getAttribute(CP_KPI_SOAP_LIST));
            return SEARCH_CP_KPI_SOAP_LIST;
        } else {
            return ERROR;
        }
    }

    public boolean checkValidateSearch() {
        try {
            if (searchCpKpiSoapForm.getMsisdn() == null) {
                req.setAttribute(SEARCH_CP, 1);
                req.setAttribute(Search_CpKpiSoapMsg, "sl.fail.msisdnne");
                return false;
            }
            if (searchCpKpiSoapForm.getFromDate() == null) {
                req.setAttribute(SEARCH_CP, 1);
                req.setAttribute(Search_CpKpiSoapMsg, "sl.fail.fromDate");
                return false;
            }
            if (searchCpKpiSoapForm.getToDate() == null) {
                req.setAttribute(SEARCH_CP, 1);
                req.setAttribute(Search_CpKpiSoapMsg, "sl.fail.toDate");
                return false;
            }
            if (searchCpKpiSoapForm.getFromDate().after(searchCpKpiSoapForm.getToDate())) {
                req.setAttribute(SEARCH_CP, 1);
                req.setAttribute(Search_CpKpiSoapMsg, "sl.fail.fromDateSmallerToDate");
                return false;
            }
        } catch (Exception e) {Constant.LogNo(e);
            req.setAttribute(SEARCH_CP, 1);
            req.setAttribute(Search_CpKpiSoapMsg, "sl.fail.search");
            return false;
        }
        return true;
    }
}
