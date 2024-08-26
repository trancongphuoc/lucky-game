/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.luckydraw.bo.ActionLog;
import com.viettel.luckydraw.bo.LuckyGamePlayTurn;
import com.viettel.luckydraw.bo.LuckyGiftHistory;
import com.viettel.luckydraw.bo.LuckyHistoryBuyMore;
import com.viettel.luckydraw.bo.SubInfo;
import com.viettel.luckydraw.dao.BccsPosDAO;
import com.viettel.luckydraw.dao.BccsPreDAO;
import com.viettel.luckydraw.dao.LuckyGameDAO;
import com.viettel.luckydraw.util.AppUtils;
import com.viettel.luckydraw.util.Config;
import com.viettel.luckydraw.util.Constant;
import com.viettel.luckydraw.util.Views;
import com.viettel.luckydraw.ws.form.ApiGwRequest;
import com.viettel.luckydraw.ws.form.ApiGwResponse;
import com.viettel.luckydraw.ws.form.MpsResponse;
import com.viettel.luckydraw.ws.form.ResponseForm;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 *
 * @author manhnv23
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class LuckyGameWS {

    private static final Logger log = Logger.getLogger(LuckyGameWS.class);
    SimpleDateFormat dateRes = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
    SimpleDateFormat dateReq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String giftCode = "giftCode";
    private static String giftDes = "giftDesc";
    private static String giftMsg = "giftMsg";
    private static String giftValue = "giftValue";
    private static String giftType = "giftType";
    private static String remainingTurn = "remainingTurn";
    private static String playId = "playId";
    private static String success = "SUCCESS";
    private static String giftFinished = "GIFT_FINISHED";
    private Logger actionLog = Logger.getLogger("actionLog");
    private static final String FILE_SPLIT = "|";

    private static final String ISDN = "isdn";
    private static final String TIME = "time";
    private static final String PAGE_SIZE = "pageSize";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final int PAGE_SIZE_DEFAULT_VAL = 10;
    private static final int PAGE_NUMBER_DEFAULT_VAL = 1;
    private static final String DEFAULT = "DEFAULT";
    private static final String GAME_CODE = "LUCKYGAME";
    private static final String UPDATE_STATUS_OK = "1";
    private static final String UPDATE_STATUS_NOK = "99";
    private static final String GENERATE_GIFT_LUCKY_FREE = "GENERATE_GIFT_LUCKY_FREE";
    private static final String LUCKY_GENERATE_GIFT_FREE = "LUCKY_GENERATE_GIFT_FREE";
    private static final String GENERATE_GIFT_LUCKY_PREMIUM = "GENERATE_GIFT_LUCKY_PREMIUM";
    private static final String LUCKY_GENERATE_GIFT_PREMIUM = "LUCKY_GENERATE_GIFT_PREMIUM";
    private static final String GET_HISTORY_GIFT_PREMIUM = "get_history_gift_premium";
    private static final String GET_HISTORY_GIFT_LETTER = "get_history_gift_letter";
    private static final String GET_HISTORY_BUY_MORE = "get_history_buy_more";

    @POST
    @Path("/lucky")
    @Consumes(MediaType.APPLICATION_JSON)
    public String luckyGameRequest(String req, @Context HttpServletRequest q) {
        log.info("Starting call Api lucky game .................");

        long startTime = System.currentTimeMillis();
        ApiGwResponse res = new ApiGwResponse();
        ApiGwRequest request = new ApiGwRequest();
        String resString = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                
        Map<String, Object> wsResponse = new HashMap<>();
        List<Map<String, Object>> wsResponseList = new ArrayList<>();
        String msisdn = null;
        try {
            request = mapper.readValue(req, ApiGwRequest.class);
            log.info("Request:" + request.toString());
            String vali = request.validate(request);
            if (vali == null) {
                msisdn = !StringUtils.isEmpty(request.getWsRequest().get(ISDN)) ? AppUtils.convertToMsisdn(request.getWsRequest().get(ISDN)) : "";

                String gameCode = request.getWsRequest().get("gameCode");
                String lang = !StringUtils.isEmpty(request.getWsRequest().get("language")) ? request.getWsRequest().get("language").toUpperCase() : Constant.LANG_LC;
                String subType = request.getWsRequest().get("subType");
                String simType = request.getWsRequest().get("simType");
                String token = request.getWsRequest().get("token");
                String otpType = request.getWsRequest().get("otpType");
                String transId = request.getWsRequest().get("transId");
                String otp = request.getWsRequest().get("otp");
                String playId = request.getWsRequest().get("playId");
                String playType = request.getWsRequest().get("playType");
                String playChannel = request.getWsRequest().containsKey("playChannel") ? request.getWsRequest().get("playChannel") : Constant.PLAY_CHANNEL_WEB;
                String subService = (String) request.getWsRequest().get("subService");
                String uuid = (String) request.getWsRequest().get("uuid");
                switch (request.getWsCode()) {
                    case Constant.WS_GET_LUCKY_PLAY_TURN:
                        //Lay luot quay mac dinh, neu ko co thi them moi
                        getLuckyGamePlayTurn(subType, msisdn, gameCode,token, res, wsResponse, lang);
                        break;

                    case Constant.WS_GENERATE_GIFT:
                        //Xu ly chon qua, trao thuong
                        generateGift(subType, simType, msisdn, gameCode,token, res, wsResponse, lang);
                        break;
//                    case Constant.WS_UPDATE_PLAY_TURN_STATUS:
//                        //Xu ly chon qua, trao thuong
//                        updatePlayTurnStatus(subType, simType, msisdn,playId,gameCode, res, wsResponse, lang);
//                        break;

                    case Constant.WS_GET_GIFT_HIS:
                        //Lich su trung thuong
                        int pageNumber = request.getWsRequest().get(PAGE_NUMBER) == null ? PAGE_NUMBER_DEFAULT_VAL : Integer.parseInt(request.getWsRequest().get(PAGE_NUMBER));
                        int pageSize = request.getWsRequest().get(PAGE_SIZE) == null ? PAGE_SIZE_DEFAULT_VAL : Integer.parseInt(request.getWsRequest().get(PAGE_SIZE));
                        processGetGiftHis(msisdn, gameCode, playType,token,lang, res, wsResponseList, pageNumber, pageSize);
                        break;

                    case Constant.WS_GET_GIFT_HIS_LETTER:
                        //Lich su trung thuong
                        //   int pageNumber = request.getWsRequest().get(PAGE_NUMBER) == null ? PAGE_NUMBER_DEFAULT_VAL : Integer.parseInt(request.getWsRequest().get(PAGE_NUMBER));
                        //     int pageSize = request.getWsRequest().get(PAGE_SIZE) == null ? PAGE_SIZE_DEFAULT_VAL : Integer.parseInt(request.getWsRequest().get(PAGE_SIZE));
                        processGetGiftHisLetter(msisdn, GAME_CODE, playType,token ,lang, res, wsResponseList);
                        break;

//                    case Constant.WS_GET_SUBTYPE:
//                        getSubType(msisdn, res, wsResponse);
//                        break;
                    case Constant.WS_GET_RULE:
                        String ruleCode = request.getWsRequest().get("ruleCode");
                        getRule(lang, ruleCode, res, wsResponse);
                        break;

//                    case Constant.WS_INVITE_FRIEND:
//                        String invitedIsdn = request.getWsRequest().get("invitedIsdn");
//                        saveInviteFriend(msisdn, invitedIsdn, gameCode, lang, res, wsResponseList);
////                        res.setErrorCode(Constant.ERROR_SUCCESS);
//                       break;
////                          
//                    case Constant.WS_CHECK_PREMIUM_SERVICE:
//                        //Lay thong tin contact info
//                        checkPremiumOrNot(msisdn,playChannel,lang, res,wsResponse);
//                        
//                        break;
                    case Constant.WS_CHECK_SUB_SERVICE:
                        //Lay thong tin contact info
                        processcheckSubRegisterV2(msisdn, playChannel, token, lang, res, wsResponse);
                        break;
//                    case Constant.GET_SLIDESHOW_IMAGE:
//
//                        processGetListSlideShowImage(lang, res, wsResponseList);
//                        break;
                    case Constant.WS_REGISTER_SERVICE:
                        //Dang ky dich vu
                        processMpsService(msisdn, otpType, otp, Constant.REGISTER_CMD, res, lang, transId, "", "", subService,wsResponse);
                        break;
                    case Constant.WS_RENEW_SERVICE:
                        //Dang ky dich vu
                        processMpsService(msisdn, otpType, otp, Constant.RENEW_CMD, res, lang, transId, "", "", subService,wsResponse);
                        break;

                    case Constant.WS_CANCEL_SERVICE:
                        //Huy dich vu
                        //  String otpType = request.getWsRequest().get("otpType");
                        processMpsService(msisdn, otpType, otp, Constant.CANCEL_CMD, res, lang, transId, "", "", "",wsResponse);
                        break;

                    case Constant.WS_BUY_MORE_TURN_SERVICE:
                        // Call MPS charge
                        String cate = request.getWsRequest().get("cate");
                        String amount = request.getWsRequest().get("amount");
                        //   String predictionTurnId = request.getWsRequest().get("predictionTurnId");
//                        if (StringUtils.isEmpty(cate) || StringUtils.isEmpty(amount)) {
//                            res.setMessage("Invalid param");
//                            res.setErrorCode(Constant.ERROR_FAIL);
//                        }
//                        
                        processMpsService(msisdn, otpType, otp, Constant.CHARGE_CMD, res, lang, transId, cate, amount, subService,wsResponse);

                        break;

                    case Constant.WS_GET_OTP:
                        // Get OTP
                        getOtp(lang, msisdn, res, wsResponse);
                        break;

                    case Constant.WS_GET_ISDN:
                        // Get ISDN
                        getIsdn(uuid, res, wsResponse, lang);
                        break;
                    case Constant.WS_ADD_USER_TOKEN:
                        // Get Add User token
                        processAddUserToken(msisdn, token,res, wsResponse, lang);
                        break;
                        
                    case Constant.WS_LOGIN_OTP:
                        // Login OTP
                        processLoginOtp(msisdn, otp, transId, res, wsResponse, lang);
                        break;

                    case Constant.WS_GET_HISTORY_BUY_MORE:
                        // Get Accumulation 
                        processGetHistoryBuyMore(msisdn,token,lang, res, wsResponseList);
                        break;

                    default:
                        res.setMessage("Invalid wsCode");
                        res.setErrorCode(Constant.ERROR_FAIL);
                }
            } else {
                res.setMessage(vali);
                res.setErrorCode(Constant.ERROR_FAIL);
            }
        } catch (IOException | NumberFormatException e) {
            log.error(e.getMessage(), e);
            res.setErrorCode(Constant.ERROR_FAIL);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            try {
                res.setWsResponse(wsResponse);
                res.setWsResponseList(wsResponseList);
                res.setObject(null);
                if (Constant.ERROR_FAIL.equals(res.getErrorCode())) {
                    resString = mapper.writerWithView(Views.Normal.class).writeValueAsString(res);
                } else {
                    if (Constant.WS_GET_GIFT_HIS.equals(request.getWsCode())
                            || Constant.WS_GET_GIFT_HIS_LETTER.equals(request.getWsCode())
                            || Constant.WS_GET_HISTORY_BUY_MORE.equals(request.getWsCode())) {
                        resString = mapper.writerWithView(Views.List.class).writeValueAsString(res);
                        resString = resString.replace("wsResponseList", "wsResponse");
                    } else {
                        resString = mapper.writerWithView(Views.Manager.class).writeValueAsString(res);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (request.getWsCode().equals(Constant.WS_GET_LUCKY_PLAY_TURN)
                    || request.getWsCode().equals(Constant.WS_GENERATE_GIFT)
                    || request.getWsCode().equals(Constant.WS_GET_TURN_PREMIUM)
                    || request.getWsCode().equals(Constant.WS_CHECK_SUB_SERVICE)
                    || request.getWsCode().equals(Constant.WS_REGISTER_SERVICE)
                    || request.getWsCode().equals(Constant.WS_BUY_MORE_TURN_SERVICE)
                    || request.getWsCode().equals(Constant.WS_RENEW_SERVICE)
                    || request.getWsCode().equals(Constant.WS_CANCEL_SERVICE)
                    || request.getWsCode().equals(Constant.WS_INVITE_FRIEND)
                    || request.getWsCode().equals(Constant.WS_GET_SUBTYPE)
                    || request.getWsCode().equals(Constant.WS_GET_ACCUMULATION_MONEY)
                    || request.getWsCode().equals(Constant.WS_GET_ISDN)) {
                ActionLog logAction = new ActionLog(request.toString(), resString, msisdn, duration, request.getWsCode());
                writeActionLog(logAction);
//                InsertLogProcess.endQueue(logAction);
            }
        }

        return resString;
    }

    public void getRule(String lang, String ruleCode, ApiGwResponse res, Map<String, Object> wsResponse) {
        String rule = LuckyGameDAO.getInstance().getRule(lang, ruleCode);
        wsResponse.put("info", rule);
        res.setErrorCode(Constant.ERROR_SUCCESS);
    }

    public void getLuckyGamePlayTurn(String subType, String msisdn, String gameCode, String token, ApiGwResponse res, Map<String, Object> wsResponse, String lang) {

        boolean isVerifyToken = true ;
         if (Config.getInstance().getIsCheckTokenOrNot()== 1) {
                  LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
            }
      
        String message = "";
        if (isVerifyToken) {
            //Lay luot quay mac dinh, neu ko co thi them moi
            boolean checkReg = true;
            if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
                checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
            }

            if (checkReg) {

                log.info("Premium type -------------------->");
                //      List<LuckyGamePlayTurn> lstTurnPremium= LuckyGameDAO.getInstance().getLuckyGamePlayTurnPremium(msisdn, DEFAULT, null, gameCode);

                Long remainPlayTurn = LuckyGameDAO.getInstance().getRemaingPlayTurn(msisdn);
            
                    //Lay danh sach luot con lai

                    log.info("Get turn remaining premium");

//                    List<LuckyGamePlayTurn> listTurnNew
//                            = LuckyGameDAO.getInstance().getLuckyGamePlayTurnPremium(msisdn, null, Constant.ERROR_CODE_0, gameCode);
                    wsResponse.put(remainingTurn, remainPlayTurn);
                    res.setErrorCode(Constant.ERROR_SUCCESS);

                    res.setMessage("Get lucky game play turn successfully!");
                
            } else {
                message = Config.getInstance().getMessage(lang, "MSG_NOT_REG_SERVICE");
                wsResponse.put("isPremium", Boolean.valueOf(false));
                res.setErrorCode(Constant.ERROR_CODE_2);
                res.setMessage(message);
              }
        } else {
            log.info("Token invalid or expire");
            message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
            res.setMessage(message);
            res.setErrorCode(Constant.ERROR_CODE_TOKEN_INVALID_10);
            wsResponse.put("isdn", msisdn);
        }

    }

    public void processGetGiftHis(String msisdn, String gameCode, String playType, String token, String lang, ApiGwResponse res, List<Map<String, Object>> wsResponseList, int pageNumber, int pageSize) {

        boolean isVerifyToken  = true; // LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
        
         if (Config.getInstance().getIsCheckTokenOrNot()== 1) {
                  LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
            }
        
        String procName = GET_HISTORY_GIFT_PREMIUM;
//        if(Constant.PREMIUM.equals(playType)){
//            procName = GET_HISTORY_GIFT_PREMIUM;
//        }

        //   log.info("isdn ====> :" +  msisdn);
        //   log.info("procName: " + procName);
        String message = "";
        if (isVerifyToken) {
            List<LuckyGamePlayTurn> listData = LuckyGameDAO.getInstance().getGiftListHistory(msisdn, gameCode, procName, lang, pageNumber, pageSize);

            log.info("listDataHis: " + listData.size());

            for (LuckyGamePlayTurn i : listData) {
                Map<String, Object> item = new HashMap<>();
                item.put(TIME, i.getGiftDate());
                item.put(giftCode, i.getGiftCode());
                item.put(giftDes, i.getGiftDesc());
                item.put(giftType, i.getGiftType());
                wsResponseList.add(item);
            }
            res.setErrorCode(Constant.ERROR_SUCCESS);
        } else {
            log.info("Token invalid or expire");
            message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
            res.setMessage(message);
            res.setErrorCode(Constant.ERROR_CODE_TOKEN_INVALID_10);
            Map<String, Object> item = new HashMap<>();
            item.put("isdn", msisdn);
            wsResponseList.add(item);
        }
    }

    public void processGetGiftHisLetter(String msisdn, String gameCode, String playType, String token, String lang, ApiGwResponse res, List<Map<String, Object>> wsResponseList) {
        String procName = GET_HISTORY_GIFT_LETTER;
        boolean isVerifyToken = true ; // LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);

          if (Config.getInstance().getIsCheckTokenOrNot()== 1) {
                  LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
            }
          
        String message = "";
        if (isVerifyToken) {
            List<LuckyGiftHistory> listHis = LuckyGameDAO.getInstance().getGiftListHistoryLetter(msisdn, gameCode, procName, lang);

            log.info("listHis: " + listHis.size());

            for (LuckyGiftHistory i : listHis) {
                Map<String, Object> item = new HashMap<>();
                item.put(ISDN, i.getMsisdn());
                item.put(giftCode, i.getGiftCode());
                //   item.put(giftDes, i.getGiftDesc());
                item.put("giftCount", i.getGiftCount());
                wsResponseList.add(item);
            }

            res.setErrorCode(Constant.ERROR_SUCCESS);
        } else {
            log.info("Token invalid or expire");
            message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
            res.setMessage(message);
            res.setErrorCode(Constant.ERROR_CODE_TOKEN_INVALID_10);
            Map<String, Object> item = new HashMap<>();
            item.put("isdn", msisdn);
            wsResponseList.add(item);
        }
    }
    
    public void processGetHistoryBuyMore(String msisdn, String token, String lang, ApiGwResponse res, List<Map<String, Object>> wsResponseList) {
        String procName = GET_HISTORY_BUY_MORE;
        boolean isVerifyToken = true ; // LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);

          if (Config.getInstance().getIsCheckTokenOrNot()== 1) {
                  LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
            }
          
        String message = "";
        if (isVerifyToken) {
            
            String isdn = AppUtils.standardizeIsdn(msisdn);
            List<LuckyHistoryBuyMore> listHis = LuckyGameDAO.getInstance().getGiftListHistoryBuyMore(isdn, procName, lang);

            log.info("listHis: " + listHis.size());

            for (LuckyHistoryBuyMore i : listHis) {
                Map<String, Object> item = new HashMap<>();
                System.out.println("isdn" + i.getMsisdn());
                System.out.println("price" + i.getPrice());
                System.out.println("buyTime" + i.getRequestTime());
                item.put(ISDN, i.getMsisdn());
                item.put("price", i.getPrice());
                //   item.put(giftDes, i.getGiftDesc());
                item.put("buyTime", i.getRequestTime());
                wsResponseList.add(item);
            }

            res.setErrorCode(Constant.ERROR_SUCCESS);
        } else {
            log.info("Token invalid or expire");
            message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
            res.setMessage(message);
            res.setErrorCode(Constant.ERROR_CODE_TOKEN_INVALID_10);
            Map<String, Object> item = new HashMap<>();
            item.put("isdn", msisdn);
            wsResponseList.add(item);
        }
    }


    public void generateGift(String subType, String simType, String msisdn, String gameCode,String token, ApiGwResponse res, Map<String, Object> wsResponse, String lang) {

        int resCheckUmoney = 0;//resCheckUmoney(msisdn);
        boolean isVerifyToken = true;// LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
        if (Config.getInstance().getIsCheckTokenOrNot()== 1) {
                  LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
        }
        
        boolean checkReg = true;

        String message = "";
        if (isVerifyToken) {

        if (checkReg) {
            
            
       //     LuckyGameDAO.getInstance().insertLuckyPlayTurn(msisdn,DEFAULT,GAME_CODE);
            
            
            log.info("Start generateGift -------------------->");

            ResponseForm resDb = LuckyGameDAO.getInstance().generateAndAddGift(msisdn, subType, gameCode, lang, simType, LUCKY_GENERATE_GIFT_PREMIUM, resCheckUmoney);

            //  System.out.println("resDb.getResult(): "+ resDb.getResult());
            if (success.equals(resDb.getResult())) {
                wsResponse.put(giftCode, resDb.getGiftCode());
                wsResponse.put(giftDes, resDb.getGiftDesc());
                wsResponse.put(giftMsg, resDb.getGiftMsg());
                //   wsResponse.put(playId, resDb.getPlayId());
                wsResponse.put(giftValue, resDb.getGiftValue());

                res.setErrorCode(Constant.ERROR_SUCCESS);
                String giftType = resDb.getGiftType();
                String giftCode = resDb.getGiftCode();
                String playId = resDb.getPlayId();
                String giftVal = resDb.getGiftValue();

                /*
                    if (Config.getInstance().getGiftTypeAddTurn().equals(giftType)) {
                        for (int i = 0; i < Config.getInstance().getNumAddTurn(); i++) {
                            LuckyGameDAO.getInstance().addPlayTurnPremium(msisdn, "ADDTURN", gameCode);
                        }
                    }
                   
                    
                    else if (Constant.GIFT_TYPE_ADS.equalsIgnoreCase(giftType) || Constant.GIFT_TYPE_MISSION.equalsIgnoreCase(giftType) || Constant.GIFT_TYPE_INVITATION.equalsIgnoreCase(giftType)) {
                        for (int i = 0; i < Config.getInstance().getNumAddTurn(); i++) {
                            LuckyGameDAO.getInstance().addPlayTurnPremium(msisdn, "ADDTURN", gameCode);
                        }
                    } 
                    
                 */
                if (Constant.GIFT_TYPE_DATA.equalsIgnoreCase(giftType)) {

                    String value = resDb.getGiftValue();
                    String cmd = Constant.DATA_CMD;
                    processMpsAddGift(msisdn, cmd, lang, playId, value);
                    //   insertPromotion(msisdn, giftType, giftCode, value);

                }
                if (Constant.GIFT_TYPE_VOICE.equalsIgnoreCase(giftType)) {
                    String cmd = Constant.VOICE_CMD;
                    String value = resDb.getGiftValue();
                    processMpsAddGift(msisdn, cmd, lang, playId, value);
                    // insertPromotion(msisdn, giftType, giftCode, value);
                }
                if (Constant.GIFT_TYPE_UPOINT.equalsIgnoreCase(giftType)) {
                    //  String cmd = Constant.UPOINT_CMD;
                    log.info("Add Gift Upoint");

                    String value = resDb.getGiftValue();
                    processMpsAddUpointGift(msisdn,value,lang,playId);
                    //  insertPromotion(msisdn, giftType, giftCode, value);
                }
//                            else if("UMONEY".equalsIgnoreCase(giftType)){
//                        long value = Long.parseLong(resDb.getGiftValue());
//                        topupUmoney(msisdn, value, playId);
//                    }
                //send SMS
                String key = giftCode;
                String smsContent = Config.getInstance().getSms(lang, key);
                
                

                if (smsContent != null) {

                    log.info("Premium generateGift SMS --------------------> " + smsContent);
                    //  SmsGwUtils.getInstance().sendMtUnicode(msisdn, smsContent);
                }
                
                            //Load lai so luot lac con lai
              //  Long remainPlayTurn = LuckyGameDAO.getInstance().getRemaingPlayTurn(msisdn);

//                List<LuckyGamePlayTurn> lstTurnPremiumRemain = LuckyGameDAO.getInstance().getLuckyGamePlayTurnPremium(msisdn, null, "0", gameCode);
//                wsResponse.put(remainingTurn, lstTurnPremiumRemain.size());
            //    wsResponse.put(remainingTurn, remainPlayTurn);
                
//                    
//                    if (Constant.GIFT_TYPE_ADS.equalsIgnoreCase(giftType)){
//                           wsResponse.put("adsCountTime",  Config.getInstance().getAdsCountTime());
//                    }

            }


            }else{
                message = Config.getInstance().getMessage(lang, "MSG_NOT_REG_SERVICE");
                wsResponse.put("isPremium", Boolean.valueOf(false));
                res.setErrorCode(Constant.ERROR_CODE_2);
                res.setMessage(message);
            }
        }
        else {
          log.info("Token invalid or expire");
          message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
          res.setMessage(message);
          res.setErrorCode(Constant.ERROR_CODE_TOKEN_INVALID_10);
          wsResponse.put("isdn", msisdn);
        }
    }




    public void getIsdn(String uuid, ApiGwResponse res, Map<String, Object> wsResponse, String lang) {

        String token = UUID.randomUUID().toString();

        String isdn = UuidUtils.getInstance().getIsdn(uuid);
        String message = "";
        if (!StringUtils.isEmpty(isdn)) {
            isdn = AppUtils.standardizeIsdn(isdn);
            String msisdn = AppUtils.convertToMsisdn(isdn);
            String currToken =  LuckyGameDAO.getInstance().getUserToken(msisdn);
            
            if(!StringUtils.isEmpty(currToken)){
                message = isdn + "Ready to play game now";
                res.setErrorCode(Constant.ERROR_SUCCESS);
                wsResponse.put("token", currToken);
                wsResponse.put("isdn", isdn);
                wsResponse.put("uuid", uuid);
                
            }else{
                int addToken = LuckyGameDAO.getInstance().addUserToken(msisdn, token, token);
                if (addToken > 0) {
                    message = isdn + "Ready to play game now";
                    res.setErrorCode(Constant.ERROR_SUCCESS);
                    wsResponse.put("token", token);
                    wsResponse.put("isdn", isdn);
                    wsResponse.put("uuid", uuid);
                } else {
                    message = Config.getInstance().getMessage(lang, "MSG.LOGIN_FAIL");
                    res.setErrorCode(Constant.ERROR_FAIL);
                }
            }            
            
            res.setMessage(message);
        } else {
            message = "Can not get Isdn please input phone numner to login and play";
            res.setErrorCode(Constant.ERROR_FAIL);
            res.setMessage(message);
        }

    }

//    public String formatGiftCode(String giftCode) {
//        if (giftCode.startsWith("LT_")) {
//            giftCode = giftCode.substring(3);
//        }
//        return giftCode;
//    }

    public void getSubType(String msisdn, ApiGwResponse res, Map<String, Object> wsResponse) {
        String productCode = null;
        String subType = null;

        productCode = BccsPreDAO.getInstance().getProductCode(AppUtils.convertToIsdn(msisdn));
        if (productCode != null) {
            subType = Constant.PREPAID;
        } else {
            productCode = BccsPosDAO.getInstance().getProductCode(AppUtils.convertToIsdn(msisdn));
            if (productCode != null) {
                subType = Constant.POSTPAID;
            }
        }
        if (productCode == null) {
            res.setErrorCode(Constant.ERROR_FAIL);
            log.error(msisdn + "=> not found productCode");
            return;
        }

        String simType = Constant.MOBILE;
        if (productCode.toUpperCase().startsWith("LT") || productCode.toUpperCase().startsWith("4G")) {
            simType = Constant.LT;
        }

        wsResponse.put("subType", subType);
        wsResponse.put("simType", simType);
        res.setErrorCode(Constant.ERROR_SUCCESS);

    }

    public void writeActionLog(ActionLog log) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        StringBuffer buffer = new StringBuffer();
        buffer.append(dateFormat.format(new Date())).append(FILE_SPLIT).append(log.getIsdn()).append(FILE_SPLIT)
                .append(log.getMethod()).append(FILE_SPLIT).append(log.getRequest()).append(FILE_SPLIT)
                .append(log.getResponse()).append(FILE_SPLIT).append(log.getDuration());
        actionLog.info(buffer.toString());
    }

 

    /**
     * @param msisdn
     * @param price
     * @param res
     * @param wsResponse
     * @param auctionCode
     * @param lang
     */
    public void checkPremiumOrNot(String msisdn, String playChannel, String lang,
            ApiGwResponse res, Map<String, Object> wsResponse) {

        // Kiem tra tinh hop le cua Token
        ///    boolean isVerifyToken = LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
        String message = "";
//        if (Constant.PLAY_CHANNEL_WEB.equals(playChannel) && isVerifyToken) {

        boolean checkReg = true;
        //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
            checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
        }
        if (checkReg) {
            message = Config.getInstance().getMessage(lang, "MSG_REG_SERVICE");
            wsResponse.put("isPemium", true);

//                }
        } else {
            message = Config.getInstance().getMessage(lang, "MSG_NOT_REG_SERVICE");
//                res.setErrorCode(Constant.ERROR_CODE_1);  // Chua dang ky dich vu              
//                res.setMessage(message);
            wsResponse.put("isPemium", false);
        }
        res.setErrorCode(Constant.ERROR_CODE_0);
        res.setMessage(message);

//        }
    }

    public void processcheckSubRegisterV2(String msisdn, String playChannel, String token, String lang,
            ApiGwResponse res, Map<String, Object> wsResponse) {

        // Kiem tra tinh hop le cua Token
        boolean isVerifyToken = LuckyGameDAO.getInstance().verifyUserToken(msisdn, token);
        String message = "";
        if (Constant.PLAY_CHANNEL_WEB.equals(playChannel) && isVerifyToken) {

            SubInfo checkReg = null;
            //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
            if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
                checkReg = LuckyGameDAO.getInstance().checkSubStatus(msisdn);
            }

            if (checkReg != null) {

                wsResponse.put("subInfo", checkReg);

                message = Config.getInstance().getMessage(lang, "MSG_REG_SERVICE");
                if (Constant.ERROR_CODE_1.equals(checkReg.getRegStatus()) && Constant.ERROR_CODE_1.equals(checkReg.getChargeStatus())) {
                    res.setErrorCode(Constant.ERROR_CODE_0);  // Da dang ky dich vu va thanh toan
                    res.setMessage(message);

                } else if (Constant.ERROR_CODE_1.equals(checkReg.getRegStatus()) && Constant.ERROR_CODE_2.equals(checkReg.getChargeStatus())) {
                    res.setErrorCode(Constant.ERROR_CODE_3);  // Da dang ky dich vu nung chua gia han
                    res.setMessage(message);
                }
//                }
            } else {
                message = Config.getInstance().getMessage(lang, "MSG_NOT_REG_SERVICE");
                res.setErrorCode(Constant.ERROR_CODE_2);  // Chua dang ky dich vu
                res.setMessage(message);
            }
        }
    }

    public void processMpsService(String msisdn, String otpType, String otp, String cmd, ApiGwResponse res, String lang, String transId, String cate, String amount, String subService,Map<String, Object> wsResponse) {
                
        MpsResponse mpsRes = MpsWsUtils.getInstance().callMpsOtpHandle(msisdn, cmd, otpType, otp, transId, cate, amount);

        String message = "";

        //   boolean checkReg = true;
        //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
//        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
//            checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
//        }

        if (mpsRes != null && Constant.ERROR_CODE_0.equals(mpsRes.getErrorCode())) {
            if (Constant.REGISTER_CMD.equals(cmd)) {
                message = Config.getInstance().getMessage(lang, "MSG_REGISTER_SUCCESS");
            } else if (Constant.RENEW_CMD.equals(cmd)) {
                message = Config.getInstance().getMessage(lang, "MSG_RENEW_SUCCESS");
            } else if (Constant.CANCEL_CMD.equals(cmd)) {
                message = Config.getInstance().getMessage(lang, "MSG_CANCEL_SUCCESS");
            } else {
                // Update trang thai choi hop le  predictionTurnId
             
             message = Config.getInstance().getMessage(lang, "MSG_BUYMORE_SUCCESS");
                 log.info(">>>>>>>>>>> Buyre More Turn  successfully");
            }

            res.setErrorCode(Constant.ERROR_SUCCESS);
          String token = UUID.randomUUID().toString();
          String currToken =  LuckyGameDAO.getInstance().getUserToken(msisdn);
          
          if(!StringUtils.isEmpty(currToken)){
            
              token = currToken ;
            }
          
           wsResponse.put("token", token);
           wsResponse.put("isdn", msisdn); 

        } else if (mpsRes != null && Constant.ERROR_CODE_401.equals(mpsRes.getErrorCode())) {
            res.setErrorCode(Constant.ERROR_CODE_401);
            message = Config.getInstance().getMessage(lang, "MSG_NOT_ENOUGH_MONEY");
        } else if (mpsRes != null && (Constant.ERROR_CODE_416.equals(mpsRes.getErrorCode()) || Constant.ERROR_CODE_415.equals(mpsRes.getErrorCode()))) {
            res.setErrorCode(Constant.ERROR_CODE_416);
            message = Config.getInstance().getMessage(lang, "MSG_WRONG_OTP");
            res.setMessage(message);
        } else {
            res.setErrorCode(Constant.ERROR_FAIL);
            if (Constant.REGISTER_CMD.equals(cmd)) {
                message = Config.getInstance().getMessage(lang, "MSG_REGISTER_ERROR");
            } else if (Constant.CANCEL_CMD.equals(cmd)) {
                message = Config.getInstance().getMessage(lang, "MSG_CANCEL_ERROR");
            } else if (Constant.RENEW_CMD.equals(cmd)) {
                message = Config.getInstance().getMessage(lang, "MSG_RENEW_ERROR");
            } else {
                message = Config.getInstance().getMessage(lang, "MSG_BUYMORE_ERROR");
            }
        }
        res.setMessage(message);
        

        
//        if(!StringUtils.isEmpty(currToken)){
//            
//              token = currToken ;
//        }
        
     //   wsResponse.put("isPremium", false);
   //     res.setMessage("Register to play game");
    //    res.setErrorCode(Constant.ERROR_SUCCESS);
    //    wsResponse.put("token", token);
    //    wsResponse.put("isdn", msisdn);                
         
    }

    public void processMpsAddGift(String msisdn, String cmd, String lang, String transId, String cate) {

        MpsResponse mpsRes = MpsWsUtils.getInstance().callMpsModbalance(msisdn, cmd, transId, cate);

        String message = "";

        //   boolean checkReg = true;
        //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
//        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
//            checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
//        }
        if (mpsRes != null && Constant.ERROR_CODE_0.equals(mpsRes.getErrorCode())) {
            message = "Add gift successfully: " + msisdn + " GiftValue: " + cate;
            log.info(">>>>>>>>>>> " + message);
        } else {
            message = "Add gift fail: " + msisdn + " GiftValue: " + cate;
            log.info(">>>>>>>>>>> " + message);
        }
        // Save log
    }

    public void processMpsAddUpointGift(String msisdn, String amount, String lang, String transId) {

        MpsResponse mpsRes = MpsWsUtils.getInstance().callMpsAddUpoint(msisdn, amount, transId);

        String message = "";

        //   boolean checkReg = true;
        //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
//        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
//            checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
//        }
        if (mpsRes != null && Constant.ERROR_CODE_0.equals(mpsRes.getErrorCode())) {
            message = "Add upoint gift successfully: " + msisdn + " GiftValue: " + amount;
            log.info(">>>>>>>>>>> " + message);
        } else {
            message = "Add upoint gift fail: " + msisdn + " GiftValue: " + amount;
            log.info(">>>>>>>>>>> " + message);
        }
        // Save log
    }

    public void getOtp(String lang, String isdn, ApiGwResponse res, Map<String, Object> wsResponse) {

        boolean checkReg = true;
        String transId = UUID.randomUUID().toString();
        //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
            checkReg = LuckyGameDAO.getInstance().checkSubRegister(isdn);
        }

        if (checkReg) {

            String isGetOtp = LuckyGameDAO.getInstance().getOTP(lang, isdn);
            String message = "";

            if (null == isGetOtp) {

                message = Config.getInstance().getMessage(lang, "MSG_GET_OTP.error");
                log.info(">>>>>>>>>>> Get OTP error");
                res.setErrorCode(Constant.ERROR_CODE_1);
            } else {
                switch (isGetOtp) {
                    case Constant.ERROR_CODE_0:
                        log.info(">>>>>>>>>>> Get OTP successfully");
                        message = Config.getInstance().getMessage(lang, "MSG_GET_OTP.success");
                        res.setErrorCode(Constant.ERROR_CODE_0);
                        break;
                    case Constant.ERROR_CODE_GET_OVER_OTP_5:
                        log.info(">>>>>>>>>>> Limit get OTP over per day:" + isdn);
                        message = Config.getInstance().getMessage(lang, "MSG_LIMIT_OTP");
                        res.setErrorCode(Constant.ERROR_CODE_GET_OVER_OTP_5);
                        break;
                    default:
                        log.info(">>>>>>>>>>> Get OTP error");
                        message = Config.getInstance().getMessage(lang, "MSG_GET_OTP.success");
                        res.setErrorCode(Constant.ERROR_CODE_1);
                        break;
                }
            }
            res.setMessage(message);
            res.setErrorCode(Constant.ERROR_SUCCESS);

            wsResponse.put("isPremium", checkReg);
            wsResponse.put("transId", transId);
            wsResponse.put("isdn", isdn);
            res.setMessage("Login to play game");

        } else {

         
         //   processMpsService(isdn, "0", "", Constant.REGISTER_CMD, res, lang, transId, "", "", "",wsResponse);

            wsResponse.put("isPremium", false);
            res.setMessage("Register to play game");
            res.setErrorCode(Constant.ERROR_SUCCESS);
            wsResponse.put("transId", transId);
            wsResponse.put("isdn", isdn);

        }

    }

    public void getTotalAccumulationMoney(String lang, String isdn, ApiGwResponse res, Map<String, Object> wsResponse) {
        Long totalMoney = LuckyGameDAO.getInstance().getTotalAccumulationMoney(isdn);

        wsResponse.put("totalMoney", totalMoney);
        res.setErrorCode(Constant.ERROR_SUCCESS);

    }

    public void processLoginOtp(String msisdn, String otp, String transId, ApiGwResponse res, Map<String, Object> wsResponse, String lang) {

        boolean checkReg = true;
        //Kiem tra xem da dang ky dich vu hay chua neu chua dk thi se tien hanh thong bao va dang ky dich vu
        if (Config.getInstance().getIsCheckRegisterOrNot() == 1) {
            checkReg = LuckyGameDAO.getInstance().checkSubRegister(msisdn);
        }

        if (checkReg) {

            boolean isVerify = LuckyGameDAO.getInstance().verifyOtp(msisdn, otp);

            String message = "";
            String  token = UUID.randomUUID().toString();
            if (isVerify) {
               // token = UUID.randomUUID().toString();
                String refreshToken = UUID.randomUUID().toString();

                int addToken = LuckyGameDAO.getInstance().addUserToken(msisdn, token, refreshToken);
                if (addToken > 0) {
                    message = "Isdn can play game now";
                    res.setErrorCode(Constant.ERROR_SUCCESS);
                    
                    wsResponse.put("isdn", msisdn);
                    wsResponse.put("token", token);
//                boolean checkReg = GymDAO.getInstance().checkSubRegister(msisdn);

                } else {
                    message = Config.getInstance().getMessage(lang, "MSG.LOGIN_FAIL");
                    res.setErrorCode(Constant.ERROR_FAIL);
                }
            } else {
                message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
                res.setErrorCode(Constant.ERROR_FAIL);
            }
            res.setMessage(message);
        } else {
            
           // processMpsService(msisdn, "1", otp, Constant.REGISTER_CMD, res, lang, transId, "", "", "",wsResponse);
            if(Constant.ERROR_SUCCESS.equals(res.getErrorCode())){
               wsResponse.put("isdn", msisdn);
               wsResponse.put("token",  UUID.randomUUID().toString());
                
               res.setMessage("Isdn can play game now");
            }            
        }

    }
    
    public void processAddUserToken(String msisdn, String token, ApiGwResponse res, Map<String, Object> wsResponse, String lang) throws IOException {

   
        String srvPhone ="";
        String message = "";

     //    srvPhone = UuidUtils.getInstance().verifyUniIdUser(token);
        
        if (Config.getInstance().getIsCheckTokenOrNot()== 1) {
                srvPhone = UuidUtils.getInstance().verifyUniIdUser(token);
        

        if (StringUtils.isEmpty(srvPhone)) {
                message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
                res.setErrorCode(Constant.ERROR_FAIL);
                res.setMessage(message);
        } else {
                
                srvPhone = AppUtils.convertToMsisdn(srvPhone);

                if (srvPhone.equalsIgnoreCase(msisdn)) {
                    // token = UUID.randomUUID().toString();
                    String refreshToken = UUID.randomUUID().toString();

                    int addToken = LuckyGameDAO.getInstance().addUserToken(srvPhone, token, refreshToken);
                    if (addToken > 0) {
                        message = "user can play game now";
                        res.setErrorCode(Constant.ERROR_SUCCESS);

                        wsResponse.put("isdn", srvPhone);
                        wsResponse.put("token", token);
//                boolean checkReg = GymDAO.getInstance().checkSubRegister(msisdn);

                    } else {
                        message = Config.getInstance().getMessage(lang, "MSG.LOGIN_FAIL");
                        res.setErrorCode(Constant.ERROR_FAIL);
                    }
                } else {
                    message = Config.getInstance().getMessage(lang, "MSG.TOKEN.INVALID");
                    res.setErrorCode(Constant.ERROR_FAIL);
                }               
            }
        }
        else {
            
             String refreshToken = UUID.randomUUID().toString();

             int addToken = LuckyGameDAO.getInstance().addUserToken(msisdn, token, refreshToken);
                    if (addToken > 0) {
                        message = "user can play game now";
                        res.setErrorCode(Constant.ERROR_SUCCESS);

                        wsResponse.put("isdn", msisdn);
                        wsResponse.put("token", token);
//                boolean checkReg = GymDAO.getInstance().checkSubRegister(msisdn);

                    } else {
                        message = Config.getInstance().getMessage(lang, "MSG.LOGIN_FAIL");
                        res.setErrorCode(Constant.ERROR_FAIL);
                    }          
            
        }
         res.setMessage(message);
            
        //   String  token = UUID.randomUUID().toString();
    }
}
