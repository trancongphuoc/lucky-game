/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.util;
/**
 *
 * @author manhnv23
 */
public class Constant {

    //DS wsCode
    public static final String WS_GET_LUCKY_PLAY_TURN = "wsGetLuckyPlayTurn";
    public static final String WS_GET_TURN_PREMIUM = "wsGetAllPlayTurnPremium";
    public static final String WS_GENERATE_GIFT = "wsGenerateGift";
//    public static final String ADD_FRIEND = "wsSaveShareFriend";
    public static final String WS_GET_GIFT_HIS = "wsGetHistoryGift";
    public static final String WS_GET_GIFT_HIS_LETTER = "wsGetHistoryGiftLetter";
    public static final String WS_GET_CHART = "wsGetAllGiftTop";
    public static final String WS_GET_RULE = "wsGetRule";
    public static final String WS_GET_SUBTYPE = "wsGetSubType";
    public static final String WS_INVITE_FRIEND = "wsInviteFriend";        
    public static final String WS_GET_ACCUMULATION_MONEY = "wsGetAccumulationMoney";
    public static final String WS_UPDATE_PLAY_TURN_STATUS = "wsUpdatePlayTurnStatus";

    //DS errorCode
    public static final String ERROR_SUCCESS = "0";
    public static final String ERROR_FAIL = "1";
    public static final String INSUFFICIENT_PRIVILEGE = "10";

    //DS language
    public static final String LANG_EN = "EN";
    public static final String LANG_LC = "LC";
    public static final String LANG_VI = "VI";
    public static final String LANG_ZH = "ZH";
    
    //List Gift type
    public static final String GIFT_TYPE_DATA = "DATA";
    public static final String GIFT_TYPE_VOICE = "VOICE";
    public static final String GIFT_TYPE_SMS = "SMS";
    public static final String GIFT_TYPE_GIFT = "GIFT";
    public static final String GIFT_TYPE_MONEY = "MONEY";
    public static final String GIFT_TYPE_ADDTURN = "ADDTURN";
    public static final String GIFT_TYPE_UPOINT = "UPOINT";
    public static final String GIFT_TYPE_MISSION = "MISSION";
    public static final String GIFT_TYPE_ADS = "ADS";
    public static final String GIFT_TYPE_INVITATION = "INVITATION";
    
    public static final String DEFAULT_TURN = "DEFAULT";

    public final static String SYSTEM_ERROR = "EXCEPTION";
    public final static String TIMEOUT = "TIMEOUT";
    public static final String CONNECTION_ERROR = "CONNECTION_ERROR";
    public final static String RES_OK = "00";
    public final static String RES_NOK = "23";
    public final static int NUM_RECORD_PER_INSERT_LOG = 100;
    public final static String CALL_BCCS_GET_ACC_INFO = "getInfoAccount";
    public final static String CALL_BCCS_ADD_BALANCE = "addBalanceAccount";
    public static final String SUB_PRE = "1";
    public static final String SUB_POST = "2";
    public static final String PREPAID = "1";
    public static final String POSTPAID ="2";
    public static final String FREE = "0";
    public static final String PREMIUM ="1";
    
    
    public static final String LT="1";
    public static final String MOBILE ="0";
    public static final String BALANCE_FIELD ="82";
    public static final String STATUS_FIELD = "39";
    public static final String RESPONSE_CODE_TAG ="responseCode";
    public static final String RESP_SUCCESS = "00000";
    public static final String STATUS_ACTIVE = "00000";
     
    public static final String WS_CHECK_SUB_REG = "LUCKY_STATUS";     
    public static final String REGISTER_CMD = "REGISTER";
    public static final String RENEW_CMD = "RESTORE";
    public static final String CANCEL_CMD = "CANCEL";
    public static final String CHARGE_CMD = "CHARGE";    
    public static final String DATA_CMD = "MODBALANCEDATA2";    
    public static final String VOICE_CMD = "MODBALANCEVOICE3";    
    public static final String BALANCE_CMD = "MODBALANCE";    
    public static final String BALANCE_CATE_10000 = "MODBALANCEM10000";    
    public static final String BALANCE_CATE_200000 = "MODBALANCEM200000";    
    public static final String UPOINT_CMD = "UPOINT";
    public static final String PLAY_CHANNEL_WEB = "WEB";    
    public static final int ERROR_OK = 0;
    public static final int ERROR_NOK = 1;
    
    public static final String ERROR_CODE_0 = "0";    
    public static final String ERROR_CODE_1 = "1";
    public static final String ERROR_CODE_2 = "2";
    public static final String ERROR_CODE_3 = "3";
    public static final String ERROR_CODE_4 = "4";
    public static final String ERROR_CODE_401 = "401";
    public static final String ERROR_CODE_415 = "415";
    public static final String ERROR_CODE_416 = "416";    
    public static final String ERROR_CODE_GET_OVER_OTP_5 = "5";    
    
    public static final String ERROR_CODE_INVALID_PRICE_6 = "6";  //Gia khong hop le
    public static final String ERROR_CODE_SESSION_EXPIRE_7 = "7"; //Chuong trinh da het han
    public static final String ERROR_CODE_OVER_LIMIT_PLAY_8 = "8";  // Qua so lan mua trong ngay
    public static final String ERROR_CODE_OTP_INVALID_9 = "9";  //OTP invalid or expire
    public static final String ERROR_CODE_TOKEN_INVALID_10 = "10";  //OTP invalid or expire
    public static final String ERROR_CODE_ADD_TURN_11 = "11";  //Het luong fee

    
    
    
    public static final int REG_FEE = 3;
    public static final String REG_STATUS_1 = "1";
    public static final String REG_STATUS_2 = "2";
    
    
    public static final String WS_CHECK_PREMIUM_SERVICE = "wsCheckPremiumService";
    public static final String WS_CHECK_SUB_SERVICE = "wsCheckSubGameService";
    public static final String WS_REGISTER_SERVICE = "wsRegisterGameService";
    public static final String WS_RENEW_SERVICE = "wsRenewGameService";
    public static final String WS_CANCEL_SERVICE = "wsCancelGameService";
    public static final String WS_BUY_MORE_TURN_SERVICE = "wsBuyMoreTurnGameService";
    public static final String WS_GET_HISTORY_BUY_MORE = "wsGetHistoryGiftBuyMore";
    public static final String WS_LOGIN_OTP = "wsLoginOtpGameService";
    public static final String WS_REFRESH_TOKEN = "wsRefreshTokenGameService";
    public static final String WS_GET_OTP = "wsGetOtpGameService";
    public static final String WS_GET_ISDN = "wsGetIsdn";
    public static final String WS_ADD_USER_TOKEN = "wsAddUserToken";
                 
}
