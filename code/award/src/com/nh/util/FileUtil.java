package com.nh.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class FileUtil {
    
    private final static Logger log = Logger.getLogger(FileUtil.class);
    
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
        
}
