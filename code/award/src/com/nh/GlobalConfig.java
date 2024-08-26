/**
 *
 * handsome boy
 */

package com.nh;

import com.nh.util.CommonUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadc <Apllication Development Department - Viettel Global>
 * @since Jun 11, 2015
 * @mail hadc@viettel.com.vn
 */
public class GlobalConfig {
    public final static String DEFAULT_CONFIG_APP = "etc/config.properties";
    
    
    private static final Object obj = new Object();
    
    public static String configFilePath;
    
    private static ResourceBundle resourceBundle;
    private static String databasePath;
    private static String schedulerPath;
    private static String log4jPath;
    private static String tempPath;
    
    private static String backup_folder;
    
    private static int excutorLength;
    private static int cdrReadThread;
    private static int callProcessThread;
    private static Boolean remakefileOnError = null;
    private static int fetchSize = -1;
    private static String CDR_SEPARATE = "\\|";
    private static String CDR_PATH;
    private static String prosoapUrl;
    private static String wsAddData;
    private static String wsAddPromotionData;
    private static String wsUssdInvite;
    private static String ussdgwUrl;
    
    private static String sms_url;
    private static String sms_username;
    private static String sms_password;
    private static String sms_shortcode;
    private static String sms_alias;
    
    private static String sms_msg_notify;
    
    private static String advertise_hour;
    
    
    public static int cdrTimeout;
    public static int cdrDelete = -1;
    public static int advertiseNumber = -1;
    public static int advertiseInterval = -1;
    
     public final static int DistansceBusycall_DEFAULT = 8* 3600 * 1000; //8h
     public static int distansceBusycall = -1;
    
    private static void config(InputStream inputStream) {
        try {
            resourceBundle = new PropertyResourceBundle(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void config(String configFile) {
        configFilePath = configFile;
        FileInputStream fis;
        try {
            fis = new FileInputStream(configFile);
            config(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static String get(String key) {
        synchronized(obj) {
            try{
                return resourceBundle.getString(key);
            } catch(MissingResourceException ex){
            }
            return null;
        }
    }

    public static String getDatabasePath() {
        if (databasePath == null){
            databasePath = get("path_database");
        }
        return databasePath;
    }
    

    public static int getExcutorLength() {
        if (excutorLength == 0){
            excutorLength = 1;
            String tm = get("thread_size_scan_file");
            if (tm != null) {
                excutorLength = Integer.parseInt(tm);
            }
        }
        return excutorLength;
    }
    
    public static int getInt(String key, int defaultValue) {
        int value = defaultValue;
        String tm = get(key);
        if (tm != null) {
            value = Integer.parseInt(tm);
        }
        
        return value;
    }

    static String getLog4jPath() {
        if (log4jPath == null){
            log4jPath = get("path_log4j");
        }
        return log4jPath;
    }

    public static int getCdrReadThread() {
        if (cdrReadThread == 0){
            cdrReadThread = CommonUtil.getNumCoreCPU();
            String tm = get("thread_size_cdr_process");
            if (tm != null) {
                cdrReadThread = Integer.parseInt(tm);
            }
        }
        return cdrReadThread;
    }
    
    public static int getCallReadThread() {
        if (callProcessThread == 0){
            callProcessThread = CommonUtil.getNumCoreCPU();
            String tm = get("thread_size_call_process");
            if (tm != null) {
                callProcessThread = Integer.parseInt(tm);
            }
        }
        return callProcessThread;
    }
    public static String getBackupFolder() {
        if (backup_folder == null){
                backup_folder = get("backup_folder");
        }
        return backup_folder;
    }
    
    public static boolean remakeFileOnError() {
        if (remakefileOnError == null){
            int tm = getInt("remakefile_on_error", 1 );
            remakefileOnError = tm > 0;
        }
        return remakefileOnError;
    }

    public static int getFetchSize() {
        if (fetchSize < 0 ){
            fetchSize = getInt("fetch_size", 1000 );
        }
        return fetchSize;
    }

    public static int getDistanceBusycall() {
        if (distansceBusycall < 0 ){
            distansceBusycall = getInt("distance_busy_call", DistansceBusycall_DEFAULT );
        }
        
        return distansceBusycall;
    }

    public synchronized static String getCdrPath() {
         if (CDR_PATH == null){
                CDR_PATH = get("CDR_PATH");
        }
        return CDR_PATH;
    }

    public synchronized static String getProsoapUrl() {
         if (prosoapUrl == null){
                prosoapUrl = get("Prosoap_Url");
        }
        return prosoapUrl;
    }

    public static long getCdrTimeout() {
        if (cdrTimeout == 0){
                cdrTimeout = getInt("cdr_timeout", 300000);//default 5 minutes
        }
        return cdrTimeout;
    }

    public static int getAdvertiseInterval() {
        if (advertiseInterval < 0 ){
            advertiseInterval = getInt("advertise_interval", 2000 );
        }
        return advertiseInterval;
    }

    public static int getAdvertiesNumber() {
         if (advertiseNumber < 0 ){
            advertiseNumber = getInt("advertise_number ", 5 );
        }
        return advertiseNumber;
    }

    public static boolean isDeleteFile() {
         if (cdrDelete < 0 ){
            cdrDelete = getInt("cdr_delete", 0);
        }
        return cdrDelete > 0;
    }
    
    public synchronized static String getWsAddData(){
        if (wsAddData == null){
            wsAddData = get("ws_add_data");
        }
        return wsAddData;
    }
    
    public synchronized static String getWsAddPromotionData(){
        if (wsAddPromotionData == null){
            wsAddPromotionData = get("ws_add_data_promotion_register");
        }
        return wsAddPromotionData;
    }
    
    
    public synchronized static String getWsUssdInvite(){
        if (wsUssdInvite == null){
            wsUssdInvite = get("ws_ussd_invite");
        }
        return wsUssdInvite;
    }
    
    public synchronized static String getUssdgwUrl(){
        if (ussdgwUrl == null){
            ussdgwUrl = get("ussdgw_url");
        }
        return ussdgwUrl;
    }
    
    
    public static String getSmsUrl() {
        if (sms_url == null){
            sms_url = get("sms.url");
        }
        return sms_url;
    }
    
    public static String getSmsUsername() {
        if (sms_username == null){
            sms_username = get("sms.username");
        }
        return sms_username;
    }
    
    public static String getSmsPassword() {
        if (sms_password == null){
            sms_password = get("sms.password");
        }
        return sms_password;
    }
    
    public static String getSmsShortcode() {
        if (sms_shortcode == null){
            sms_shortcode = get("sms.shortcode");
        }
        return sms_shortcode;
    }
    
    public static String getSmsAlias() {
        if (sms_alias == null){
            sms_alias = get("sms.alias");
        }
        return sms_alias;
    }
    
    public static String getContext() {
        if (sms_msg_notify == null){
            sms_msg_notify = get("sms_notify_credit");
        }
        return sms_msg_notify;
    }
    
    public static String getSmsAdvertis() {
        return get("sms_content_invite");
    }
    
    public static String getAdvertiseHour() {
        if (advertise_hour == null){
            advertise_hour = get("advertise_hour");
        }
        return advertise_hour;
    }

    public static String getSchedulerPath() {
        if (schedulerPath == null){
            schedulerPath = get("path_scheduler");
        }
        return schedulerPath;
    }


}
