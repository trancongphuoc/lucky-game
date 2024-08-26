/**
 *
 * handsome boy
 */

package com.nh.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.openide.util.Exceptions;

/**
 *
 * @author hadc <Apllication Development Department - Viettel Global>
 * @since Apr 27, 2015
 * @mail hadc@viettel.com.vn
 */
public class CountryCode {
    
    public final static String COUNTRY_CODE = "country_code";
    public final static String REPLACE_MOBILE_PREFIX = "replace_mobile_prefix";
    public final static String VIETTEL_RULES = "vietel_mobile_number_rules";
    
    private static CountryCode countryCode;
    
//    private final Map<String,String> values;
    
    private String _code = ""; //viet nam is 84
    private List<String> _replaceMobilePrefixCodes; //viet nam is 84,0, +84
    public List<String> _viettelRules; //viet nam is 84,0, +84
    
    private CountryCode(Map<Object,Object> values) {
//        this.values = values;
        Object tm = values.get(COUNTRY_CODE);
//        String tm = values.get(COUNTRY_CODE);
        if (null != tm && !"".equals(tm)) {
            _code = String.valueOf(tm).trim();
        }
        
        tm = values.get(REPLACE_MOBILE_PREFIX);
        if (null != tm && !"".equals(tm)) {
            List<String> codes = Arrays.asList(String.valueOf(tm).split(","));
            _replaceMobilePrefixCodes = codes;
        }
        
        tm = values.get(VIETTEL_RULES);
        if (null != tm && !"".equals(tm)) {
            List<String> codes = Arrays.asList(String.valueOf(tm).split(","));
            _viettelRules = codes;
        }
        
    }
    
    public static void config(String configFile) {
        try {
            Properties prop = new Properties();
            FileInputStream gencfgFile = new FileInputStream(configFile);
            prop.load(gencfgFile);
            config(prop);
        } catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
        }
        
    }
    
    public static void config(final Map<Object,Object> values) {
        if (countryCode == null) {
            countryCode = new CountryCode(values);
        } 
    }
    
    private static CountryCode getInstance() {
        if (countryCode == null) {
           throw new IllegalArgumentException("please config values for Country code");
        }
        
        return countryCode;
    }
    
    public static String getCountryCode() {
        return getInstance()._code;
    }
    
    public static List<String> getReplacePrefixCodes() {
        if (getInstance()._replaceMobilePrefixCodes == null) {
           throw new IllegalArgumentException("please config values for Country code");
        }
        return getInstance()._replaceMobilePrefixCodes;
    }
    
    public static List<String> getViettelRules() {
        if (getInstance()._viettelRules == null) {
           throw new IllegalArgumentException("please config values for Country code");
        }
        return getInstance()._viettelRules;
    }
      
    public static String formatMobile(String msisdn) {
        if(msisdn == null || "".equals(msisdn)) {
            return msisdn;
        }
        
        String value = msisdn;
        value = value.trim();
        value = value.replace(" ", "");
        List<String> replacePrefixs = getReplacePrefixCodes();
        for (String replacePrefix : replacePrefixs) {
            if (value.startsWith(replacePrefix)) {
                value = value.substring(replacePrefix.length());
                value = value.trim();
                break;
            }
        }
        
        return value;
    }
    
    public static boolean isViettelNumber(String msisdn) {
        String _msisdn = formatMobile(msisdn);
        
        List<String> viettelRules = getViettelRules();
        
        for(String viettelRule : viettelRules) {
            if (_msisdn.matches(viettelRule)) {
                return true;
            }
        }
        
        return false;
    }
    
    
}
