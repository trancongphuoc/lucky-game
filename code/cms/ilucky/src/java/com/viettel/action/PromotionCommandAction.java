package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.PromotionCommandsManagerEditForm;
import com.viettel.client.form.PromotionCommandsManagerForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.PromotionCommandsBO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PromotionCommandAction extends BaseDAOMDBAction {

    private final static String ALL_VALUE = "-1";
    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    PromotionCommandsManagerEditForm Promotion_commandsManagerEditForm = new PromotionCommandsManagerEditForm();
    PromotionCommandsManagerForm Promotion_commandsManagerForm = new PromotionCommandsManagerForm();

    public PromotionCommandsManagerEditForm getPromotion_commandsManagerEditForm() {
        return Promotion_commandsManagerEditForm;
    }

    public void setPromotion_commandsManagerEditForm(PromotionCommandsManagerEditForm Promotion_commandsManagerEditForm) {
        this.Promotion_commandsManagerEditForm = Promotion_commandsManagerEditForm;
    }

    public PromotionCommandsManagerForm getPromotion_commandsManagerForm() {
        return Promotion_commandsManagerForm;
    }

    public void setPromotion_commandsManagerForm(PromotionCommandsManagerForm Promotion_commandsManagerForm) {
        this.Promotion_commandsManagerForm = Promotion_commandsManagerForm;
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
        } catch (Exception ex) {
            VGenLog.error("ERROR: INIT CORE %%% ", ex);
            return Constant.STRING_SESSIONTIMEOUT;
        }
        return s1;
    }

    public String preparePromotion_commandsManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        String username = userToken.getUsername();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN) && !username.isEmpty()) {
//            searchServices("");
//            searchSub_Service();
//            searchWebservice();
            return "preparePromotion_commandsManager";
        } else {
            return ERROR;
        }
    }


    public String preEditPromotion_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            String id = req.getParameter("id");
            String sub_service_name = req.getParameter("sub_service_name");

            String Copy_Promotion = req.getParameter("Copy_Promotion");
            if (Copy_Promotion == null) {
                Copy_Promotion = "";
            }
            if (sub_service_name == null) {
                sub_service_name = "";
            }
            req.setAttribute("Copy_Promotion", Copy_Promotion);
            reqSess.setAttribute("Copy_Promotion", Copy_Promotion);

            CallableStatement calStat = null;
            ResultSet rs = null;
            Connection conn = null;

            if (id.equalsIgnoreCase(Constant.STRING_EMPTY)) {
                return ERROR;
            }
            try {
//                searchServices("");
//                searchSub_Service();

                if (conn == null || conn.isClosed()) {
                    conn = DataStore.getConnection();
                }
                String sqlUserEdit = "SELECT * from " + Constant.SCHEMA_NAME + ".PROMOTION_COMMAND  where id= ? ";
                calStat = conn.prepareCall(sqlUserEdit);
                calStat.setString(1, id);
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                while (rs.next()) {
                    if (!Copy_Promotion.equals(Constant.USER_TYPE_ADMIN)) {
                        Promotion_commandsManagerEditForm.setId(rs.getString("id"));
                        Promotion_commandsManagerEditForm.setSubServiceName(rs.getString("Sub_service_name"));
                    }
                    Promotion_commandsManagerEditForm.setDatetime(rs.getTimestamp("Datetime"));
                    Promotion_commandsManagerEditForm.setStatus(rs.getInt("Status"));
                    Promotion_commandsManagerEditForm.setDescription(rs.getString("Description"));
                    Promotion_commandsManagerEditForm.setType(rs.getString("Type"));
                    Promotion_commandsManagerEditForm.setSuccessMessage(rs.getString("Success_message"));
                    Promotion_commandsManagerEditForm.setFailMessage(rs.getString("Failed_message"));
                    Promotion_commandsManagerEditForm.setWebserviceId(rs.getInt("Webservice_id"));
                    Promotion_commandsManagerEditForm.setChargeCommand(rs.getString("Charge_cmd"));
                    Promotion_commandsManagerEditForm.setCheckRequired(rs.getInt("Check_required"));
                    Promotion_commandsManagerEditForm.setCmdPrice(rs.getInt("Cmd_price"));
                    Promotion_commandsManagerEditForm.setMessageChannel(rs.getInt("Message_channel"));

                    Promotion_commandsManagerEditForm.setFromDate(rs.getDate("from_date"));
                    Promotion_commandsManagerEditForm.setToDate(rs.getDate("to_date"));
                    Promotion_commandsManagerEditForm.setPromotionChannel(rs.getString("promotion_channel"));
                    Promotion_commandsManagerEditForm.setCode(rs.getString("code"));
                    Promotion_commandsManagerEditForm.setFreeDay(rs.getInt("free_day"));
                    Promotion_commandsManagerEditForm.setConfirmMessage(rs.getString("CONFIRM_MESSAGE"));
                }
//                searchWebservice();
            } catch (Exception ex) {
                Constant.LogNo(ex);
                VGenLog.error(ex.toString());
            } finally {
                DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "preEditPromotion_commands()", conn, calStat, rs);
            }
        }

        return "Promotion_commandsManagerDlg";
    }

    public String preAddPromotion_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
//            searchSub_Service();
            String sub_service_name = "";
            if (!req.getParameter("sub_service_name").isEmpty()) {
                sub_service_name = req.getParameter("sub_service_name");
                Promotion_commandsManagerEditForm.setSubServiceName(sub_service_name);
            }
//            searchWebservice();
            req.setAttribute("Copy_Promotion", "");
            reqSess.setAttribute("Copy_Promotion", "");
            return "Promotion_commandsManagerDlg";
        } else {
            return ERROR;
        }
    }

    public String searchPromotion_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            return ERROR;
        }
        String sub_service_name = Promotion_commandsManagerForm.getSub_service_name();
        String service_name = Promotion_commandsManagerForm.getService_name();
        String status = Promotion_commandsManagerForm.getStatus();
        reqSess.setAttribute("moc_sub_service_name", sub_service_name);
        reqSess.setAttribute("moc_service_name", service_name);
        reqSess.setAttribute("moc_status", status);
        Connection conn = null;
        PreparedStatement calStat = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            StringBuilder sql = new StringBuilder("SELECT a.* FROM promotion_command a WHERE 1=1 ");
            List<Object> conditionValues = new LinkedList<>();

            if (!ALL_VALUE.equals(status) && status != null) {
                sql.append(" AND a.status= ? ");
                conditionValues.add(status);
            }

            if (!ALL_VALUE.equals(sub_service_name) && sub_service_name != null) {
                sql.append(" AND a.SUB_SERVICE_NAME = ? ");
                conditionValues.add(sub_service_name);
            }

            if (!ALL_VALUE.equals(service_name) && service_name != null) {
                sql.append(" AND a.SUB_SERVICE_NAME in (select b.SUB_SERVICE_NAME from sub_services b where b.SERVICE_NAME = ?) ");
                conditionValues.add(service_name);
            }

            sql.append(" order by a.SUB_SERVICE_NAME asc, a.code asc ");

            calStat = conn.prepareCall(sql.toString());

            if (!conditionValues.isEmpty()) {
                int i = 0;
                for (Object conditionValue : conditionValues) {
                    calStat.setObject(++i, conditionValue);
                }
            }
            rs = calStat.executeQuery();
            List<PromotionCommandsBO> lst = new LinkedList<PromotionCommandsBO>();
            PromotionCommandsBO promotionBO = null;
            while (rs.next()) {
                promotionBO = new PromotionCommandsBO();
                promotionBO.setDatetime(rs.getTimestamp("DATETIME"));
                promotionBO.setStatus(rs.getInt("Status"));
                promotionBO.setDescription(rs.getString("Description"));
                promotionBO.setSubServiceName(rs.getString("SUB_SERVICE_NAME"));

                promotionBO.setType(rs.getString("Type"));
                promotionBO.setSuccessMessage(rs.getString("Success_message"));
                promotionBO.setFailMessage(rs.getString("Failed_message"));
                promotionBO.setWebserviceId(rs.getInt("Webservice_id"));

                promotionBO.setChargeCommand(rs.getString("Charge_cmd"));
                promotionBO.setCheckRequired(rs.getInt("Check_required"));
                promotionBO.setCmdPrice(rs.getInt("Cmd_price"));
                promotionBO.setMessageChannel(rs.getInt("Message_channel"));
                promotionBO.setId(rs.getString("ID"));

                promotionBO.setFromDate(rs.getDate("FROM_DATE"));
                promotionBO.setToDate(rs.getDate("TO_DATE"));
                promotionBO.setCode(rs.getString("CODE"));
                promotionBO.setFreeDay(rs.getInt("FREE_DAY"));
                promotionBO.setConfirmMessage(rs.getString("CONFIRM_MESSAGE"));
                lst.add(promotionBO);
            }
            req.setAttribute("Promotion_commandsList", lst);
            reqSess.setAttribute("Promotion_commandsList", lst);
            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List Promotion_commands");
        } catch (Exception ex) {
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "searchPromotion_commands()", conn, calStat, rs);
        }
        return "Promotion_commandsManagerList";

    }

    public String pagingPromotion_commandsManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            req.setAttribute("Promotion_commandsList", reqSess.getAttribute("Promotion_commandsList"));
//            searchPromotion_commands();
            return "Promotion_commandsManagerList";
        } else {
            return ERROR;
        }
    }

    public String updateInsertPromotion_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            return ERROR;
        }
        String sub_service_name = Promotion_commandsManagerEditForm.getSubServiceName();
        int status = Promotion_commandsManagerEditForm.getStatus();
        String description = Promotion_commandsManagerEditForm.getDescription();
        String type = Promotion_commandsManagerEditForm.getType();
        String success_message = Promotion_commandsManagerEditForm.getSuccessMessage();
        String failed_message = Promotion_commandsManagerEditForm.getFailMessage();
        int webservice_id = Promotion_commandsManagerEditForm.getWebserviceId();
        int check_required = Promotion_commandsManagerEditForm.getCheckRequired();
        int cmd_price = Promotion_commandsManagerEditForm.getCmdPrice();
        int message_channel = Promotion_commandsManagerEditForm.getMessageChannel();
        String confirmMessage = Promotion_commandsManagerEditForm.getConfirmMessage();

        Date tmpTromDate = Promotion_commandsManagerEditForm.getFromDate();
        java.sql.Date newFromDate = null;
        if (tmpTromDate != null) {
            newFromDate = new java.sql.Date(tmpTromDate.getTime());
        } else {
            req.setAttribute("Ret_updatePromotion_commands", 1);
            req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.fail_fromdate_null");
            req.setAttribute("Ret_updatePromotion_commandsMsg_param", "unknown");
            searchPromotion_commands();
            return "Promotion_commandsManagerList";
        }

        Date tmpToDate = Promotion_commandsManagerEditForm.getToDate();
        java.sql.Date newToDate = null;
        if (tmpToDate != null) {
            newToDate = new java.sql.Date(tmpToDate.getTime());

            if (newToDate.before(newFromDate)) {
                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.fail_todate_before_fromdate");
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", "unknown");
                searchPromotion_commands();
                return "Promotion_commandsManagerList";
            }
        }

        String charge_cmd = Promotion_commandsManagerEditForm.getChargeCommand();
        String promotionChannel = Promotion_commandsManagerEditForm.getPromotionChannel();
        String code = Promotion_commandsManagerEditForm.getCode();
        int freeDay = Promotion_commandsManagerEditForm.getFreeDay();

        if (code == null || code.trim().isEmpty()) {
            req.setAttribute("Ret_updatePromotion_commands", 1);
            req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.code_null");
            req.setAttribute("Ret_updatePromotion_commandsMsg_param", "unknown");
            searchPromotion_commands();
            return "Promotion_commandsManagerList";
        }

        List<PromotionCommandsBO> commandsBOs = checkPromotion_commands(sub_service_name);

        String existedPromotionCode = null;
        String conflictTime = "";

        String id = Promotion_commandsManagerEditForm.getId();

        for (PromotionCommandsBO promotionCommandsBO : commandsBOs) {
            Date oldFromDate = promotionCommandsBO.getFromDate();
            Date oldToDate = promotionCommandsBO.getToDate();

            if (id != null && !id.isEmpty()
                    && promotionCommandsBO.getId().equals(id)) {
                continue;
            }

            if (oldToDate == null) {
                if (newToDate == null) {
                    // (newFromdate, ~) conflict (oldFromdate, ~)
                    //ca 2 loai khuyen mai la khuyen mai vo thoi han
                    conflictTime = "(newFromdate, ~) conflict (oldFromdate, ~)";
                    existedPromotionCode = promotionCommandsBO.getCode();
                    break;
                }

                if (newToDate.after(oldFromDate)) {
                    //newToDate in (oldFromDate, ~)
                    // ngay khuyen mai ban ghi moi, sau ngay bat dau khuyen mai cu
                    conflictTime = "newToDate in (oldFromDate, ~)";
                    existedPromotionCode = promotionCommandsBO.getCode();
                    break;
                }

                //cac truong hop con lai thoa man
                continue;
            }

            if (newToDate == null) {
                if (oldToDate.after(newFromDate)) {
                    //oldTodate in (newFromDate, ~)
                    //neu khuyen mai hien tai la vo thoi han, ma han cua khuyen
                    //mai truoc nam trong dai do
                    conflictTime = "oldTodate in (newFromDate, ~)";
                    existedPromotionCode = promotionCommandsBO.getCode();
                    break;
                }
                continue;
            }

            if (newToDate.after(oldFromDate)
                    && newToDate.before(oldToDate)) {
                //newTodate in (oldFromDate, oldToDate)
                conflictTime = "newTodate in (oldFromDate, oldToDate)";
                existedPromotionCode = promotionCommandsBO.getCode();
                break;
            }

            if (newFromDate.after(oldFromDate)
                    && newFromDate.before(oldToDate)) {
                //newFromdate in (oldFromDate, oldToDate)
                conflictTime = "newFromdate in (oldFromDate, oldToDate)";
                existedPromotionCode = promotionCommandsBO.getCode();
                break;
            }

            if (oldFromDate.after(newFromDate)
                    && oldFromDate.before(newToDate)) {
                //oldFromDate in (newFromDate, newToDate)
                conflictTime = "oldFromDate in (newFromDate, newToDate)";
                existedPromotionCode = promotionCommandsBO.getCode();
                break;
            }
        }

        if (existedPromotionCode != null) {
            req.setAttribute("Ret_updatePromotion_commands", 1);
            req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.fail_existed");
            req.setAttribute("Ret_updatePromotion_commandsMsg_param", existedPromotionCode + "," + conflictTime);
            searchPromotion_commands();
            return "Promotion_commandsManagerList";
        }

        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        if (conn == null || conn.isClosed()) {
            conn = DataStore.getConnection();
        }
        if (conn == null) {
            return null;
        }
        if (id == null || id.isEmpty()) { //new
            if (sub_service_name == null || sub_service_name.isEmpty()) {
                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "cj.clxrtqtcnmlmo");
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
                return ERROR;
            }
            UUID uuid = UUID.randomUUID();
            id = uuid.toString();

            try {
                String sqlNew = new String("INSERT INTO PROMOTION_COMMAND (SUB_SERVICE_NAME,STATUS,DESCRIPTION,"
                        + "TYPE,SUCCESS_MESSAGE,FAILED_MESSAGE,WEBSERVICE_ID,"
                        + "CHARGE_CMD,CHECK_REQUIRED,ID,CMD_PRICE,"
                        + "MESSAGE_CHANNEL,FROM_DATE,TO_DATE,UPDATED_BY,"
                        + "PROMOTION_CHANNEL,CODE,FREE_DAY, confirm_Message) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                calStat = conn.prepareCall(sqlNew);
                calStat.setString(1, sub_service_name);
                calStat.setInt(2, status);
                calStat.setString(3, description);

                calStat.setString(4, type);
                calStat.setString(5, success_message);
                calStat.setString(6, failed_message);
                calStat.setInt(7, webservice_id);

                calStat.setString(8, charge_cmd);
                calStat.setInt(9, check_required);
                calStat.setString(10, id);
                calStat.setInt(11, cmd_price);

                calStat.setInt(12, message_channel);
                calStat.setDate(13, newFromDate);
                calStat.setDate(14, newToDate);
                calStat.setString(15, userToken.getUsername());

                calStat.setString(16, promotionChannel);
                calStat.setString(17, code);
                calStat.setInt(18, freeDay);
                calStat.setString(19, confirmMessage);
//                        
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.add_success");
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", code);
                insertActionLog(getClass().getName(), req.getLocalAddr(), "Insert PROMOTION_COMMAND: " + id);
            } catch (Exception ex) {
                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.fail");
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", code);
                VGenLog.error(ex.toString(), ex);
            } finally {
                DbCloseConn.closeQuietly("Mo_commandsManagerAction", "updateInsertUser()", conn, calStat, rs);
            }
        } else { //update
            try {
                String sqlEdit = "UPDATE PROMOTION_COMMAND set SUB_SERVICE_NAME=?,DATETIME=sysdate,STATUS=?,DESCRIPTION=?,TYPE=?,SUCCESS_MESSAGE=?,FAILED_MESSAGE=?,WEBSERVICE_ID=?,CHARGE_CMD=?,"
                        + "CHECK_REQUIRED=?,CMD_PRICE=?,MESSAGE_CHANNEL=?,FROM_DATE=?,TO_DATE=?,UPDATED_BY=?,PROMOTION_CHANNEL=?,CODE=?,FREE_DAY=?,confirm_Message=? WHERE ID = ? ";
                calStat = conn.prepareCall(sqlEdit);
                calStat.setString(1, sub_service_name);
                calStat.setInt(2, status);
                calStat.setString(3, description);
                calStat.setString(4, type);
                calStat.setString(5, success_message);
                calStat.setString(6, failed_message);
                calStat.setInt(7, webservice_id);
                calStat.setString(8, charge_cmd);
                calStat.setInt(9, check_required);
                calStat.setInt(10, cmd_price);
                calStat.setInt(11, message_channel);
                calStat.setDate(12, newFromDate);
                calStat.setDate(13, newToDate);
                calStat.setString(14, userToken.getUsername());
                calStat.setString(15, promotionChannel);
                calStat.setString(16, code);
                calStat.setInt(17, freeDay);
                calStat.setString(18, confirmMessage);
                calStat.setString(19, id);
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.success");
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
                insertActionLog(getClass().getName(), req.getLocalAddr(), "Update PROMOTION_COMMAND: " + id);
            } catch (Exception ex) {
                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", ex.toString());
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
                VGenLog.error(ex.toString(), ex);
            } finally {
                DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "updateInsertPromotion_commands()", conn, calStat, rs);
            }

        }
        //Load list
        searchPromotion_commands();
        return "Promotion_commandsManagerList";

    }

    public String deletePromotion_commands() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
            return ERROR;
        }

        String id = req.getParameter("id");
        ResultSet rs = null;
        Connection conn = null;
        CallableStatement calStat = null;

        if (!"".equals(id)) { //update
            try {
                if (conn == null || conn.isClosed()) {
                    conn = DataStore.getConnection();
                }
                String sqlDelete = new String("DELETE PROMOTION_COMMAND WHERE ID = ?");
                calStat = conn.prepareCall(sqlDelete);
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                calStat.setString(1, id);
                calStat.execute();
                req.setAttribute("Ret_updatePromotion_Commands", 1);
                req.setAttribute("Ret_updatePromotion_CommandsMsg", "ivr.alarm.delete.USSD.success");
                req.setAttribute("Ret_updatePromotion_CommandsMsg_param", id);
                insertActionLog(getClass().getName(), req.getRemoteAddr(), "Delete PROMOTION_COMMAND: " + id);
            } catch (Exception ex) {
                req.setAttribute("Ret_updatePromotion_Commands", 1);
                req.setAttribute("Ret_updatePromotion_CommandsMsg", "ivr.alarm.delete.USSD.fail");
                req.setAttribute("Ret_updatePromotion_CommandsMsg_param", id);
                VGenLog.error(ex.toString(), ex);
            } finally {
                DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "deleteId()", conn, calStat, rs);
            }
        } else { //new
            req.setAttribute("Ret_updatePromotion_Commands", 1);
            req.setAttribute("Ret_updatePromotion_CommandsMsg", "ivr.alarm.delete.USSD.fail");
            req.setAttribute("Ret_updatePromotion_CommandsMsg_param", id);
        }
        //Load list
        searchPromotion_commands();
        return "Promotion_commandsManagerList";

    }

    public List<PromotionCommandsBO> checkPromotion_commands(String sub_service_name) {
        Connection conn = null;
        List<PromotionCommandsBO> result = new ArrayList<PromotionCommandsBO>();
        CallableStatement calStat = null;
        ResultSet rs = null;
        try {
            conn = DataStore.getConnection();
            if (conn == null) {
                return null;
            }
            String sql = "select * FROM " + Constant.SCHEMA_NAME + ".promotion_command  WHERE status = 1 and sub_service_name=? ";

            calStat = conn.prepareCall(sql);
            calStat.setString(1, sub_service_name);
            calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
            rs = calStat.executeQuery();
            while (rs.next()) {
                PromotionCommandsBO promotionBO = new PromotionCommandsBO();
                promotionBO.setDatetime(rs.getTimestamp("DATETIME"));
                promotionBO.setStatus(rs.getInt("Status"));
                promotionBO.setDescription(rs.getString("Description"));
                promotionBO.setSubServiceName(rs.getString("SUB_SERVICE_NAME"));

                promotionBO.setType(rs.getString("Type"));
                promotionBO.setSuccessMessage(rs.getString("Success_message"));
                promotionBO.setFailMessage(rs.getString("Failed_message"));
                promotionBO.setWebserviceId(rs.getInt("Webservice_id"));

                promotionBO.setChargeCommand(rs.getString("Charge_cmd"));
                promotionBO.setCheckRequired(rs.getInt("Check_required"));
                promotionBO.setCmdPrice(rs.getInt("Cmd_price"));
                promotionBO.setMessageChannel(rs.getInt("Message_channel"));
                promotionBO.setId(rs.getString("ID"));

                promotionBO.setFromDate(rs.getDate("FROM_DATE"));
                promotionBO.setToDate(rs.getDate("TO_DATE"));
                promotionBO.setCode(rs.getString("CODE"));
                promotionBO.setFreeDay(rs.getInt("FREE_DAY"));
                result.add(promotionBO);
            }
        } catch (Exception ex) {
            VGenLog.error("Error: " + ex.getMessage(), ex);
        } finally {
            DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "checkId()", conn, calStat, rs);
            DbCloseConn.commitAndCloseQuietly(conn);
        }

        return result;
    }
}
