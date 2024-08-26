/*
 * define OS which run this module
 */
package com.nh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author hungtv45
 */
public class OSUtil {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));

    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
    

    public static String runCmd(String cmd)
    {
        String output = "";
        Process p = null;
        InputStream is = null;
        InputStream es = null;
        OutputStream os = null;
        try
        {
            if (OSUtil.isWindows()) {
                cmd = "cmd.exe /c  " + cmd;
            } 
            
            Runtime r = Runtime.getRuntime();
            p = r.exec(cmd);
            
            try
            {
                p.waitFor();
            }
            catch (InterruptedException x)
            {
                x.printStackTrace();
            }
            is = p.getInputStream();
            es = p.getErrorStream();
            os = p.getOutputStream();
            output = getStringFromInputStream(is);
            output = output + getStringFromInputStream(es);
        }
        catch (IOException ex)
        {
//            ex.printStackTrace();
        }
        finally
        {
            if (p != null)
            {
                p.destroy();
                p = null;
            }
            try
            {
                if (is !=null ) {is.close(); is = null;}
                if (es !=null ) {es.close(); es = null;}
                if (os !=null ) {os.close(); os = null;}
            }
            catch (IOException iex)
            {
            }
        }
        return output;
    }
    
    public static String runCmd(String[] cmds)
    {
        String output = "";
        Process p = null;
        InputStream is = null;
        InputStream es = null;
        OutputStream os = null;
        try
        {
            
            Runtime r = Runtime.getRuntime();
            p = r.exec(cmds);
            
            try
            {
                p.waitFor();
            }
            catch (InterruptedException x)
            {
                x.printStackTrace();
            }
            is = p.getInputStream();
            es = p.getErrorStream();
            os = p.getOutputStream();
            output = getStringFromInputStream(is);
            output = output + getStringFromInputStream(es);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (p != null)
            {
                p.destroy();
                p = null;
            }
            try
            {
                if (is !=null ) {is.close(); is = null;}
                if (es !=null ) {es.close(); es = null;}
                if (os !=null ) {os.close(); os = null;}
            }
            catch (IOException iex)
            {
            }
        }
        return output;
    }
    
    
    public static String runCmd2(String cmd){
        String hasLog = null;
        if (OSUtil.isWindows()) {
            hasLog = OSUtil.runCmd(cmd);
        } else {
            String[] logFileCmdslogFileCmds = new String[] {"/bin/sh", "-c", cmd};
            hasLog = OSUtil.runCmd(logFileCmdslogFileCmds);
        }
        return hasLog;
    }
    
    
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
