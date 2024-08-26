/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionInvocation;
import com.viettel.client.form.Mt_allManagerForm;
import com.viettel.database.bean.Users;
import com.viettel.database.dao.Mt_allBO;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 
 */
public class MtAllAction extends BaseDAOMDBAction {

    HttpServletRequest req;
    HttpSession reqSess;
    HttpServletResponse response;
    ActionInvocation actionInvocation;
    Mt_allManagerForm mt_allManagerForm = new Mt_allManagerForm();

    public Mt_allManagerForm getMt_allManagerForm() {
        return mt_allManagerForm;
    }

    public void setMt_allManagerForm(Mt_allManagerForm mt_allManagerForm) {
        this.mt_allManagerForm = mt_allManagerForm;
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
            if (reqSess == null || reqSess.isNew()) {
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

    public String prepare() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        String username = userToken.getUsername();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP) && !username.isEmpty()) {
//            searchServices("");
            return "prepare";
        } else {
            return ERROR;
        }
    }

    public String searchmt_all() throws SQLException, Exception {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        String datetime = "";
        String from_datetime = "";
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
        String msisdn = mt_allManagerForm.getMsisdn();
        msisdn = msisdn.trim();
//        msisdn = CountryCode.formatMobile(msisdn);
        
        if (mt_allManagerForm.getDatetime() != null) {
            datetime = DateTimeUtils.convertDateTimeToString2(mt_allManagerForm.getDatetime());
        }
        if (mt_allManagerForm.getFrom_datetime() != null) {
            from_datetime = DateTimeUtils.convertDateTimeToString2(mt_allManagerForm.getFrom_datetime());
        }

        try {
            List<Mt_allBO> lst = getWiners(msisdn, datetime, from_datetime) ;
            req.setAttribute("mt_allList", lst);
            reqSess.setAttribute("mt_allList", lst);
//            insertActionLog(getClass().getName(), req.getRemoteAddr(), "Get List mt_all");
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error(ex);
        } 
        return "mt_allManagerList";
    }
    
    public static List<Mt_allBO> getWiners(String msisdn, String fromDate, String toDate) {
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        List<Mt_allBO> lst  = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            String sqlq = "";
            ArrayList colParams = new ArrayList();
            sqlq = "select * from " + Constant.SCHEMA_NAME + ".V_WINNER where 1=1 ";


            if (toDate != null &&  fromDate != null 
                    && !toDate.isEmpty() && !fromDate.isEmpty()) {
                sqlq += " and win_day >=to_date(? ,'yyyy-MM-dd')"
                        + "  and win_day <= to_date(? ,'yyyy-MM-dd') + 1";
                colParams.add(fromDate);
                colParams.add(toDate);
            }
            
            if (msisdn != null && !msisdn.isEmpty()) {
                sqlq += " and msisdn = ? ";
                colParams.add(msisdn);
            }
            
            sqlq += " order by win_day desc, rank asc " ;

            calStat = conn.prepareCall(sqlq);
            if (colParams.size() > 0) {
                for (int i = 0; i < colParams.size(); i++) {
                    calStat.setString(i + 1, colParams.get(i).toString());
                }
            }
            rs = calStat.executeQuery();
            lst = new ArrayList<Mt_allBO>();
            while (rs.next()) {
                Mt_allBO abc = new Mt_allBO();
                String id                 = rs.getString("id") ;
                String isdn             = rs.getString("msisdn") ;
                int point                 = rs.getInt("point") ;
//                Timestamp  created         = rs.getTimestamp("created") ;
                Timestamp win_day         = rs.getTimestamp("win_day") ;
                String description        = rs.getString("description") ;
                String message_code       = rs.getString("message_code") ;
                String code               = rs.getString("code") ;
                String award_code         = rs.getString("award_code") ;
                String game_type          = rs.getString("game_type") ;
                int rank                  = rs.getInt("rank") ;
                String award_type         = rs.getString("award_type") ;
                int status                = rs.getInt("status") ;
                
                abc.setId(id);
                abc.setMsisdn(isdn);
                abc.setPoint(point);
                abc.setWin_day(win_day);
                abc.setDescription(description);
                abc.setMessage_code(message_code);
                abc.setCode(code);
                abc.setAward_code(award_code);
                abc.setGame_type(game_type);
                abc.setRank(rank);
                abc.setAward_type(award_type);
                abc.setStatus(status);
                
                lst.add(abc);
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            VGenLog.error(ex);
        } finally {
            DbCloseConn.closeQuietly("MtAllAction", "searchmt_all()", conn, calStat, rs);
        }
        
        return lst;
    }
   
    public String prepareAll_MTManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP)) {
            req.setAttribute("mt_allList", reqSess.getAttribute("mt_allList"));
            return "mt_allManagerList";
        } else {
            return ERROR;
        }
    }


    
    public String pagingMt_allManager() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
        String user_type = userToken.getUserType();
        if (!user_type.equalsIgnoreCase(Constant.USER_TYPE_CP)) {
            req.setAttribute("mt_allList", reqSess.getAttribute("mt_allList"));
            return "mt_allManagerList";
        } else {
            return ERROR;
        }
    }
    
    public String approveWinner() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
        String msisdn = req.getParameter("msisdn");
        String id = req.getParameter("id");
        String status = req.getParameter("status");
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        if (conn == null || conn.isClosed()) {
            conn = DataStore.getConnection();
        }
        try {
                String sqlEdit = "UPDATE LUCKY_WINNER set status = ? WHERE ID = ? and status != 2 ";
                calStat = conn.prepareCall(sqlEdit);
                calStat.setString(1, status);
                calStat.setString(2, id);
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.success");
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
//                insertActionLog(getClass().getName(), req.getLocalAddr(), "Update PROMOTION_COMMAND: " + id);
                mt_allManagerForm.setMsisdn(msisdn);
                searchmt_all();
            } catch (Exception ex) {
                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", ex.toString());
                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
                VGenLog.error(ex.toString(), ex);
            } finally {
                DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "updateInsertPromotion_commands()", conn, calStat, rs);
            }
       
        
        return "mt_allManagerList";
        
    }
    
    public String approveWinnerAll() throws SQLException {
        if (initCore().equals(Constant.STRING_SESSIONTIMEOUT)) {
            return Constant.STRING_SESSIONTIMEOUT;
        }
//        String msisdn = req.getParameter("msisdn");
        String ids = req.getParameter("ids");
        String status = req.getParameter("status");
        if (ids == null || ids.trim().isEmpty()) {
            return ERROR;
        }
        String [] idarr = ids.split(",");
//        Users userToken = (Users) reqSess.getAttribute(Constant.STRING_USERTOKEN);
//        String user_type = userToken.getUserType();
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        if (conn == null || conn.isClosed()) {
            conn = DataStore.getConnection();
        }
        try {
                String sqlEdit = "UPDATE LUCKY_WINNER set status = ? WHERE  status != 2  and ID in (" ;
                for (int i = 0; i < idarr.length; i++) {
                    String id = idarr[i];
                    if (i == 0) {
                        sqlEdit += "'" + id + "'";
                    } else {
                        sqlEdit += ",'" + id + "'" ;
                    }
                }
                sqlEdit += ")";
                
                calStat = conn.prepareCall(sqlEdit);
                calStat.setString(1, status);
//                calStat.setString(2, ids);
                calStat.setQueryTimeout(Constant.QUERY_TIMEOUT);
                rs = calStat.executeQuery();

                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", "promotion.success");
//                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
//                insertActionLog(getClass().getName(), req.getLocalAddr(), "Update PROMOTION_COMMAND: " + id);
//                mt_allManagerForm.setMsisdn(msisdn);
//                searchmt_all();
            } catch (Exception ex) {
                req.setAttribute("Ret_updatePromotion_commands", 1);
                req.setAttribute("Ret_updatePromotion_commandsMsg", ex.toString());
//                req.setAttribute("Ret_updatePromotion_commandsMsg_param", id);
                VGenLog.error(ex.toString(), ex);
            } finally {
                DbCloseConn.closeQuietly("Promotion_commandsManagerAction", "updateInsertPromotion_commands()", conn, calStat, rs);
            }
       
        
        return "mt_allManagerList";
        
    }
}
