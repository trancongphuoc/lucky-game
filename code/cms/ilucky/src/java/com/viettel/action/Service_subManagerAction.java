/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.Service_subManagerForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.Service_subBO;
import com.viettel.database.utils.DataStore;
import com.viettel.database.utils.DbCloseConn;
import com.viettel.framework.interceptor.BaseDAOMDBAction;
import com.viettel.util.VGenLog;
import com.viettel.utils.Constant;
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
public class Service_subManagerAction extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    Service_subManagerForm Service_subManagerForm = new Service_subManagerForm();

    public Service_subManagerForm getService_subManagerForm() {
        return Service_subManagerForm;
    }

    public void setService_subManagerForm(Service_subManagerForm Service_subManagerForm) {
        this.Service_subManagerForm = Service_subManagerForm;
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
            } else if (statusLogin != 1 || userlog.isEmpty()) {
                reqSess.removeAttribute(Constant.STRING_USERTOKEN);
                reqSess.invalidate();
                return Constant.STRING_SESSIONTIMEOUT;
            } else {
                s1 = "success";
            }
        } catch (Exception ex) {
            Constant.LogNo(ex);
            VGenLog.error("ERROR: INIT CORE %%% " + ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public String prepareService_subManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        String username = userToken.getUsername();
        if (!username.isEmpty()) {
//            searchServices("");
//            searchSub_Service_Sub();
            return "prepareService_subManager";
        } else {
            return ERROR;
        }
    }

    public String searchService_sub() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        String sub_service_name = Service_subManagerForm.getSub_service_name();
        String service_name = Service_subManagerForm.getService_name();
        String msisdn = Service_subManagerForm.getMsisdn();
        msisdn = msisdn.trim();
        reqSess.setAttribute("ser_s_sub_service_name", sub_service_name);
        reqSess.setAttribute("ser_s_service_name", service_name);
        reqSess.setAttribute("ser_s_msisdn", msisdn);

        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            String sqlq = "";
            ArrayList colParams = new ArrayList();
            sqlq = "select * from " + Constant.SCHEMA_NAME + ".LUCKY_SUBSCRIBER where 1=1 " ;
            if (msisdn != null && !msisdn.isEmpty()) {
                sqlq += " and msisdn = ? ";
                colParams.add(msisdn);
            }
            sqlq += " order by status desc, sub_service_name asc ";
            calStat = conn.prepareCall(sqlq);
            if (colParams.size() > 0) {
                for (int i = 0; i < colParams.size(); i++) {
                    calStat.setString(i + 1, colParams.get(i).toString());
                }
            }
            rs = calStat.executeQuery();
            List<Service_subBO> lst = new ArrayList<Service_subBO>();    
            while (rs.next()) {
                lst.add(new Service_subBO(rs.getString("sub_service_name"), rs.getString("sub_service_name"),
                        rs.getString("sub_service_name"),
                        rs.getString("sub_service_name"), rs.getTimestamp("cancel_time"),
                        rs.getString("msisdn"), rs.getString("status"), rs.getTimestamp("register_time"),
                        rs.getString("descriptions"), rs.getTimestamp("last_monfee_charge_time"),
                        rs.getTimestamp("next_monfee_charge_time"), rs.getString("charge_status"), rs.getString("is_promotion")));
            }
            req.setAttribute("Service_subList", lst);
            reqSess.setAttribute("Service_subList", lst);
            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List Service_sub");

        } catch (Exception ex) {
            Constant.LogNo(ex);
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("Service_subManagerAction", "searchService_sub()", conn, calStat, rs);
        }
        return "Service_subManagerList";
    }

    public String pagingService_subManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP)) {
            req.setAttribute("Service_subList", reqSess.getAttribute("Service_subList"));
            return "Service_subManagerList";
        } else {
            return ERROR;
        }
    }


    public String Service_sub_cancel() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
            String msisdn = req.getParameter("msisdn");
            reqSess.setAttribute("msisdn", msisdn);
            String sub_service_name = req.getParameter("sub_service_name");
            reqSess.setAttribute("sub_service_name", sub_service_name);
            CallableStatement calStat = null;
            ResultSet rs = null;
            Connection conn = null;

            if (!msisdn.equalsIgnoreCase(Constant.STRING_EMPTY)) {
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                    String sqlUserEdit = "";
                    ArrayList colParams = new ArrayList();
                    sqlUserEdit = "select * from " + Constant.SCHEMA_NAME + ".LUCKY_SUBSCRIBER where 1=1 ";

                    if (!msisdn.isEmpty() && msisdn != null) {
                        sqlUserEdit += "and msisdn = ? ";
                        colParams.add(msisdn);
                    }
                    calStat = conn.prepareCall(sqlUserEdit);
                    if (colParams != null && colParams.size() > 0) {
                        for (int i = 0; i < colParams.size(); i++) {
                            calStat.setString(i + 1, colParams.get(i).toString());
                        }
                    }
                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                    rs = calStat.executeQuery();
                    while (rs.next()) {
                        Service_subManagerForm.setMsisdn(rs.getString("msisdn"));
                        Service_subManagerForm.setSub_service_name(rs.getString("sub_service_name"));
                        Service_subManagerForm.setStatus(rs.getString("status"));
                        Service_subManagerForm.setRegister_time(rs.getTimestamp("register_time"));
                        Service_subManagerForm.setCancel_time(rs.getTimestamp("cancel_time"));
                        req.setAttribute("Ser_sub_status", rs.getString("status"));
                        reqSess.setAttribute("Ser_sub_status", rs.getString("status"));
                    }
                    insertActionLog(getClass().getName(), req.getRemoteAddr(), "Begin Cancel service subscription");
                } catch (Exception ex) {
                    Constant.LogNo(ex);
                    VGenLog.error(ex.toString());
                } finally {
                    DbCloseConn.closeQuietly("Service_subManagerAction", "Service_sub_cancel()", conn, calStat, rs);
                }
            } else {
            }
            return "Service_sub_cancel_Dlg";
    }

    public String Cancel_service_sub() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
            Service_subManagerForm.setMsisdn(reqSess.getAttribute("ser_s_msisdn").toString());
            Service_subManagerForm.setService_name(reqSess.getAttribute("ser_s_service_name").toString());
            Service_subManagerForm.setSub_service_name(reqSess.getAttribute("ser_s_sub_service_name").toString());

            String msisdn = reqSess.getAttribute("msisdn").toString();
            String sub_service_name = reqSess.getAttribute("sub_service_name").toString();
            String descriptions = Service_subManagerForm.getDescriptions();
            descriptions = userToken.getUsername().toString() + "|" + descriptions;
            ResultSet rs = null;
            Connection conn = null;
            CallableStatement calStat = null;

            if (!"".equals(msisdn)) { //update
                VGenLog.info("Cancel_service_sub|msisdn:" + msisdn + "|sub_service_name" + sub_service_name);
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                    calStat = conn.prepareCall("update LUCKY_SUBSCRIBER set status = 0, descriptions = 'CMS|CANCEL' where msisdn=? ");
                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                    calStat.setString(1, msisdn);
                    calStat.setString(2, sub_service_name);
                    rs = calStat.executeQuery();

                    if (rs.next()) {
                        if (rs.getString("status").equals(Constant.STATUS_OK)) {
                            req.setAttribute("Ret_updateService_sub", 1);
                            req.setAttribute("Ret_updateService_subMsg", "Thuê bao thực sự đã hủy!");
                        } else {
                            calStat = conn.prepareCall("{call " + Constant.SCHEMA_NAME + ".pr_services_subscriptions_u(?,?,?,?,?)}");
                            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                            calStat.setString(1, msisdn);
//                            calStat.setString(2, sub_service_name);
//                            calStat.setString(3, descriptions);
//                            calStat.setString(4, "0");
//                            calStat.setString(5, "1");
                            calStat.execute();
                            req.setAttribute("Ret_updateService_sub", 1);
//                            req.setAttribute("Ret_updateService_subMsg", "Hủy thuê bao thành công!");
                            req.setAttribute("Ret_updateService_subMsg", "cj.htbtc");
                            req.setAttribute("Ret_updateService_subMsg_param", msisdn);

                            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Cancel_service_sub: " + msisdn + "|sub_service_name" + sub_service_name);
                        }
                    }
                } catch (Exception ex) {
                    Constant.LogNo(ex);
                    req.setAttribute("Ret_updateService_sub", 1);
//                    req.setAttribute("Ret_updateService_subMsg", "Có lỗi xảy ra trong quá trình hủy thuê bao!");
                    req.setAttribute("Ret_updateService_subMsg", "cj.clxrtqthtb");
                    req.setAttribute("Ret_updateService_subMsg_param", msisdn);
                    VGenLog.error(ex.toString());
                } finally {
                    DbCloseConn.closeQuietly("Service_subManagerAction", "Cancel_service_sub()", conn, calStat, rs);
                }
            } else { //new
                req.setAttribute("Ret_updateService_sub", 1);
//                req.setAttribute("Ret_updateService_subMsg", "Có lỗi xảy ra trong quá trình hủy thuê bao!");
                req.setAttribute("Ret_updateService_subMsg", "cj.clxrtqthtb");
                req.setAttribute("Ret_updateService_subMsg_param", msisdn);
            }
            //Load list
            searchService_sub();
            return "Service_subManagerList";
    }

//    public String Active_service_sub() throws SQLException, Exception {
//        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
//            return Constant.STRING_SESSIONTIMEOUT;
//        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)
//                || user_type.equalsIgnoreCase(Constant.USER_TYPE_CC)) {
//
//            Service_subManagerForm.setMsisdn(reqSess.getAttribute("ser_s_msisdn").toString());
//            Service_subManagerForm.setService_name(reqSess.getAttribute("ser_s_service_name").toString());
//            Service_subManagerForm.setSub_service_name(reqSess.getAttribute("ser_s_sub_service_name").toString());
//
//            String msisdn = reqSess.getAttribute("msisdn").toString();
//            String sub_service_name = reqSess.getAttribute("sub_service_name").toString();
////            String descriptions = Service_subManagerForm.getDescriptions();
//            String date = Service_subManagerForm.getSub_service_code();
////            descriptions = userToken.getUsername().toString() + "|" + descriptions;
//            ResultSet rs = null;
//            Connection conn = null;
//            CallableStatement calStat = null;
//
//            if (!"".equals(msisdn)) { //update
//                VGenLog.info("Active_service_sub|msisdn:" + msisdn + "|sub_service_name" + sub_service_name);
//                try {
//                    if (conn == null || conn.isClosed()) {
//                        conn = DataStore.getConnection();
//                    }
//                    calStat = conn.prepareCall("select * from " + Constant.SCHEMA_NAME + ".services_subscriptions where msisdn=? and sub_service_name = ? ");
//                    calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
//                    calStat.setString(1, msisdn);
//                    calStat.setString(2, sub_service_name);
//                    rs = calStat.executeQuery();
//
//                    if (rs.next()) {
//                        if (rs.getString("status").equals(Constant.USER_TYPE_ADMIN)) {
//                            req.setAttribute("Ret_updateService_sub", 1);
////                            req.setAttribute("Ret_updateService_subMsg", "Thuê bao đang kích hoạt!");
//                            req.setAttribute("Ret_updateService_subMsg", "cj.tbdkh");
//                            req.setAttribute("Ret_updateService_subMsg", msisdn);
//                        } else {
//                            calStat = conn.prepareCall("{call " + Constant.SCHEMA_NAME + ". pr_services_subscriptions_u(?,?,?,?,?)}");
//                            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
//                            calStat.setString(1, msisdn);
//                            calStat.setString(2, sub_service_name);
//                            calStat.setString(3, "DK");//descriptions
//                            calStat.setString(4, "1");//status
//                            calStat.setString(5, date);
//                            calStat.execute();
//                            req.setAttribute("Ret_updateService_sub", 1);
////                            req.setAttribute("Ret_updateService_subMsg", "Kích hoạt thuê bao thành công!");
//                            req.setAttribute("Ret_updateService_subMsg", "cj.khtbtc");
//                            req.setAttribute("Ret_updateService_subMsg", msisdn);
//
//
//                            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Register_service_sub: " + msisdn + "|sub_service_name" + sub_service_name);
//                        }
//                    }
//                } catch (Exception ex) {
//                    Constant.LogNo(ex);
//                    req.setAttribute("Ret_updateService_sub", 1);
////                    req.setAttribute("Ret_updateService_subMsg", "Có lỗi xảy ra trong quá trình kích hoạt thuê bao!");
//                    req.setAttribute("Ret_updateService_subMsg", "cj.clxrtqtkhtb");
//                    req.setAttribute("Ret_updateService_subMsg_param", msisdn);
//                    VGenLog.error(ex.toString());
//                } finally {
//                    DbCloseConn.closeQuietly("Service_subManagerAction", "Active_service_sub()", conn, calStat, rs);
//                }
//            } else { //new
//                req.setAttribute("Ret_updateService_sub", 1);
////                req.setAttribute("Ret_updateService_subMsg", "Có lỗi xảy ra trong quá trình kích hoạt thuê bao!");
//                req.setAttribute("Ret_updateService_subMsg", "cj.clxrtqtkhtb");
//                req.setAttribute("Ret_updateService_subMsg_param", msisdn);
//            }
//            //Load list
//            searchService_sub();
//            return "Service_subManagerList";
//        } else {
//            return ERROR;
//        }
//    }
}
