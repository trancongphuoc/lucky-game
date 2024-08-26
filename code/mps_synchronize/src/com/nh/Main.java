/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh;

import com.nh.backend.database.ConnectionPoolManager;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.soap.HttpListener;
import com.nh.soap.handle.Authen;
import com.nh.soap.handle.BuyOneTime;
import com.nh.soap.handle.CancelHandler;
import com.nh.soap.handle.MOListen;
import com.nh.soap.handle.Modbalance;
import com.nh.soap.handle.OtpHandle;
import com.nh.soap.handle.RegisterCancelHandler;
import com.nh.soap.handle.RenewHandler;
import com.nh.soap.handle.RsaHandler;
import com.nh.soap.handle.SqlViewHandle;
import com.nh.soap.handle.UpointAdd;
import com.nh.util.CountryCode;
import com.nh.util.MyLog;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    static final String copyright =
            "Developed by (@) Hadc 2020\n";

    static {
        System.out.println(copyright);
    }
    
   public final static Map<String, String> handleMapper = new HashMap<>();
    /**
     * @param args the command line arguments$
     */
    public static void main(String args[]) throws Exception {
        System.out.println("=====================================================: " );
//        System.out.println("libpath: " + System.getProperty("java.library.path"));
        configFile();
        mapHandle();
        start();
    }
    
    public static Map<String, String> mapHandle() {
       synchronized (handleMapper) {
           if (handleMapper.isEmpty()) {
               handleMapper.put("/RegisterCancel", RegisterCancelHandler.class.getName());
               handleMapper.put("/Renew", RenewHandler.class.getName());
               handleMapper.put("/RsaHandler", RsaHandler.class.getName());
               handleMapper.put("/BuyOneTime", BuyOneTime.class.getName());
               handleMapper.put("/CancelHandler", CancelHandler.class.getName());
               handleMapper.put("/MOListen", MOListen.class.getName());
               handleMapper.put("/SqlViewHandle", SqlViewHandle.class.getName());
               handleMapper.put("/OtpHandle", OtpHandle.class.getName());
               handleMapper.put("/Authen", Authen.class.getName());
               handleMapper.put("/Modbalance", Modbalance.class.getName());
               handleMapper.put("/UpointAdd", UpointAdd.class.getName());
           }
           
           return handleMapper;
       }
    }

    private static void configFile() throws Exception {
        String home = System.getProperty("home");
        if (home != null && !home.isEmpty()) {
            if (!home.endsWith(File.separator)){
                home = home + File.separator;
            }
            GlobalConfig.HOME_CONFIG = home;
        }

        PropertyConfigurator.configure(GlobalConfig.HOME_CONFIG + GlobalConfig.CONFIG_LOG4J);
        GlobalConfig.config(GlobalConfig.HOME_CONFIG + GlobalConfig.CONFIG_APP);
        ConnectionPoolManager.loadConfig(GlobalConfig.HOME_CONFIG + GlobalConfig.CONFIG_DATABASE);
        CountryCode.config(GlobalConfig.HOME_CONFIG + GlobalConfig.CONFIG_APP);
        ConstSMS.config(GlobalConfig.HOME_CONFIG + GlobalConfig.CONFIG_LANGUAGE);
    }

    private static void start() throws Exception{
        String ports = GlobalConfig.get("http_port");

        String[] lports = ports.split(",");
        int i = lports.length;
        for (int j = 0; j < i; j++) {
            HttpListener http = new HttpListener(Integer.parseInt(lports[j]));
            http.start();
            MyLog.Infor("Start listen on port: " + lports[j]);
        }
        Vector<String> v = ConnectionPoolManager.getConnnectionString();
        if (v == null) {
            throw new Exception("can not get string connection db");
        }
        DatabaseConnectionPool.config(v, GlobalConfig.getInt("db_pool_size", 10));
    } 
}
