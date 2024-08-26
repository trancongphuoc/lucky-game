/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author duongdv7
 */
public class Config {

    private static Config config;
    Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(Config.class);
    private static final String WS_CONF_FILE = "../etc/config.cfg";
    public static final String CHARSET_NAME = "ISO-8859-1";
    public static final String CHARSET_ENCODE = "UTF-8";

    private Config() {
        try {
            String cfgFile = utils.Config.getConfigDir() + File.separator + WS_CONF_FILE;
            cfgFile = "D:\\PHUOC\\work\\i_lucky\\ilucky_lumitel-master\\ilucky_lumitel-master\\code\\LuckyGameBE\\etc\\config.cfg";
            properties.load(new FileInputStream(cfgFile));
        } catch (IOException ex) {
            logger.error("Load config error " + ex.getMessage(), ex);
        }
    }

    public static synchronized Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public int getApiNum() {
        return Integer.parseInt(properties.getProperty("apiNumber", "10"));
    }

    public String getUrl() {
        return properties.getProperty("WS_URL");
    }

    public int getStartTurnNo() {
        return Integer.parseInt(properties.getProperty("startTurnNo", "3"));
    }

    public int getWsCorePoolSize() {
        return Integer.valueOf(properties.getProperty("wsCorePoolSize", "1024"));
    }

    public int getWsMaximumPoolSize() {
        return Integer.valueOf(properties.getProperty("wsMaximumPoolSize", "3072"));
    }

    public int getWsKeepAliveTime() {
        return Integer.valueOf(properties.getProperty("wsKeepAliveTime", "15"));
    }

    public String getOtpUrl() {
        return properties.getProperty("OTP_URL");
    }

    public String getOtpUser() {
        return properties.getProperty("OTP_USERNAME");
    }

    public String getOtpPass() {
        return properties.getProperty("OTP_PASSWORD");
    }

    public String getOtpServiceId() {
        return properties.getProperty("OTP_SERVICE_ID");
    }

    public String getOtpSender() {
        return properties.getProperty("OTP_SENDER");
    }

    public String getOtpContentType() {
        return properties.getProperty("OTP_CONTENT_TYPE", "0");
    }

    public String getOtpStatus() {
        return properties.getProperty("OTP_STATUS", "1");
    }

    public String getOtpPrefix() {
        return properties.getProperty("OTP_PREFIX");
    }

    public String getOtpXml() {
        return properties.getProperty("OTP_XML", "http://tempuri.org/");
    }

    public int getOtpRetry() {
        return Integer.parseInt(properties.getProperty("OTP_RETRY", "0"));
    }

    public String getEnSms(String giftType) {
        String key = "EN_" + giftType + "_SMS";
        return properties.getProperty(key);
    }

    public String getSwSms(String giftType) {
        String key = "SW_" + giftType + "_SMS";
        return properties.getProperty(key);
    }

    public int isTrialVersion() {
        return Integer.parseInt(properties.getProperty("IS_TRIAL_VERSION"));
    }

    public String getGiftTypeAddTurn() {
        return properties.getProperty("GIFT_TYPE_ADD_TURN");
    }

    public String getSms(String lang, String giftCode) {
        String key = lang + "_" + giftCode;
        String value = properties.getProperty(key.toUpperCase());
        try {
            if (value != null) {
                value = new String(value.getBytes(CHARSET_NAME), CHARSET_ENCODE);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return value;
    }

    public int getNumAddTurn() {
        return Integer.parseInt(properties.getProperty("NUM_ADD_TURN"));
    }

    public String getListLanguage() {
        return properties.getProperty("LIST_LANGUAGE");
    }

    public int getAddExpireData() {
        return Integer.parseInt(properties.getProperty("ADD_EXPIRE_DATA"));
    }

    public int getAddExpire(String giftType) {
        return Integer.parseInt(properties.getProperty("ADD_EXPIRE_" + giftType));
    }

    public String getAccountId(String giftType) {
        return properties.getProperty(giftType + "_ACCOUNT_ID");
    }

    public int getFlatProcessLog() {
        return Integer.parseInt(properties.getProperty("FLAT_PROCESS_LOG"));
    }

    public int getFlatProcessTopup() {
        return Integer.parseInt(properties.getProperty("FLAT_PROCESS_TOPUP"));
    }
    public String getScheduleTime() {
        return properties.getProperty("SCHEDULE_TIME");
    }

    public long getProcessSleepTime() {
        return Long.parseLong(properties.getProperty("PROCESS_SLEEP_TIME", "1000"));
    }

    public int getNumRecordPerCycle() {
        return Integer.parseInt(properties.getProperty("NUM_RECORD_PER_CYCLE", "100"));
    }

    public int getDelayTime() {
        return Integer.parseInt(properties.getProperty("DELAY_TIME", "10"));
    }

    public String getLaptopProductCode() {
        return properties.getProperty("LAPTOP_PRODUCT_CODE");
    }

    public String getMaxThread() {
        return properties.getProperty("MAX_THREAD", "1000");
    }

    public String getMinThread() {
        return properties.getProperty("MIN_THREAD", "20");
    }

    public int getUmoneyBalanceCheck() {
        return Integer.parseInt(properties.getProperty("UMONEY_BALANCE_CHECK"));
    }

    public String getMessage(String lang, String messageKey) {
        String key = messageKey + "." + lang;
        String value = properties.getProperty(key.toUpperCase());
        try {
            if (value != null) {
                value = new String(value.getBytes(CHARSET_NAME), CHARSET_ENCODE);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return value;

    }
    
    
    public int getOtpLength() {
        return Integer.parseInt(properties.getProperty("OTP_LENGTH", "6"));
    }
    public int getIsCheckRegisterOrNot() {
        return Integer.parseInt(properties.getProperty("IS_CHECK_REG_OR_NOT"));
    }    
    public int getIsCheckTokenOrNot() {
        return Integer.parseInt(properties.getProperty("IS_CHECK_TOKEN_OR_NOT"));
    }
    public int getAdsCountTime() {
        return Integer.parseInt(properties.getProperty("ADS_COUNT_TIME"));
    }
    
    public int getAddTurnBuyMore() {
        return Integer.parseInt(properties.getProperty("ADD_TURN_BUY_MORE"));
    }
    public String getSubService() {
        return properties.getProperty("SUB_SERVICE");
    }
    
    public String getSubServiceBuy() {
        return properties.getProperty("SUB_SERVICE_BUY");
    }
    public String getCateBlank() {
        return properties.getProperty("CATE_BLANK");
    }
    
    public String getCateBuy() {
        return properties.getProperty("CATE_BUY");
    }
    
    public String getAmount() {
        return properties.getProperty("AMOUNT");
    }    

}
