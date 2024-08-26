/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.util;

import com.nh.GlobalConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class CommonUtil {
    
    private final static Logger log = Logger.getLogger(CommonUtil.class);
    
    public static boolean isNumeric(String strNumber) {
//        return strNumber != null && strNumber.matches("[0-9]+$");
        return strNumber != null && strNumber.matches("-?[1-9]\\d*|0");
    }
    
    public static String getPath(String rootPath, String folderName) {
//        File f = new File(rootPath + File.separator + folderName);
//            if (!f.exists() || !f.isDirectory()) {
//                f.mkdir();
//            }
            
        return rootPath + File.separator + folderName;
    }
    
    public static String getPathOnCreate(String rootPath, String folderName) {
        File f = new File(rootPath + File.separator + folderName);
        if (!f.exists() || !f.isDirectory()) {
            f.mkdir();
        }
            
        return f.getAbsolutePath();
    }
    
    public static void mkdirs(String path) {
        try {
            File f = new File(path);
            f.mkdirs();
        } catch(Exception ex) {
            log.error("mkdirs " + path, ex);
        }
    }
    
    public static void deleteOnExit(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            delete(filePath);
        }
    }
    
    public static int getNumCoreCPU() {
        Runtime rt = Runtime.getRuntime();
        return rt.availableProcessors();
    }
    
    
    public static void move(String filePath,String targetPath) {
        String mvCmd;
        if (OSUtil.isWindows()) {
            String tmfilePath = filePath.replace("/", "\\");
            String tmtargetPath = targetPath.replace("/", "\\");
            
            mvCmd = "cmd.exe /c  move " + validPathForWindow(tmfilePath) 
                    + " " + validPathForWindow(tmtargetPath);
        } else {
            mvCmd = "mv " + filePath + " " + targetPath;
        }
        
        OSUtil.runCmd(mvCmd);
    }
    
    private static String validPathForWindow(String path) {
        String value = path;
        boolean valid = path != null && path.startsWith("\"") && path.endsWith("\"");
        if (valid) {
            return value;
        }
        
        if (!value.startsWith("\"")) {
            value = "\"" + value;
        }
        
        if (!value.endsWith("\"")) {
            value += "\"";
        }
        
        return value;
    }
    
    public static void delete(String filePath) {
        String mvCmd;
        if (OSUtil.isWindows()) {
            filePath = filePath.replace("/", "\\");
            mvCmd = "cmd.exe /c  del " + validPathForWindow(filePath);
        } else {
            mvCmd = "rm -f " + filePath ;
        }
        
        OSUtil.runCmd(mvCmd);
    }
    
    public static boolean isLocked(String filePath) {
        boolean isLocked = true;
        RandomAccessFile fos=null;
        try {
            File file = new File(filePath);
            if (!file.isDirectory() && file.exists()) {
                fos = new RandomAccessFile(file, "rw");
                isLocked = false;
            }
        } catch (FileNotFoundException e) {
            log.error(e);
        } catch (SecurityException e) {
            log.error(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {}
        }
        
        return isLocked;
    }
    
    private static final Map<String, String> CONTEXT_PARAMS = new HashMap<String, String>();
    static {
        CONTEXT_PARAMS.put("YYYYMM","yyyyMM");
        CONTEXT_PARAMS.put("YYMM","yyMM");
        CONTEXT_PARAMS.put("YYYYMMDDHH24MMSS", "yyyyMMddhhmmss");
        CONTEXT_PARAMS.put("YYYYMMDD:DD-1", "yyyyMMdd");
        CONTEXT_PARAMS.put("YYYYMMDD:DD-2", "yyyyMMdd");
        CONTEXT_PARAMS.put("YYYYMMDD", "yyyyMMdd");
//        CONTEXT_PARAMS.put("YYYYMM01:MM-1", "YYYYMM01");
//        CONTEXT_PARAMS.put("YYYYMM01:MM+1", "YYYYMM01");
    }
            
    private static final String JSch_COMMAND_SPLIT ="->";
    private static Map<String,String> initContext() {
//        Date today = new Date();
        Map<String,String> context = new HashMap<String,String>();
        for (Map.Entry<String, String> entry : CONTEXT_PARAMS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            SimpleDateFormat format = new SimpleDateFormat(value);
            Date date = getDate(key);
            String val = format.format(date);
            String contextKey = "${" + key + "}";
            context.put(contextKey, val);
//            log.info("fixed conext: " + contextKey + " => " + val );
        }
        
        int dynamicCtxLenght = GlobalConfig.getInt("conext.length", 0);
        if (dynamicCtxLenght > 0) {
            for (int i= 0; i < dynamicCtxLenght; i++) {
                String tmp = GlobalConfig.get("conext." +(i+1));
                String[] tmps = tmp.split(JSch_COMMAND_SPLIT);
                if (tmps.length != 2 ) {
                    continue;
                }
                
                String key = tmps[0].trim();
                String value = tmps[1].trim();

                SimpleDateFormat format = new SimpleDateFormat(value);
                Date date = getDate(key);
                String val = format.format(date);
                String contextKey = "${" + key + "}";
                context.put(contextKey, val);
//                log.info("dynamic conext: " + contextKey + " => " + val );
            }
        }
        
        return context;
    }
    
    
    public static final int ONE_MINUTE = 60 * 1000;
    public static final int ONE_HOUR = 60 * 60 * 1000;
    public static final int ONE_DAY = 24 * 60 * 60 * 1000;
    
    private static Date getDate(String dateParameter) {
        Date date = null;
        
        if ("YYYYMMDD:DD-1".equals(dateParameter)) {
            long time = System.currentTimeMillis() - 1 * ONE_DAY - 500;
            date = new Date(time);
//            log.debug(dateParameter);
        } else if ("YYYYMMDD:DD-2".equals(dateParameter)) {
            date = new Date(System.currentTimeMillis() - 2 * ONE_DAY);
        } else if (dateParameter.startsWith("YYYYMMDD:DD")) {
            String tmp = dateParameter.replace("YYYYMMDD:DD", "");
            int nagetive = 1;
            if (tmp.startsWith("-")) {
                tmp = tmp.replace("-", "");
                nagetive = -1;
            } 
            
            if (CommonUtil.isNumeric(tmp) ) {
                int deltalDay = Integer.parseInt(tmp);
                long time = System.currentTimeMillis();
                for (int i = 0; i < deltalDay; i++) {
                        time = time + nagetive  * ONE_DAY ;
                }
                date = new Date(time);
            } else {
                date = new Date();
            }
        }else {
            date = new Date();
        }
        
        return date;
    }
    
    public static String passContext(String message) {
        Map<String,String> params = initContext();
        String result = message;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String k = entry.getKey();
            String val = entry.getValue();
            result = result.replace(k, val);
        }
        
        return result;
    }
    
    public static HashSet<String> convertHashSet(String value) {
        HashSet<String> result = new HashSet<String>();
        Collections.synchronizedSet(result);
        try {
            if (value == null) {
                return result;
            }
            String temp[] = value.trim().split(",");
            for (int i=0; i<temp.length; i++) {
                if (temp[i] != null && !temp[i].trim().isEmpty()) {
                    result.add(temp[i].toUpperCase().trim());
                }
            }
        } catch (Exception ex) {
            log.error("return empty Hashset",ex);
        }
        return result;
    }
    
}
