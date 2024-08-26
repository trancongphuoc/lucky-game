/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.util;

import com.nh.GlobalConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 
 */
public class WebserviceTemplate {
    private static String PATH = "etc/template/";
    
    private final static String TEMPLATE_EMPTY = "";
//    private final static String TEMPLATE_SURFIX = ".xml";
    
    static WebserviceTemplate webserviceTemplate;
    
    private Map<String,String> map = new HashMap<String, String>();
    private final Object lock = new Object();
    
    private WebserviceTemplate() {
       PATH = GlobalConfig.get("ws_template");
    }
    
    public synchronized static void setPath(String path) {
        PATH = path;
    }
    
    public synchronized static WebserviceTemplate getInstance() {
        if (webserviceTemplate == null) {
            webserviceTemplate = new WebserviceTemplate();
        }
        return  webserviceTemplate ;
    }
    
    public String get(String key) {
        synchronized(lock) {
            String teamplate = map.get(key);
            if (teamplate != null) {
                return teamplate;
            }
            
            if (map.size() > 10000) {
                return TEMPLATE_EMPTY;
            }
            
            teamplate = loadTempalte(key);
            map.put(key, teamplate);
            
            return teamplate;
        }
    }
    
    private String loadTempalte(String key) {
        String returnValue = TEMPLATE_EMPTY;
//        String templateFile = PATH + key + TEMPLATE_SURFIX;
        String templateFile = PATH + key;
        
        BufferedReader br = null;
        try {
            File file = new File(templateFile);
            if (!file.exists()) {
                return TEMPLATE_EMPTY;
            }
            br = new BufferedReader(new FileReader(file));
            
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            returnValue = sb.toString();
        } catch (FileNotFoundException ex) {
            MyLog.Error(ex);
        } catch (IOException ex) {MyLog.Error(ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception x2) {
                    MyLog.Error(x2);
                }
            }
        }
        
        return returnValue;
    }

}
