/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.ActionLog;
import com.viettel.luckydraw.bo.CallWsTransactionLog;
import com.viettel.luckydraw.bo.LuckyGameGift;
import com.viettel.luckydraw.bo.LuckyGamePlayTurn;
import com.viettel.luckydraw.bo.LukyGameInvitedFriendHis;
import com.viettel.luckydraw.bo.LuckyGameRule;
import com.viettel.luckydraw.bo.LuckyGiftHistory;
import com.viettel.luckydraw.bo.LuckyHistoryBuyMore;
import com.viettel.luckydraw.bo.LuckyTopGift;
import com.viettel.luckydraw.bo.SubInfo;
import com.viettel.luckydraw.bo.Webservice;
import com.viettel.luckydraw.util.AppContext;
import com.viettel.luckydraw.util.Config;
import com.viettel.luckydraw.util.Constant;
import com.viettel.luckydraw.util.SmsGwUtils;
import com.viettel.luckydraw.util.WebServiceUtil;
import com.viettel.luckydraw.ws.MpsWsUtils;
import com.viettel.luckydraw.ws.form.MpsResponse;
import com.viettel.luckydraw.ws.form.ResponseForm;
import com.viettel.luckydraw.ws.form.SoapWSResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.StringUtils;

/**
 *
 * @author manhnv23
 */
public class LuckyGameDAO {
    
    private static LuckyGameDAO instance;
    public static final String SHORTDATE_FORMAT = "yyyyMMdd";
    DateFormat df = new SimpleDateFormat(SHORTDATE_FORMAT);
    private DataSource dataSource;
    public static final Logger log = Logger.getLogger(LuckyGameDAO.class);
    private static HashMap<String, LuckyGameGift> giftList;
    private static String O_C_TRANS_LB = "O_C_TRANS";
    private static String MSISDN_LB = "p_msisdn";
    private static String RESPONSE_STATUS = "O_RES_STATUS";
    
    private static String GROUPTYPE_LB = "p_groupType";
    private static final String P_LANG = "p_lang";
    private static final String P_SUB_TYPE = "p_subType";
    private static String LANGUAGE_LB = P_LANG;
    private static final String P_PLAY_TYPE = "p_playType";
    private static final String P_OTP = "p_otp";
    private static final String P_TOKEN = "p_token";
    private static final String P_REFRESH_TOKEN = "p_refresh_token";
    private static final String SMS_OTP_CODE = "@OTP_CODE@"; 
    
    private static final String SQL_CODE = "sql_code";
        
    public static final String REQUEST_ID = "requestid";
    
        
    private static final String P_ERROR_DESC = "p_errorDesc";
    private static final String P_ERROR_CODE = "p_errorCode";
    private static final String P_PLAY_ID = "p_playId";
    private static final String PKG_GAME_TURN = "PKG_GAME_TURN";
    private static final String P_PLAY_STATUS = "p_playStatus";
        private static final String P_DATE = "p_date";
    public synchronized static LuckyGameDAO getInstance() {
        if (instance == null) {
            try {
                instance = (LuckyGameDAO) AppContext.getInstance().getContext().getBean("LuckyGameDAO");
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return instance;
    }
    
    public List<LuckyGamePlayTurn> getLuckyGamePlayTurnFree(String msisdn, String playType, String status, String groupType) {
        long startTime = System.currentTimeMillis();
        log.info("getLuckyGamePlayTurnFree: " + msisdn + "-" + playType + "-" + status + "-" + groupType);
        List<LuckyGamePlayTurn> res = new ArrayList<>();
        String procedureName = "DEFAULT".equals(playType) ?  "lucky_get_turn_free_default":"lucky_get_turn_free";
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName(procedureName)
                    .returningResultSet(O_C_TRANS_LB, new LuckyPlayTurnRowMapper());
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(GROUPTYPE_LB, groupType)
                    .addValue(P_PLAY_TYPE, playType);
                 //   .addValue(P_PLAY_STATUS, status);
            Map<String, Object> out = jdbcCall.execute(in);
            res = (List<LuckyGamePlayTurn>) out.get(O_C_TRANS_LB);
            
        } catch (Exception e) {
            log.error("getLuckyGamePlayTurnFree" + e.getMessage(), e);
        } finally {
            log.info("Time getMiniGame2018PlayTurn:" + (System.currentTimeMillis()- startTime) + " ms");
        }
        return res;
    }

    
    public List<LuckyGamePlayTurn> getLuckyGamePlayTurnPremium(String msisdn, String playType, String status, String groupType) {
        long startTime = System.currentTimeMillis();
        log.info("getLuckyGamePlayTurnPremium: " + msisdn + "-" + playType + "-" + status + "-" + groupType);
        List<LuckyGamePlayTurn> res = new ArrayList<>();
        String procedureName = "DEFAULT".equals(playType) ?  "lucky_get_turn_premium_default":"lucky_get_turn_premium";
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName(procedureName)
                    .returningResultSet(O_C_TRANS_LB, new LuckyPlayTurnRowMapper());
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(GROUPTYPE_LB, groupType)
                    .addValue(P_PLAY_TYPE, playType);
                //    .addValue(P_PLAY_STATUS, status);
            Map<String, Object> out = jdbcCall.execute(in);
            res = (List<LuckyGamePlayTurn>) out.get(O_C_TRANS_LB);
            
        } catch (Exception e) {
            log.error("getLuckyGamePlayTurnPremium" + e.getMessage(), e);
        } finally {
            log.info("Time getLuckyGamePlayTurnPremium:" + (System.currentTimeMillis()- startTime) + " ms");
        }
        return res;
    }

    
    
    
    public int addPlayTurn(String msisdn, String playType, String groupType) {
        log.info("---------------------------");
        long startTime = System.currentTimeMillis();
        BigDecimal res = null;
        try {
         SimpleJdbcCall   jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("lucky_game_add_turn_free");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(P_PLAY_ID, UUID.randomUUID().toString().replace("-", ""))
                    .addValue(MSISDN_LB, msisdn)
                    .addValue("p_playDate", df.format(new Date()))
                    .addValue(P_PLAY_TYPE, playType)
                    .addValue(GROUPTYPE_LB, groupType);
            Map<String, Object> out = jdbcCall.execute(in);
            res = (BigDecimal) out.get(RESPONSE_STATUS);
            log.info("Free add turn RES:" + res);
        } catch (Exception e) {
            log.error("Error when addPlayTurn Free " + e.getMessage(), e);
        } finally {
            log.info("Time addPlayTurn Free: " + (System.currentTimeMillis() - startTime) + " ms");
        }
        return (res == null ? 0 : res.intValue());
    }
    public int addPlayTurnPremium(String msisdn, String playType, String groupType) {
        log.info("---------------------------");
        long startTime = System.currentTimeMillis();
        BigDecimal res = null;
        try {
         SimpleJdbcCall   jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("LUCKY_GAME_ADD_TURN_SUB");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(P_PLAY_ID, UUID.randomUUID().toString().replace("-", ""))
                    .addValue(MSISDN_LB, msisdn)
                    .addValue("p_playDate", df.format(new Date()))
                    .addValue(P_PLAY_TYPE, playType)
                    .addValue(GROUPTYPE_LB, groupType);
            Map<String, Object> out = jdbcCall.execute(in);
            res = (BigDecimal) out.get(RESPONSE_STATUS);
            log.info("addPlayTurnPremium Res:" + res);
        } catch (Exception e) {
            log.error("Error when addPlayTurnPremium " + e.getMessage(), e);
        } finally {
            log.info("Time addPlayTurnPremium: " + (System.currentTimeMillis() - startTime) + " ms");
        }
        return (res == null ? 0 : res.intValue());
    }
    
    public synchronized ResponseForm generateAndAddGift(String msisdn, String subType, String groupType, String lang, String isLaptopProduct, String procedureName, int checkUmoney) {
    //    log.info("CHECK_UMONEY:" + checkUmoney);
        long startTime = System.currentTimeMillis();
        ResponseForm res = new ResponseForm();
        res.setIsdn(msisdn);
        
    //    System.out.println("procedureName: "+ procedureName);
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName(procedureName);
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(P_SUB_TYPE, subType)
                    .addValue(GROUPTYPE_LB, groupType)
                    .addValue(P_LANG, lang)
                    .addValue("p_isLT", isLaptopProduct);
               //     .addValue("checkUmoney_", checkUmoney);
            Map<String, Object> out = jdbcCall.execute(in);
            String kq = (String) out.get(RESPONSE_STATUS);
            
            log.info("kq =========> " + kq);
            
            if ("SUCCESS".equals(kq)) {
                res.setGiftCode((String) out.get("GIFT_CODE"));
                res.setGiftDesc((String) out.get("GIFT_DESC"));
                res.setGiftAccId((String) out.get("GIFT_ACC_ID"));
                res.setGiftValue((String) out.get("GIFT_VALUE"));
                res.setPlayId((String) out.get("PLAY_ID"));
                res.setGiftType((String) out.get("GIFT_TYPE"));
                res.setGiftMsg((String) out.get("GIFT_MSG"));
                
            }
            res.setResult(kq);
        } catch (Exception e) {
            log.error("Error when generateAndAddGift" + e.getMessage(), e);
        } finally {
            log.info("Time generateAndAddGift:" + (System.currentTimeMillis() - startTime) + " ms");
            log.info("Result generateAndAddGift for " + msisdn + " : "+ res.toString());
        }
        return res;
    }

    

    
    
    public List<LuckyGamePlayTurn> getGiftListHistory(String msisdn, String groupType,String procedureName, String lang, int pageNumber, int pageSize) {
        long startTime = System.currentTimeMillis();
        List<LuckyGamePlayTurn> res = new ArrayList<>();
               
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName(procedureName)
                    .returningResultSet(O_C_TRANS_LB, new LuckyPlayTurnRowMapper())
                    ;
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(GROUPTYPE_LB, groupType)
                    .addValue(P_LANG, lang)
                    .addValue("p_pageNumber", pageNumber)
                    .addValue("p_pageSize", pageSize)  ;
            Map<String, Object> out = jdbcCall.execute(in);
            res = (List<LuckyGamePlayTurn>) out.get(O_C_TRANS_LB);
        } catch (Exception e) {
            log.error("Error when getGiftList" + e.getMessage(), e);
        } finally {
            log.info("Time getGiftList:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return res;
    }
    
    
    public List<LuckyGiftHistory> getGiftListHistoryLetter(String msisdn, String groupType,String procedureName, String lang) {
        long startTime = System.currentTimeMillis();
        List<LuckyGiftHistory> res = new ArrayList<>();
               
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName(procedureName)
                    .returningResultSet(O_C_TRANS_LB, new LuckyGiftHistoryRowMapper())
                    ;
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(GROUPTYPE_LB, groupType)
                    .addValue(P_LANG, lang)  ;
            Map<String, Object> out = jdbcCall.execute(in);
            res = (List<LuckyGiftHistory>) out.get(O_C_TRANS_LB);
        } catch (Exception e) {
            log.error("Error when getGiftListHistoryLetter" + e.getMessage(), e);
        } finally {
            log.info("Time getGiftListHistoryLetter:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return res;
    }
    
      
    public List<LuckyHistoryBuyMore> getGiftListHistoryBuyMore(String msisdn, String procedureName, String lang) {
        long startTime = System.currentTimeMillis();
        List<LuckyHistoryBuyMore> res = new ArrayList<>();
               
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName(procedureName)
                    .returningResultSet(O_C_TRANS_LB, new LuckyHistoryBuyMoreRowMapper())
                    ;
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(P_LANG, lang)  ;
            Map<String, Object> out = jdbcCall.execute(in);
            res = (List<LuckyHistoryBuyMore>) out.get(O_C_TRANS_LB);
        } catch (Exception e) {
            log.error("Error when getGiftListHistoryBuyMore" + e.getMessage(), e);
        } finally {
            log.info("Time getGiftListHistoryBuyMore:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return res;
    }
    
    public List<LuckyTopGift> getTopGift() {
        long startTime = System.currentTimeMillis();
        List<LuckyTopGift> res = new ArrayList<>();
               
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("lucky_get_top_prize")
                    .returningResultSet(O_C_TRANS_LB, new LuckyTopGiftRowMapper());

            Map<String, Object> out = jdbcCall.execute();
            res = (List<LuckyTopGift>) out.get(O_C_TRANS_LB);
        } catch (Exception e) {
            log.error("Error when getTopGift" + e.getMessage(), e);
        } finally {
            log.info("Time getTopGift:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return res;
    }
    
    
    /**
     * 
     * @param playId
     * @param errorCode
     * @param errorDesc
     * @return 
     */
    
    public ResponseForm updatePlayTurn(String playId, String errorCode, String errorDesc) {
       ResponseForm res = new ResponseForm();
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("LUCKY_UPDATE_TURN_FREE");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(P_PLAY_ID, playId)
                    .addValue(P_ERROR_CODE, errorCode)
                    .addValue(P_ERROR_DESC, errorDesc);
           Map<String, Object> out = jdbcCall.execute(in);
           BigDecimal retVal = (BigDecimal) out.get(RESPONSE_STATUS);
            
            if (retVal!=null && retVal.intValue() > 0 ) {
                res.setGiftCode((String) out.get("GIFT_CODE"));
                res.setGiftDesc((String) out.get("GIFT_DESC"));
                res.setGiftValue((String) out.get("GIFT_VALUE"));
                res.setPlayId((String) out.get("PLAY_ID"));
                res.setGiftType((String) out.get("GIFT_TYPE"));
                res.setGiftMsg((String) out.get("GIFT_MSG"));      
                res.setIsdn((String) out.get("O_MSISDN"));      
                res.setResult(String.valueOf(retVal.intValue()));
            }
                      
        } catch (Exception e) {
            log.error("Error when updatePlayTurn" + e.getMessage(), e);
        } finally {
            log.info("Result updatePlayTurn for " + playId + ":" + res);
        }
        return res;
    }
    
    /**
     * 
     * @param playId
     * @param errorCode
     * @param errorDesc
     * @return 
     */

    public ResponseForm updatePlayTurnPremium(String playId, String errorCode, String errorDesc) {
       
        ResponseForm res = new ResponseForm();
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("LUCKY_UPDATE_TURN_PREMIUM");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(P_PLAY_ID, playId)
                    .addValue(P_ERROR_CODE, errorCode)
                    .addValue(P_ERROR_DESC, errorDesc);
            Map<String, Object> out = jdbcCall.execute(in);
           BigDecimal retVal = (BigDecimal) out.get(RESPONSE_STATUS);
            

            if (retVal!=null && retVal.intValue() > 0 ) {
                res.setGiftCode((String) out.get("GIFT_CODE"));
                res.setGiftDesc((String) out.get("GIFT_DESC"));
                res.setGiftValue((String) out.get("GIFT_VALUE"));
                res.setPlayId((String) out.get("PLAY_ID"));
                res.setGiftType((String) out.get("GIFT_TYPE"));
                res.setGiftMsg((String) out.get("GIFT_MSG"));
                res.setIsdn((String) out.get("O_MSISDN"));                   
                res.setResult(String.valueOf(retVal.intValue()));                
            }
                      
        } catch (Exception e) {
            log.error("Error when updatePlayTurnPremium" + e.getMessage(), e);
        } finally {
            log.info("Result updatePlayTurnPremium for " + playId + ":" + res);
        }
        return res;
    }
    
    
    
    public void insertActionLog(List<ActionLog> actionLogs) {
        long startTime = System.currentTimeMillis();
        try {
            String SQL = "insert into MINI_GAME_2018_ACTION_LOG(request, response, isdn, insert_time, duration, method) values(:request, :response, :isdn, sysdate, :duration, :method)";
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(actionLogs.toArray());
            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
            
            int[] updateCounts = jdbcTemplateObject.batchUpdate(SQL, batch);
            log.info("insertActionLog batch size:"  + updateCounts.length);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally{
            log.info("Time insertLog:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
    public synchronized void  insertLuckyPlayTurn(String msisdn,String playType,String gameCode ) {
        long startTime = System.currentTimeMillis();
        try {
            String SQL = "   INSERT  /*+ append */ INTO LUCKY_PLAY_TURN_SUB " +
                            "     ( PLAY_ID," +
                            "       MSISDN," +
                            "       PLAY_REQUEST_DATE," +
                            "       PLAY_TYPE, " +
                            "       GROUP_TYPE, " +
                            "       PLAY_STATUS " +
                            "     ) " +
                            "     VALUES\n" +
                            "     (" +
                            "       SYS_GUID()," +
                            "       :isdn, " +
                            "        sysdate, " +
                            "       :playType," +
                            "       :groupType, " +
                            "       :playStatus " +
                            "     ) log errors into LUCKY_PLAY_TURN_SUB_ERR ('INSERT APPEND') reject limit unlimited " ;
            
             NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
             SqlParameterSource namedParameters = new MapSqlParameterSource()
                     .addValue("isdn", msisdn)
                     .addValue("playType", playType)
                     .addValue("groupType", gameCode)
                     .addValue("playStatus", "0")
                       ;
             int rowInsert = jdbcTemplateObject.update(SQL, namedParameters); 
             
             log.info("rowInsert: "+ rowInsert +" msisdn: " + msisdn);
             
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally{
            log.info("Time insertLuckyPlayTurn:"  + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
    
        public List<LuckyGamePlayTurn> getPlayTurn(int limit) {
        long startTime = System.currentTimeMillis();
         List<LuckyGamePlayTurn> list = new ArrayList<LuckyGamePlayTurn>();
        try {
//            String SQL = "SELECT msisdn, group_type, gift_date, gift_code, gift_desc, gift_order, gift_value, gift_type, play_id, fee , lang  FROM mini_game_2018_play_turn WHERE play_status=0 and gift_code IS NOT NULL AND gift_type NOT IN (:giftType) AND ROWNUM<=:limit";
            String SQL = "SELECT msisdn, group_type, gift_date, gift_code, gift_desc, gift_order, gift_value, gift_type, play_id, fee , lang  FROM mini_game_2018_play_turn WHERE PLAY_REQUEST_DATE >= trunc(sysdate)-1 AND play_status=0 and gift_code IS NOT NULL AND gift_type<>'LUCKY' AND gift_date<=sysdate-:delayTime/(24*60*60) AND ROWNUM<=:limit";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("limit",limit)
                    .addValue("delayTime", Config.getInstance().getDelayTime());
            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
           list = jdbcTemplateObject.query(SQL, parameters, new LuckyPlayTurnRowMapper());
         
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally{
            log.info("Time getPlayTurn:" + (System.currentTimeMillis() - startTime) + " ms,  records:" + list.size());
        }
          return list;
    }
        
    public void updatePlayTurn( String playId, String msisdn,  String errorCode, String desc, String playStatus){
         long startTime = System.currentTimeMillis();
        try {
            String UPDATE_QUERY = "UPDATE mini_game_2018_play_turn SET play_status=:play_status, error_code=:error_code, error_desc=:error_desc, update_time=sysdate WHERE msisdn=:msisdn AND play_id=:play_id ";
            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
             SqlParameterSource namedParameters = new MapSqlParameterSource()
                     .addValue("play_status", playStatus)
                     .addValue("error_code", errorCode)
                     .addValue("error_desc", desc)
                     .addValue("msisdn", msisdn)
                     .addValue("play_id", playId);
             int updateStatus = jdbcTemplateObject.update(UPDATE_QUERY, namedParameters); 
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally{
            log.info("Time updatePlayTurn:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }    
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    

    public String getRule(String lang, String ruleCode) {
        long startTime = System.currentTimeMillis();
        String rule = null;
        List<LuckyGameRule> res = new ArrayList<LuckyGameRule>();
        try {
          SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("LUCKY_GET_RULE")
                    .returningResultSet(O_C_TRANS_LB, new LuckyGameRuleRowMapper());
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(LANGUAGE_LB, lang)
                    .addValue("p_rule_code", ruleCode);
                   
            Map<String, Object> out = jdbcCall.execute(in);
            res = (List<LuckyGameRule>) out.get(O_C_TRANS_LB);
            if(!res.isEmpty()){
                rule = res.get(0).getDescription();
                rule = !rule.isEmpty() ? rule.replaceAll("\r", "").replaceAll("\n", "") : "";           
            }
        } catch (Exception e) {
            log.error("getRule" + e.getMessage(), e);
        } finally {
            log.info("Time getRule:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return rule;
    }
    
    
    public void insertLuckyPrizeProcess(  String msisdn,  long value, long addExpire, String balanceId, String giftType){
         long startTime = System.currentTimeMillis();
        try {
            String QUERY = "INSERT INTO lukcy_prize_process (ID,MSISDN,ADD_VALUE,ADD_TIME_EXPIRE,BALANCE_ID,CREATE_TIME,APP_NAME,RETRY_NUM,MESSAGE,CHANNEL,STATUS,SCAN_DATETIME,RESET_BALANCE, GIFT_TYPE) " +
                                    "VALUES(LUKCY_PRIZE_PROCESS_SEQ.nextval,:msisdn,:value,:addExpire,:balanceId,sysdate,'iLuckyApp',0,NULL,NULL,NULL,NULL,NULL, :giftType)";
            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
             SqlParameterSource namedParameters = new MapSqlParameterSource()
                     .addValue("msisdn", msisdn)
                     .addValue("value", value)
                     .addValue("addExpire", addExpire)
                     .addValue("balanceId", balanceId)
                     .addValue("giftType", giftType);
             int updateStatus = jdbcTemplateObject.update(QUERY, namedParameters); 
             
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
        }finally{
            log.info("Time insertPromotion:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }


   public int addPlayTurnBuyMore(String msisdn,int addNum, int isPremium) {
        BigDecimal res = null;
        try {
           SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("ADD_TURN_BUY_MORE");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue("p_addNum", addNum)
                    .addValue("addPlayTurnBuyMore", isPremium);
                 
            Map<String, Object> out = jdbcCall.execute(in);
            res = (BigDecimal) out.get(RESPONSE_STATUS);
            log.info("addPlayTurnBuyMore res:" + res);
        } catch (Exception e) {
            log.error("Error addPlayTurnBuyMore for buy more turn:" + e.getMessage(), e);
        } finally {
            log.info("addPlayTurnBuyMore for buy more turn: " + msisdn + " >> Result : " + res);
        }
        return (res == null ? 0 : res.intValue());
    }
   
   
    public String getOTP(String lang, String isdn) {
        int otpLength = Config.getInstance().getOtpLength();
        String otpValue = RandomStringUtils.randomNumeric(otpLength);
        if (checkLimitgetOtpPerDay(isdn)) {
            log.info("Limit get OTP over per day:" + isdn);
            return Constant.ERROR_CODE_GET_OVER_OTP_5;
        }

        String smsContent = Config.getInstance().getMessage(lang, "SMS_OTP_LOGIN");
        if (smsContent != null) {
            smsContent = smsContent.replace(SMS_OTP_CODE, otpValue);
            log.info("SMS Content >>>>>>>>>>> " + smsContent);
        }

        int sentOtp = SmsGwUtils.getInstance().sendMt(isdn, smsContent);

//            ResultSendOtp resultSendOtp = new ResultSendOtp("0");
        if (Constant.ERROR_OK == sentOtp) {

            log.info("Sent OTP OK to isdn: " + isdn + "_" + otpValue);
            //  log.info("Sent OTP value : " + otpValue);

            addOtp(isdn, otpValue);
            // Save log get OTP
      //      insertSmsGwInsMt(isdn, Config.getInstance().getOtpSender(), Config.getInstance().getOtpSender(), otpValue, Config.getInstance().getOtpUser());

            return Constant.ERROR_CODE_0;

        } else {
            log.info("Sent OTP Error");
            return Constant.ERROR_CODE_1;
        }
    }
    
    
       private boolean checkLimitgetOtpPerDay(String msisdn) {
        long startTime = System.currentTimeMillis();
        boolean isOver = false;

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_USER_OTP")
                    .withFunctionName("GET_TOTAL_GET_OTP_PER_DAY")
                    .withReturnValue();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn);
            //  .addValue(RULE_CODE, ruleCode);
            int retVal = jdbcCall.executeFunction(BigDecimal.class, in).intValue();

            if (retVal > 0) {
                isOver = true;
            }

        } catch (Exception e) {
            log.error("PKG_USER_OTP.GET_TOTAL_GET_OTP_PER_DAY" + e.getMessage(), e);
        } finally {
            log.info("Time checkLimitgetOtpPerDay:" + (System.currentTimeMillis() - startTime) + " ms");
        }

        return isOver;
    }
    
    public Long getTotalAccumulationMoney(String msisdn) {
        long startTime = System.currentTimeMillis();
        Long totalMoney = 0L ;

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withFunctionName("GET_ACCUMULATION_MONEY")
                    .withReturnValue();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn);
            totalMoney = jdbcCall.executeFunction(BigDecimal.class, in).longValue();
           
        } catch (Exception e) {
            log.error("GET_ACCUMULATION_MONEY" + e.getMessage(), e);
        } finally {
            log.info("Time getTotalAccumulationMoney:" + (System.currentTimeMillis() - startTime) + " ms");
        }

        return totalMoney;
    }

    public boolean verifyOtp(String msisdn, String otp) {
        long startTime = System.currentTimeMillis();
        boolean isValid = false;

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_USER_OTP")
                    .withFunctionName("VERIFY_OTP")
                    .withReturnValue();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(P_OTP, otp);
            //  .addValue(RULE_CODE, ruleCode);
            int retVal = jdbcCall.executeFunction(BigDecimal.class, in).intValue();

            if (retVal > 0) {
                isValid = true;
            }

        } catch (Exception e) {
            log.error("PKG_USER_OTP.VERIFY_OTP" + e.getMessage(), e);
        } finally {
            log.info("Time verifyOtp:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return isValid;
    }
    
    
      public int addOtp(String isdn, String otp) {
        long startTime = System.currentTimeMillis();
        BigDecimal res = null;
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_USER_OTP")
                    .withProcedureName("ADD_USER_OTP");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, isdn)
                    .addValue(P_OTP, otp);

            Map<String, Object> out = jdbcCall.execute(in);
            res = (BigDecimal) out.get(RESPONSE_STATUS);
            log.info("RES:" + res);
        } catch (Exception e) {
            log.error("Error when addOtp " + e.getMessage(), e);
        } finally {
            log.info("Time addOtp:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return (res == null ? 0 : res.intValue());
    }

    public int addUserToken(String isdn, String token, String refreshToken) {
        long startTime = System.currentTimeMillis();
        BigDecimal res = null;
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_USER_TOKEN")
                    .withProcedureName("ADD_USER_TOKEN");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, isdn)
                    .addValue(P_TOKEN, token)
                    .addValue(P_REFRESH_TOKEN, refreshToken);

            Map<String, Object> out = jdbcCall.execute(in);
            res = (BigDecimal) out.get(RESPONSE_STATUS);
            log.info("RES:" + res);
        } catch (Exception e) {
            log.error("Error when addUserToken " + e.getMessage(), e);
        } finally {
            log.info("Time addUserToken:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return (res == null ? 0 : res.intValue());
    }
    
    public String getUserToken(String isdn) {
        long startTime = System.currentTimeMillis();
        String res = null;
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_USER_TOKEN")
               //     .withProcedureName("GET_USER_TOKEN")
                    .withFunctionName("GET_USER_TOKEN")
                    .withReturnValue(); 
            
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, isdn); 

        //    Map<String, Object> out = jdbcCall.executeFunction(in);
            
                res = jdbcCall.executeFunction(String.class, in);            
            
        //    res = (String) out.get(RESPONSE_STATUS);
            log.info("RES:" + res);
        } catch (Exception e) {
            log.error("Error when getUserToken " + e.getMessage(), e);
        } finally {
            log.info("Time getUserToken:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return res;
    }
    
    
    
     public void insertSmsGwInsMt(String msisdn, String shortCode, String alias, String content, String username) {
        long startTime = System.currentTimeMillis();
        try {

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("PR_SMSGW_INS_MT");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("P_MSISDN", msisdn)
                    .addValue("P_SHORT_CODE", shortCode)
                    .addValue("P_ALIAS", alias)
                    .addValue("P_CONTENTS", content)
                    .addValue("P_USER", username)
                    .addValue("P_DATETIME", new Date());
            jdbcCall.execute(in);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time insertSmsGwInsMt:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }

  public boolean verifyUserToken(String msisdn, String token) {
        long startTime = System.currentTimeMillis();
        boolean isValid = false;

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_USER_TOKEN")
                    .withFunctionName("VERIFY_TOKEN")
                    .withReturnValue();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn)
                    .addValue(P_TOKEN, token);

            int retVal = jdbcCall.executeFunction(BigDecimal.class, in).intValue();

            if (retVal > 0) {
                isValid = true;
            }

        } catch (Exception e) {
            log.error("PKG_USER_TOKEN.VERIFY_TOKEN" + e.getMessage(), e);
        } finally {
            log.info("Time verifyUserToken:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return isValid;
    }
  
  
    public boolean checkSubRegister(String msisdn) {
        long startTime = System.currentTimeMillis();
        boolean isReg = false;
        try {
            Webservice wsObjectBean = this.getWebserviceByName(Constant.WS_CHECK_SUB_REG);
            if (wsObjectBean == null) {
                // Web-service not found, check database
                log.error("The Webserive " + Constant.WS_CHECK_SUB_REG + " can't be found for this check Sub service. Please, check the database.");

                return isReg;
            }

            WebServiceUtil wsUtil = new WebServiceUtil(wsObjectBean);
            if (StringUtils.isEmpty(msisdn)) {
                log.error("isdn invalid");
                //    bean.setErrorCode(Constants.ERROR_CODE_1);
                return isReg;
            }
            
            LinkedHashMap<String, Object> params = new LinkedHashMap<>();
            params.put(SQL_CODE, Constant.WS_CHECK_SUB_REG);
            params.put("msisdn", msisdn);
            params.put(REQUEST_ID, UUID.randomUUID().toString());
            SoapWSResponse wsResponseMps = wsUtil.callWebService(params, false);
            // parser result
            String returnValue = "";
            if (wsResponseMps != null) {

                String error = wsResponseMps.getErrCode();
                if (error != null && Constant.ERROR_CODE_0.equalsIgnoreCase(error)) {

                    try {
                        if (!StringUtils.isEmpty(wsResponseMps)) {
                            String sqlReturnRs = wsResponseMps.getElementXML(wsObjectBean.getXpathExtension02());

                            log.info(" >>>>>>>>>>>>>> checkSubRegister sqlReturnRs" + sqlReturnRs);

                            if (!sqlReturnRs.contains(Constant.WS_CHECK_SUB_REG.toLowerCase())) {
                                log.info("msidn not registered");
                           //     insertCallWsLog(wsUtil.getTransLog(), msisdn);
                                return false;
                            }
                            returnValue = wsResponseMps.getTextContent(wsObjectBean.getXpathExtension01());

                            isReg = Constant.REG_STATUS_1.equals(returnValue) || Constant.REG_STATUS_2.equals(returnValue);
                        }

                    } catch (TransformerException | XPathExpressionException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            } else {
                log.error(" =======================> Call MPS Check sub register error");
            }
        //    insertCallWsLog(wsUtil.getTransLog(), msisdn);

        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time checkSubRegister:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return isReg;
    }
    
    
    public SubInfo checkSubStatus(String msisdn) {
        long startTime = System.currentTimeMillis();
    
        SubInfo subInfo = null;
        try {
            Webservice wsObjectBean = this.getWebserviceByName(Constant.WS_CHECK_SUB_REG);
            if (wsObjectBean == null) {
                // Web-service not found, check database
                log.error("The Webserive " + Constant.WS_CHECK_SUB_REG + " can't be found for this check Sub service. Please, check the database.");

                return null;
            }

            WebServiceUtil wsUtil = new WebServiceUtil(wsObjectBean);
            if (StringUtils.isEmpty(msisdn)) {
                log.error("isdn invalid");
                //    bean.setErrorCode(Constants.ERROR_CODE_1);
                return null;
            }
            
            LinkedHashMap<String, Object> params = new LinkedHashMap<>();
            params.put(SQL_CODE, Constant.WS_CHECK_SUB_REG);
            params.put("msisdn", msisdn);
            params.put(REQUEST_ID, UUID.randomUUID().toString());
            SoapWSResponse wsResponseMps = wsUtil.callWebService(params, false);
            // parser result
            String returnValue = "";
            String chargeStatus = "";
            String subServiceName = "";
            if (wsResponseMps != null) {

                String error = wsResponseMps.getErrCode();
                if (error != null && Constant.ERROR_CODE_0.equalsIgnoreCase(error)) {

                    try {
                        if (!StringUtils.isEmpty(wsResponseMps)) {
                            String sqlReturnRs = wsResponseMps.getElementXML(wsObjectBean.getXpathExtension02());

                            log.info(" >>>>>>>>>>>>>> checkSubRegister sqlReturnRs" + sqlReturnRs);

                            if (!sqlReturnRs.contains(Constant.WS_CHECK_SUB_REG.toLowerCase())) {
                                log.info("msidn not registered");
                                insertCallWsLog(wsUtil.getTransLog(), msisdn);
                                return null;
                            }
                            
                            returnValue = wsResponseMps.getTextContent(wsObjectBean.getXpathExtension01());
                            chargeStatus = wsResponseMps.getTextContent(wsObjectBean.getXpathExtension03());
                            subServiceName = wsResponseMps.getTextContent("/Envelope/Body/sqlResponse/sqlReturn/lucky_status/sub_service_name");
                                                       
                            subInfo = new SubInfo();
                            subInfo.setRegStatus(returnValue);
                            subInfo.setChargeStatus(chargeStatus);
                            subInfo.setSubServiceName(subServiceName);                            
                                                                                                         
                        }

                    } catch (TransformerException | XPathExpressionException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            } else {
                log.error(" =======================> Call MPS Check sub register error");
            }
       //     insertCallWsLog(wsUtil.getTransLog(), msisdn);

        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time checkSubStatus:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return subInfo;
    }
    
    
     public void insertCallWsLog(CallWsTransactionLog callWsLog, String msisdn) {
        long startTime = System.currentTimeMillis();
        try {
            String SQL = "insert into LUCKY_GAME_TRANSACTION_LOG(ID, request, response, msisdn, insert_time, duration, ws_name) values(sys_guid(), :request, :response, :msisdn, sysdate, :duration, :method)";

            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("msisdn", msisdn)
                    .addValue("request", callWsLog.getRequest())
                    .addValue("response", callWsLog.getResponse())
                    .addValue("duration", callWsLog.getDuration())
                    .addValue("method", callWsLog.getCommand());
            int updateStatus = jdbcTemplateObject.update(SQL, namedParameters);
            log.info(">>>>>>>>>>>>>>>> insertCallWsLog :" + updateStatus);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time insertCallWsLog:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
     
    public void updateAddTopGiftStatus(LuckyTopGift topGift) {
        long startTime = System.currentTimeMillis();
        try {
            String SQL = " UPDATE lucky_top_gift SET STATUS = 1 , MSG =:msg WHERE id= :id AND msisdn = :msisdn ";

            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("msisdn", topGift.getMsisdn())
                    .addValue("id", topGift.getId())
                    .addValue("msg", "Add Accumulation Gift for week")
                    ;
            int updateStatus = jdbcTemplateObject.update(SQL, namedParameters);
            log.info(">>>>>>>>>>>>>>>> updateAddTopGiftStatus :" + updateStatus);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time updateAddTopGiftStatus:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
     
     
       /**
     *
     * @param wsName
     * @return
     */
    public Webservice getWebserviceByName(String wsName) {
        long startTime = System.currentTimeMillis();

        List<Webservice> list = new ArrayList<Webservice>();
        try {
            String SQL = "SELECT * from LUCKY_WEBSERVICE  WHERE WS_NAME =:wsName and STATUS = 1 ";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("wsName", wsName);
            NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
            list = jdbcTemplateObject.query(SQL, parameters, new WebserviceRowMapper());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time getWebserviceByName:" + (System.currentTimeMillis() - startTime) + " ms,  records:" + list.size());
        }
        return !list.isEmpty() ? list.get(0) : null;
    }
    
      public void insertLogSharedFriend(LukyGameInvitedFriendHis mfh){
    	 try{
             String sqlInsertLogSharedFriend = "INSERT INTO LUCKY_INVITE_FRIEND_HIS(id, isdn, shared_isdn, insert_time,result) values(sys_guid(), :isdn, :invitedIsdn, sysdate, :result)";
             SqlParameterSource namedParameters = new MapSqlParameterSource()
                     .addValue("isdn", mfh.getIsdn())
                     .addValue("invitedIsdn", mfh.getInvitedIsdn())
                     .addValue("result", mfh.getResult());
              NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
              jdbcTemplateObject.update(sqlInsertLogSharedFriend, namedParameters);
         }catch(Exception e){
             log.error(e.getMessage(), e);
         }
     }
       
    
     public void insertPrizeForWeek() {
        log.info("Starting insert top Prize for week: =========================>");
        long startTime = System.currentTimeMillis();

         String res = "";
         Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DAY_OF_MONTH, -3);
         Date runDate = cal.getTime();
             
        try {
       
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withProcedureName("GET_TOP_GIFT_STAR_BY_WEEK");
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(P_DATE, runDate);

            Map<String, Object> out = jdbcCall.execute(in);
            res = (String) out.get("O_RES_STATUS");
            log.info("RES insertPrizeForWeek :" + res);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Time insert insertPrizeForWeek:" + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
     
     
    public void addGiftForTopPlay(){
    	 try{
             
             
             List<LuckyTopGift> lstTop = getTopGift();
             
             for(LuckyTopGift obj :lstTop){
          
                 String cmd = Constant.BALANCE_CMD;
                 String cate = Constant.BALANCE_CATE_10000;
                 MpsResponse mpsRes = MpsWsUtils.getInstance().callMpsModbalance(obj.getMsisdn(), cmd, obj.getId(), cate);

                 String message = "";

                 //   boolean checkReg = true;
                 //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
                 //        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
                 //            checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
                 //        }
                 if (mpsRes != null && Constant.ERROR_CODE_0.equals(mpsRes.getErrorCode())) {
                     message = "Add gift TopPlay successfully: " + obj.getMsisdn() + " GiftValue: " + cate;
                     log.info(">>>>>>>>>>> " + message);
                 } else {
                     message = "Add gift TopPlay fail: " + obj.getMsisdn() + " GiftValue: " + cate;
                     log.info(">>>>>>>>>>> " + message);
                 }

                 updateAddTopGiftStatus(obj);
                         
           // insertPromotion(msisdn, giftType, giftCode, value);
                        
                 
             }
           
         }catch(Exception e){
             log.error(e.getMessage(), e);
         }
     } 
     
     
     
    public Long getRemaingPlayTurn(String msisdn) {
        long startTime = System.currentTimeMillis();
        Long remainingTurn = 0L ;

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName(PKG_GAME_TURN)
                    .withFunctionName("GET_REMAINING_TURN")
                    .withReturnValue();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue(MSISDN_LB, msisdn);
            remainingTurn = jdbcCall.executeFunction(BigDecimal.class, in).longValue();
            
           
        } catch (Exception e) {
            log.error("GET_REMAINING_TURN" + e.getMessage(), e);
        } finally {
            log.info("Time getRemaingPlayTurn:" + (System.currentTimeMillis() - startTime) + " ms");
        }
        return remainingTurn;
        

    }
      
}
