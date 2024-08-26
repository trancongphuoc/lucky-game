/**
 *
 * handsome boy
 */

package com.nh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadc 
 */
public class GlobalConfig {
    public static String HOME_CONFIG = "etc/";
    
    public final static String CONFIG_APP = "config.properties";
    public final static String CONFIG_LOG4J = "log4j.properties";
    public final static String CONFIG_DATABASE = "database.xml";
    public final static String CONFIG_LANGUAGE = "language.properties";
    
    private static final Object obj = new Object();
    
    public static String configFilePath;
    
    public static ResourceBundle resourceBundle;
    public static String sms_url;
    public static String sms_username;
    public static String sms_password;
    public static String sms_shortcode;
    public static String sms_alias;
    
    public static String sql_escape = null;
    
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
            try {
                return resourceBundle.getString(key);
            } catch (Exception ex) {
            }
            return null;
        }
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
    
    public static String getSql_escape() {
        if (sql_escape == null){
            sql_escape = get("sql_escape");
        }
        return sql_escape;
    }
    
    public static int getInt(String key, int defaultValue) {
        int value = defaultValue;
        String tm = get(key);
        if (tm != null) {
            value = Integer.parseInt(tm);
        }
        
        return value;
    }

    private static  Set<String>  gameDays;
    private static  Set<String>  gameHours;
    
    public synchronized static Set<String> getGameDay() {
        if (gameDays != null) {
            return gameDays;
        }

        String dayOfMonthStr = get("day_game");
        String[] dayOfMonthStrs = dayOfMonthStr.split(",");
        gameDays = new HashSet<>(Arrays.asList(dayOfMonthStrs));
        return gameDays;
    }
    
    public synchronized static Set<String> getGameHour() {
        if (gameHours != null) {
            return gameHours;
        }
        String hourOfDayStr = get("day_game_hour");
        String[] hourOfDayStrs = hourOfDayStr.split(",");
        gameHours = new HashSet<>(Arrays.asList(hourOfDayStrs));
        return gameHours;
    }
    
    public static String VASI_IP_SERVER = null;
    public static String getIPServer(){
        if (VASI_IP_SERVER != null) {
            return VASI_IP_SERVER;
        }
        try {
            VASI_IP_SERVER = getFirstNonLoopbackAddress(false);
        } catch (Exception ex) {
            VASI_IP_SERVER = "127.0.0.1";
        }
        
        return VASI_IP_SERVER;
    }
    
    private static final String IP_PREFIX = "10.";
    
    private static String getFirstNonLoopbackAddress(boolean preferIPv6) throws SocketException, UnknownHostException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        InetAddress addr = null;
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
                addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        String address = addr.getHostAddress() ;
                        if (!address.startsWith(IP_PREFIX)) {
                             continue;
                        }
                        return address;
                    }
                    if (addr instanceof Inet6Address) {
                        if (!preferIPv6) {
                            continue;
                        }
                        return addr.getHostAddress();
                    }
                }
            }
        }
        if (addr != null) {
            return addr.getHostAddress();
        }
        return InetAddress.getLocalHost().getHostAddress();
    }
}
