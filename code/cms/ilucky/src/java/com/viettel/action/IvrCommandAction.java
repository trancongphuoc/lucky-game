/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.IvrCommandsManagerEditForm;
import com.viettel.client.form.IvrCommandsManagerForm;
import com.viettel.database.bean.IvrCommands;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.IvrCommandsBO;
import com.viettel.database.utils.DataStore;
import com.viettel.database.utils.DbCloseConn;
import com.viettel.framework.interceptor.BaseDAOMDBAction;
import com.viettel.util.VGenLog;
import com.viettel.utils.Constant;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
public class IvrCommandAction extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    IvrCommandsManagerEditForm Ivr_commandsManagerEditForm = new IvrCommandsManagerEditForm();
    IvrCommandsManagerForm Ivr_commandsManagerForm = new IvrCommandsManagerForm();

    public IvrCommandsManagerEditForm getIvr_commandsManagerEditForm() {
        return Ivr_commandsManagerEditForm;
    }

    public void setIvr_commandsManagerEditForm(IvrCommandsManagerEditForm Ivr_commandsManagerEditForm) {
        this.Ivr_commandsManagerEditForm = Ivr_commandsManagerEditForm;
    }

    public IvrCommandsManagerForm getIvr_commandsManagerForm() {
        return Ivr_commandsManagerForm;
    }

    public void setIvr_commandsManagerForm(IvrCommandsManagerForm Ivr_commandsManagerForm) {
        this.Ivr_commandsManagerForm = Ivr_commandsManagerForm;
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
            } else if (statusLogin != 1 || userlog.isEmpty() || !UserType.equals(Constant.USER_TYPE_ADMIN)) {
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

    public String prepareIvr_commandsManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        String username = userToken.getUsername();
//        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN) && !username.isEmpty()) {
//            searchServices("");
//            searchSub_Service();
//            searchWebservice();
            return "prepareIvr_commandsManager";
//        } else {
//            return ERROR;
//        }
    }

//
//    public String searchSub_Service() throws SQLException {
//        initCore();
//        Connection conn = null;
//        CallableStatement calStat = null;
//        ResultSet rs = null;
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//
//        String service_name = "";
//        String check_Service = reqSess.getAttribute("check_Service").toString();
//        String service_name_client = req.getParameter("service_name");
//        if (service_name_client != null && !service_name_client.isEmpty()) {
//            service_name = service_name_client;
//            Ivr_commandsManagerForm.setService_name(service_name);
//        } else if (check_Service.equals(Constant.USER_TYPE_ADMIN)) {
//            service_name = "1";
//            reqSess.setAttribute("check_Service", "-1");
//        }
//
//        try {
//            if (conn == null || conn.isClosed()) {
//                conn = DataStore.getConnection();
//            }
//            String sqlq = "";
//            if (user_type.equals(Constant.USER_TYPE_ADMIN)) {
//                if (service_name.isEmpty()) {
//                    sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".sub_services order by sub_service_name";
//                    calStat = conn.prepareCall(sqlq);
//                } else {
//                    sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".sub_services where service_name=? order by sub_service_name";
//                    calStat = conn.prepareCall(sqlq);
//                    calStat.setString(1, service_name);
//                }
//            } 
//            rs = calStat.executeQuery();
//            List<Sub_ServicesBO> lst = new ArrayList<Sub_ServicesBO>();
//            while (rs.next()) {
//                lst.add(new Sub_ServicesBO(rs.getString("sub_service_code"), rs.getString("sub_service_name"), rs.getString("descriptions"), rs.getString("service_name"),
//                        rs.getString("is_subscriptions"), rs.getString("status"), rs.getTimestamp("time_create"), rs.getString("fix_time_charge_monfee"),
//                        rs.getString("day_charge_monfee"), rs.getString("group_charge_type"), rs.getString("subs_type"),
//                        rs.getString("subs_price"), rs.getString("promo_price"), rs.getTimestamp("promo_datetime_begin"), rs.getTimestamp("promo_datetime_end"),
//                        rs.getString("day_debit_allowed"), rs.getString("monfee_ws_id"), rs.getString("register_ws_id"), rs.getString("cancel_ws_id"),rs.getString("notify_ws_id"),
//                        rs.getString("public_cp"), rs.getString("public_vt_cp"), rs.getString("private_cp"), rs.getString("private_vt_cp"), rs.getString("url_return"),
//                        rs.getString("timeout_trans"),rs.getString("url_notify"),rs.getString("is_promo")));
//            }
//            req.setAttribute("mSub_ServiceList", lst);
//            reqSess.setAttribute("mSub_ServiceList", lst);
//            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List Sub_Service");
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (calStat != null) {
//                    calStat.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception ex) {Constant.LogNo(ex);
//                VGenLog.error("searchSub_Service|" + ex.toString());
//            }
//        } catch (Exception ex) {Constant.LogNo(ex);
//            VGenLog.error(ex);
//        } finally {
//            DbCloseConn.closeQuietly("Ivr_commandsManagerAction", "searchSub_Service()", conn, calStat, rs);
//        }
//        return "Ivr_commandsDiv";
//    }
//
//    public String searchWebservice() throws SQLException {
//        initCore();
//        Connection conn = null;
//        CallableStatement calStat = null;
//        ResultSet rs = null;
//
//        try {
//            if (conn == null || conn.isClosed()) {
//                conn = DataStore.getConnection();
//            }
//
//            String sub_service_name = req.getParameter("sub_service_name");
//            if (Ivr_commandsManagerEditForm.getSub_service_name() != null && Ivr_commandsManagerEditForm.getSub_service_name() != "-1") {
//                sub_service_name = Ivr_commandsManagerEditForm.getSub_service_name();
//            }
//            if (sub_service_name == null) {
//                sub_service_name = "";
//            }
//            Ivr_commandsManagerEditForm.setSub_service_name(sub_service_name);
//
//            String sqlq = "";
//            sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".CP_webservice_accounts order by webservice_name";
//            calStat = conn.prepareCall(sqlq);
//            if (!sub_service_name.isEmpty() && !sub_service_name.equals(Constant.STRING_NOCHOICE)) {
//                sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".CP_webservice_accounts where sub_service_name in ("
//                        + "select sub_service_name from " + Constant.SCHEMA_NAME + ".sub_services where sub_service_name =?"
//                        + ") order by webservice_name";
//                calStat = conn.prepareCall(sqlq);
//                calStat.setString(1, sub_service_name);
//            }
//
//            rs = calStat.executeQuery();
//            List<CP_webservice_accountsBO> lst = new ArrayList<CP_webservice_accountsBO>();
//            while (rs.next()) {
//                lst.add(new CP_webservice_accountsBO(rs.getString("webservice_id"), rs.getString("sub_service_name"),
//                        rs.getString("user_name"), rs.getString("password"), rs.getString("url"), rs.getString("params"),
//                        rs.getString("webservice_type"), rs.getString("soap_action"), rs.getString("tag_prefix"),
//                        rs.getString("webservice_name"), rs.getString("descriptions"), rs.getString("raw_xml"),
//                        rs.getString("return_tag")));
//            }
//            req.setAttribute("sWebserviceList", lst);
//            reqSess.setAttribute("sWebserviceList", lst);
//            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List searchWebservice");
////            searchServices("");
//            searchSub_Service();
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (calStat != null) {
//                    calStat.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception ex) {Constant.LogNo(ex);
//                VGenLog.error("searchWebservice|" + ex.toString());
//            }
//        } catch (Exception ex) {Constant.LogNo(ex);
//            VGenLog.error(ex);
//        } finally {
//            DbCloseConn.closeQuietly("Ivr_commandsManagerAction", "searchWebservice()", conn, calStat, rs);
//        }
//        return "Ivr_commandsManagerDlg";
//    }

    public String preEditIvr_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
//        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
        String id = req.getParameter("id");
        String sub_service_name = req.getParameter("sub_service_name");

        String Copy_Ivr = req.getParameter("Copy_Ivr");
        if (Copy_Ivr == null) {
            Copy_Ivr = "";
        }
        if (sub_service_name == null) {
            sub_service_name = "";
        }
        req.setAttribute("Copy_Ivr", Copy_Ivr);
        reqSess.setAttribute("Copy_Ivr", Copy_Ivr);

        CallableStatement calStat = null;
        ResultSet rs = null;
        Connection conn = null;

//        if (!id.equalsIgnoreCase(Constant.STRING_EMPTY)) {
        try {
//                    searchServices("");
//                    searchSub_Service();

            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            String sqlUserEdit = "SELECT * from QUESTION_CONTENT  where  id= ? ";
            calStat = conn.prepareCall(sqlUserEdit);
            calStat.setString(1, id);
            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
            rs = calStat.executeQuery();

            while (rs.next()) {
                Ivr_commandsManagerEditForm.setId(rs.getString("Id"));
                
                Ivr_commandsManagerEditForm.setContent(rs.getString("CONTENT"));
                Ivr_commandsManagerEditForm.setAnswer1(rs.getString("ANSWER1"));
                Ivr_commandsManagerEditForm.setAnswer2(rs.getString("ANSWER2"));
                Ivr_commandsManagerEditForm.setAnswer3(rs.getString("ANSWER3"));
                Ivr_commandsManagerEditForm.setAnswer4(rs.getString("ANSWER4"));
                Ivr_commandsManagerEditForm.setAnswerCorrect(rs.getString("ANSWER_CORRECT"));
                Ivr_commandsManagerEditForm.setQuestionCode(rs.getString("QUESTION_CODE"));
                Ivr_commandsManagerEditForm.setUpdated(rs.getTimestamp("UPDATED"));
                Ivr_commandsManagerEditForm.setDifficult(rs.getString("DIFFICULT"));
            }
//                    searchWebservice();
        } catch (Exception ex) {
            Constant.LogNo(ex);
            VGenLog.error(ex.toString());
        } finally {
            DbCloseConn.closeQuietly("Ivr_commandsManagerAction", "preEditIvr_commands()", conn, calStat, rs);
        }
//        } else {
////                IvrCommandsManagerEditForm.setOldid(id);
//        }

        return "Ivr_commandsManagerDlg";
//        } else {
//            return ERROR;
//        }
    }

    public String preAddIvr_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
//            searchSub_Service();
//        String sub_service_name = "";
//        if (!req.getParameter("sub_service_name").isEmpty()) {
//            sub_service_name = req.getParameter("sub_service_name");
//            Ivr_commandsManagerEditForm.setSub_service_name(sub_service_name);
//        }
//            searchWebservice();
        req.setAttribute("Copy_Ivr", "");
        reqSess.setAttribute("Copy_Ivr", "");
        return "Ivr_commandsManagerDlg";

    }

    private final static String ALL_VALUE = "-1" ;
    
    public String searchIvr_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
        String search = Ivr_commandsManagerForm.getSearch();
        Connection conn = null;
        PreparedStatement calStat = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            StringBuilder sql = new StringBuilder("SELECT a.* FROM QUESTION_CONTENT a WHERE 1=1");
            List<Object> conditionValues = new LinkedList<>();

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (CONTENT like ? or QUESTION_CODE like ? ) ");
                conditionValues.add("%"+search+"%");
                conditionValues.add("%"+search+"%");
            }

            sql.append("  order by QUESTION_CODE asc, updated desc  ");
            
            calStat = conn.prepareCall(sql.toString());

            if (!conditionValues.isEmpty()) {
                int i = 0;
                for (Object conditionValue : conditionValues) {
                    calStat.setObject(++i, conditionValue);
                }
            }
            rs = calStat.executeQuery();
            List<IvrCommandsBO> lst = new LinkedList<IvrCommandsBO>();
            while (rs.next()) {
                String id = rs.getString("Id");
                String content = rs.getString("CONTENT");
                String answer1 = rs.getString("ANSWER1");
                String answer2 = rs.getString("ANSWER2");
                String answer3 = rs.getString("ANSWER3");
                String answer4 = rs.getString("ANSWER4");
                String answerCorrect = rs.getString("ANSWER_CORRECT");
                String questionCode = rs.getString("QUESTION_CODE");
                Timestamp updated = rs.getTimestamp("UPDATED");
                String difficult = rs.getString("DIFFICULT");
                IvrCommandsBO bo = new IvrCommandsBO(id, content, answer1, answer2, answer3, answer4, answerCorrect, questionCode, updated, difficult) ;
                lst.add(bo);
            }
            req.setAttribute("Ivr_commandsList", lst);
            reqSess.setAttribute("Ivr_commandsList", lst);
            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List question_commands");

        } catch (Exception ex) {
            Constant.LogNo(ex);
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("question_commandsManagerAction", "searchquestion_commands()", conn, calStat, rs);
        }
        return "Ivr_commandsManagerList";
    }

    public String pagingIvr_commandsManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
//        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            req.setAttribute("Ivr_commandsList", reqSess.getAttribute("Ivr_commandsList"));
//            searchIvr_commands();
            return "Ivr_commandsManagerList";
//        } else {
//            return ERROR;
//        }
    }

    public String updateInsertIvr_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        
        String id = Ivr_commandsManagerEditForm.getId();
        String content = Ivr_commandsManagerEditForm.getContent();
        String answer1 = Ivr_commandsManagerEditForm.getAnswer1();
        String answer2 = Ivr_commandsManagerEditForm.getAnswer2();
        String answer3 = Ivr_commandsManagerEditForm.getAnswer3();
        String answer4 = Ivr_commandsManagerEditForm.getAnswer4();
        String answerCorrect = Ivr_commandsManagerEditForm.getAnswerCorrect();
        String questionCode = Ivr_commandsManagerEditForm.getQuestionCode();
        String difficult = Ivr_commandsManagerEditForm.getDifficult();
        
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;

        int i = 0;
        if (id == null || id.isEmpty()) { //new
            UUID uuid = UUID.randomUUID();
            id = uuid.toString();
            
            try {
                if (conn == null || conn.isClosed()) {
                    conn = DataStore.getConnection();
                }
                if (conn == null) {
                    return null;
                }
                
                String sqlNew = new String("INSERT INTO QUESTION_CONTENT(ID,CONTENT,ANSWER1, ANSWER2, ANSWER3, ANSWER4, ANSWER_CORRECT, QUESTION_CODE, UPDATED ,DIFFICULT)"
                        + " VALUES (?,?,?,?,?,?,?,?,sysdate,?)");
                calStat = conn.prepareCall(sqlNew);
                calStat.setString(++i, id);
                calStat.setString(++i, content);
                calStat.setString(++i, answer1);
                calStat.setString(++i, answer2);
                calStat.setString(++i, answer3);
                calStat.setString(++i, answer4);
                calStat.setString(++i, answerCorrect);
                calStat.setString(++i, questionCode);
                calStat.setString(++i, difficult);
               
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                req.setAttribute("Ret_updateIvr_commands", 1);
                req.setAttribute("Ret_updateIvr_commandsMsg", "alarm.add.ivr.success");
                req.setAttribute("Ret_updateIvr_commandsMsg_param", id);
                insertActionLog(getClass().getName(), req.getLocalAddr(), "Insert IVR_COMMAND: " + id);

            } catch (Exception ex) {
                Constant.LogNo(ex);
                req.setAttribute("Ret_updateIvr_commands", 1);
                req.setAttribute("Ret_updateIvr_commandsMsg", "alarm.add.ivr.fail");
                req.setAttribute("Ret_updateIvr_commandsMsg_param", id);
                VGenLog.error(ex.toString());
            } finally {
                DbCloseConn.closeQuietly("Mo_commandsManagerAction", "updateInsertUser()", conn, calStat, rs);
            }
        } else { //update

            try {
                if (conn == null || conn.isClosed()) {
                    conn = DataStore.getConnection();
                }
                if (conn == null) {
                    return null;
                }
                String sqlEdit = new String("UPDATE QUESTION_CONTENT set "
                        + " CONTENT = ?,"
                        + " ANSWER1 = ?,"
                        + " ANSWER2 = ?,"
                        + " ANSWER3 = ?,"
                        + " ANSWER4 = ?,"
                        + " ANSWER_CORRECT = ?,"
                        + " QUESTION_CODE = ?,"
                        + " difficult = ?,"
                        + " UPDATED = sysdate"
                        + " WHERE ID = ? ");
                calStat = conn.prepareCall(sqlEdit);
                calStat.setString(++i, content);
                calStat.setString(++i, answer1);
                calStat.setString(++i, answer2);
                calStat.setString(++i, answer3);
                calStat.setString(++i, answer4);
                calStat.setString(++i, answerCorrect);
                calStat.setString(++i, questionCode);
                calStat.setString(++i, difficult);
                calStat.setString(++i, id);
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                req.setAttribute("Ret_updateIvr_commands", 1);
                req.setAttribute("Ret_updateIvr_commandsMsg", "success");
                req.setAttribute("Ret_updateIvr_commandsMsg_param", id);
                insertActionLog(getClass().getName(), req.getLocalAddr(), "Update question_COMMAND: " + id);
            } catch (Exception ex) {
                req.setAttribute("Ret_updatequestion_commands", 1);
                req.setAttribute("Ret_updatequestion_commandsMsg", "fail");
                req.setAttribute("Ret_updatequestion_commandsMsg_param", id);
                VGenLog.error(ex.toString());
            } finally {
                DbCloseConn.closeQuietly("question_commandsManagerAction", "updateInsertquestion_commands()", conn, calStat, rs);
            }

        }
        //Load list
        searchIvr_commands();
        return "Ivr_commandsManagerList";
//        } else {
//            return ERROR;
//        }
    }

    public String deleteIvr_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String id = req.getParameter("id");
            ResultSet rs = null;
            Connection conn = null;
            CallableStatement calStat = null;

            if (!"".equals(id)) { //update
      
                try {
                    if (conn == null || conn.isClosed()) {
                        conn = DataStore.getConnection();
                    }
                        String sqlDelete = new String("DELETE FROM QUESTION_CONTENT WHERE ID = ?");
                        calStat = conn.prepareCall(sqlDelete);
                        calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                        calStat.setString(1, id);
                        calStat.execute();
                        req.setAttribute("Ret_updateIvr_Commands", 1);
                        req.setAttribute("Ret_updateIvr_CommandsMsg", "success");
                        req.setAttribute("Ret_updateIvr_CommandsMsg_param",id);
                        insertActionLog(getClass().getName(), req.getRemoteAddr(), "Delete question_COMMAND: " + id);
                } catch (Exception ex) {Constant.LogNo(ex);
                    req.setAttribute("Ret_updateIvr_Commands", 1);
                    req.setAttribute("Ret_updateIvr_CommandsMsg", "fail");
                    req.setAttribute("Ret_updateIvr_CommandsMsg_param",id);
                    VGenLog.error(ex.toString());
                } finally {
                    DbCloseConn.closeQuietly("Ivr_commandsManagerAction", "deleteId()", conn, calStat, rs);
                }
            } else { //new
                req.setAttribute("Ret_updateIvr_Commands", 1);
                req.setAttribute("Ret_updateIvr_CommandsMsg", "fail");
                req.setAttribute("Ret_updateIvr_CommandsMsg_param",id);
            }
            //Load list
            searchIvr_commands();
            return "Ivr_commandsManagerList";
        } else {
            return ERROR;
        }
    }

    public Boolean checkIvr_commands(String Id, String sub_service_name) throws SQLException {
        Connection conn = null;
        List<IvrCommands> result = new ArrayList<IvrCommands>();
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
            String sql = "select * FROM QUESTION_CONTENT ";
            sql += " WHERE 1=1 ";
            if (Id.trim().length() > 0) {
                sql += "AND id = ? ";
                colParams.add(Id);
                colParams.add(sub_service_name);
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
                result.add(new IvrCommands(rs.getString("cmd"), rs.getString("sub_service_name"), rs.getString("status")));
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error("Error: " + ex.getMessage());
        } finally {
            DbCloseConn.closeQuietly("Ivr_commandsManagerAction", "checkId()", conn, calStat, rs);
        }
        if (result != null && result.size() > 0) {
            return true;
        }
        return false;
    }
}