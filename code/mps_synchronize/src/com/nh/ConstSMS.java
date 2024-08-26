/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConstSMS {
    public static String INVALID ="Sorry, your number is invalid, please try select again number from 1-99. Thanks you!";
    public static String WINGAME ="Congratulations, you win 10.000KIP to your phone balance, thanks you!";
    public static String NOTWINGAME ="Your number is not wining, good luck next time. Thanks you!";
    public static String BANLANCENOTFULL ="Your balance in not enough to play game, please recharge and try again. Thanks you!";        
    public static String ERROR ="Sorry! System is busy now";
    
    private static String configFilePath;
    private static ResourceBundle resourceBundle;
    private static final Object obj = new Object();
    
    
    public static void config(String configFile) {
        configFilePath = configFile;
        FileInputStream fis;
        try {
            fis = new FileInputStream(configFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            config(isr);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void config(InputStreamReader reader) {
        try {
            resourceBundle = new PropertyResourceBundle(reader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
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
     
    public static String get(String key,String defaultValue) {
        synchronized(obj) {
            try {
                if (resourceBundle.containsKey(key)) {
                    return resourceBundle.getString(key);
                }
            } catch (Exception ex) {
            }
            return defaultValue;
        }
    }
     
     
    public static int getInt(String key, int defaultValue) {
        int value = defaultValue;
        String tm = get(key);
        if (tm != null) {
            value = Integer.parseInt(tm);
        }
        
        return value;
    }
}
