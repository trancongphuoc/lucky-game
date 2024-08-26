/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh;

import com.nh.database.ConnectionPoolManager;
import com.nh.database.DatabaseConnectionPool;
import com.nh.excutor.TaskManagement;
import com.nh.soap.HttpListener;
import com.nh.util.CountryCode;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author hathutt
 */
public class Start {
    private static final Logger log = Logger.getLogger(Start.class);
     
    static final String copyright =
            "======================================================================\n"
            + "Developed by: ken\n"
            + "======================================================================\n";

    static {
        System.out.println(copyright);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String tmConfigFile = System.getProperty("config_file");
        if (tmConfigFile == null) {
            tmConfigFile = GlobalConfig.DEFAULT_CONFIG_APP ;
        }
        
        GlobalConfig.config(tmConfigFile);
        
        PropertyConfigurator.configure(GlobalConfig.getLog4jPath());
        
        log.info(copyright);
        log.info("log home config: " + tmConfigFile);
        log.info("log CONFIG_FILE: " + tmConfigFile);
        try {
            ConnectionPoolManager.loadConfig(GlobalConfig.getDatabasePath());
            Vector<String> v = ConnectionPoolManager.getConnnectionString();
            if (v == null) {
                throw new Exception("can not get string connection db");
            }
        
            DatabaseConnectionPool.config(v, GlobalConfig.getInt("db_pool_size", 10));
        } catch (Exception ex) {
            log.error("cannot connect to database", ex);
        }
        try {
            Properties prop = new Properties();
            FileInputStream gencfgFile = new FileInputStream(tmConfigFile);
            prop.load(gencfgFile);
            CountryCode.config(prop);
            gencfgFile.close();
        } catch (Exception ex) {
        }
        TaskManagement.getInstance().start();
//        TaskExcutor.getInstance().start();
//        TaskExcutorDatabase.getInstance().start();
//        startHttpListen();
    }
    
    private static void startHttpListen() {
        String ports = GlobalConfig.get("http_port");
        HttpListener http = new HttpListener(Integer.parseInt(ports));
        http.start();
        log.info("http linsten on: " + ports);
    }
    
    
}
