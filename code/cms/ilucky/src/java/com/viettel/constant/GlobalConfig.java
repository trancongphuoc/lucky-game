/**
 *
 * handsome boy
 */

package com.viettel.constant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thu
 * @since Jun 11, 2015
 * @mail haind25@viettel.com.vn
 */
public class GlobalConfig {
    
    public static ResourceBundle resourceBundle;
    
    public synchronized static void config(InputStream inputStream) {
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
    
    public synchronized static void config(String configFile) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(configFile);
            config(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public synchronized static String get(String key) {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("config");
            
            // init country code with config database
            Set<String> keys = resourceBundle.keySet();
            
            Map<Object,Object> values = new HashMap<Object,Object>();
            for (String tmkey : keys) {
                String value = String.valueOf(resourceBundle.getObject(key));
                values.put(tmkey, value);
            }
            
//            CountryCode.config(values);
        }
        return resourceBundle.getString(key);
    }
    
}
