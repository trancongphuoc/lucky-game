/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.TransactionsManagerForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.TransactionsBO;
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hanhnv62
 */
public class TransactionsManagerAction extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    TransactionsManagerForm TransactionsManagerForm = new TransactionsManagerForm();

    public TransactionsManagerForm getTransactionsManagerForm() {
        return TransactionsManagerForm;
    }

    public void setTransactionsManagerForm(TransactionsManagerForm TransactionsManagerForm) {
        this.TransactionsManagerForm = TransactionsManagerForm;
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
            } else if (statusLogin != 1 || userlog.isEmpty() || UserType.equals(Constant.USER_TYPE_CP)) {
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

    public String prepareTransactionsManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        String username = userToken.getUsername();
        return "prepareTransactionsManager";
    }

    public String searchTransactions() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        String request_time = "";
        String response_time = "";
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        String catagory_name = TransactionsManagerForm.getCatagory_name();
        String msisdn = TransactionsManagerForm.getMsisdn();
//        String tmpIsdn = "";
        msisdn = msisdn.trim();
       
//        String provider_name = TransactionsManagerForm.getProvider_name();        
        if (TransactionsManagerForm.getRequest_time() != null) {
            request_time = DateTimeUtils.convertDateTimeToString2(TransactionsManagerForm.getRequest_time());
        }
        if (TransactionsManagerForm.getResponse_time() != null) {
            response_time = DateTimeUtils.convertDateTimeToString2(TransactionsManagerForm.getResponse_time());
        }
//        String cmd = TransactionsManagerForm.getCmd();
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
            try {
                List<TransactionsBO> lst = getListTrans(msisdn, request_time, response_time);
                req.setAttribute("TransactionsList", lst);
                reqSess.setAttribute("TransactionsList", lst);
                insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List Transactions");
            } catch (Exception ex) {Constant.LogNo(ex);
                VGenLog.error(ex);
            } finally {
                DbCloseConn.closeQuietly("TransactionsManagerAction", "searchTransactions()", conn, calStat, rs);
            }
            return "TransactionsManagerList";
//       
    }
    
    
    public static List<TransactionsBO> getListTrans(String msisdn, String fromDate, String toDate){
        List<TransactionsBO> lst = new ArrayList<TransactionsBO>();
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            String sqlq = "";
            ArrayList colParams = new ArrayList();
            sqlq = "select * from " + Constant.SCHEMA_NAME + ".v_transaction_log where 1=1 ";

            if (msisdn != null && !msisdn.isEmpty()) {
                sqlq += "and msisdn like ? ";
                colParams.add("%" + msisdn + "%");
            }
            if (fromDate != null && !fromDate.isEmpty()
                    && toDate != null && !toDate.isEmpty()) {
                sqlq += " and request_time >=to_date(? ,'yyyy-MM-dd')"
                        + "  and request_time <= ( to_date(? ,'yyyy-MM-dd') +1 )";
                colParams.add(fromDate);
                colParams.add(toDate);
            }

            sqlq += " order by request_time desc";

            calStat = conn.prepareCall(sqlq);
            if (colParams.size() > 0) {
                for (int i = 0; i < colParams.size(); i++) {
                    calStat.setString(i + 1, colParams.get(i).toString());
                }
            }
            rs = calStat.executeQuery();
            while (rs.next()) {
                lst.add(new TransactionsBO(rs.getString("id"), rs.getString("vcgw_request_id"), rs.getString("msisdn"),
                            rs.getString("trans_id"), rs.getTimestamp("request_time"), rs.getTimestamp("response_time"),
                            rs.getString("response_code"), rs.getString("service_name"), rs.getString("sub_service_name"),
                            rs.getString("cmd"), rs.getString("category_name"),
                            rs.getString("item_name"), rs.getString("sub_cp_name"), rs.getString("contents"),
                            rs.getString("provider_name"), rs.getString("price"), rs.getString("charge_type"),rs.getString("channel")));
            }
        } catch (Exception ex) {
            Constant.LogNo(ex);
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("TransactionsManagerAction", "searchTransactions()", conn, calStat, rs);
        }
        return lst;
    }
//
//    public String ViewMO_MT() throws SQLException {
//        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
//            return Constant.STRING_SESSIONTIMEOUT;
//        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP) && !user_type.equalsIgnoreCase(Constant.USER_TYPE_VAS) && !user_type.equalsIgnoreCase(Constant.USER_TYPE_CC_VAS)) {
//            String Id = req.getParameter("Id");
//            CallableStatement calStat = null;
//            ResultSet rs = null;
//            Connection conn = null;
//
//            if (!Id.equalsIgnoreCase(Constant.STRING_EMPTY)) {
//                try {
//                    if (conn == null || conn.isClosed()) {
//                        conn = DataStore.getConnection();
//                    }
//                    String sqlUserEdit = "select a.msisdn, a.Sub_service_name, a.contents, b.contents as MO, c.contents as MT from "
////                            + ",Service_name , Catagory_name , Request_time , Response_time "
//                            + Constant.SCHEMA_NAME + ".v_transaction_mpi a, "
//                            + Constant.SCHEMA_NAME + ".v_TRANSACTION_MO b, " + Constant.SCHEMA_NAME + ".v_TRANSACTION_MT c "
//                            + "where a.trans_id = b.trans_id and b.trans_id = c.trans_id and a.trans_id =?";
//                    calStat = conn.prepareCall(sqlUserEdit);
//                    calStat.setString(1, Id);
//                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
//                    rs = calStat.executeQuery();
//                    while (rs.next()) {
//                        TransactionsManagerForm.setMsisdn(rs.getString("msisdn"));
//                        TransactionsManagerForm.setSub_service_name(rs.getString("Sub_service_name"));
//                        TransactionsManagerForm.setCmd(rs.getString("contents"));
//                        TransactionsManagerForm.setProvider_name(rs.getString("MO"));
//                        TransactionsManagerForm.setItem_name(rs.getString("MT"));
//                    }
//                    insertActionLog(getClass().getName(), req.getRemoteAddr(), "View MO MT");
//                    try {
//                        if (conn != null) {
//                            conn.close();
//                        }
//                        if (calStat != null) {
//                            calStat.close();
//                        }
//                        if (rs != null) {
//                            rs.close();
//                        }
//                    } catch (Exception ex) {Constant.LogNo(ex);
//                        VGenLog.error("ViewMO_MT|" + ex.toString());
//                    }
//                } catch (Exception ex) {Constant.LogNo(ex);
//                    VGenLog.error(ex.toString());
//                } finally {
//                    DbCloseConn.closeQuietly("TransactionsManagerAction", "ViewMO_MT()", conn, calStat, rs);
//                }
//            } 
//            return "MO_MT_Dlg";
//        } else {
//            return ERROR;
//        }
//    }

    public String pagingTransactionsManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP)) {
            req.setAttribute("TransactionsList", reqSess.getAttribute("TransactionsList"));
            return "TransactionsManagerList";
        } else {
            return ERROR;
        }
    }

}
