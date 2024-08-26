package com.viettel.luckydraw.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author vas_khanhlp1
 */
public class AppContext {

    private static AppContext instance;
    private ApplicationContext context;
    private static final Logger logger = Logger.getLogger(AppContext.class);

    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    private AppContext() {
        try {
            String adapterConfigPath = "../etc/root.xml";

            context = new FileSystemXmlApplicationContext(
                    new String[]{adapterConfigPath});
            logger.info("Load file config : " + adapterConfigPath + " success");

        } catch (Exception ex) {
            logger.error("error = " + ex);
        }

    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }
}
