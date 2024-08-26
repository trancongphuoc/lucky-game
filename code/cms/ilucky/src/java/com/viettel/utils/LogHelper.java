/**
 * 
 */
package com.viettel.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogHelper {

    static private boolean initialized = false;

    public LogHelper() {
        //super();
    }

    private static synchronized void init() {
        if (!initialized) {
            PropertyConfigurator.configureAndWatch("log4j.properties", 60000);
            initialized = true;
        }
    }

    public static synchronized void init(String logFile) {
        if (!initialized) {
            PropertyConfigurator.configureAndWatch(logFile, 60000);
            initialized = true;
        }
    }

    public static synchronized Logger getLogger(Class component) {
        if (!initialized) {
            init("log4j.properties");
        }
        return Logger.getLogger(component);
    }

    public static synchronized Logger getLogger(String componentname) {
        if (!initialized) {
            init("log4j.properties");
        }
        return Logger.getLogger(componentname);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }
}
