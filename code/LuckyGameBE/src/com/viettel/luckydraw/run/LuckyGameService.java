/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.run;


//import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;


import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import com.viettel.luckydraw.process.InsertLogProcess;
//import com.viettel.luckydraw.process.TopupProcess;
import com.viettel.luckydraw.util.Config;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author manhnv23
 */
public class LuckyGameService {

    public final static Logger serverLog = Logger.getLogger(LuckyGameService.class);
    private InsertLogProcess logProcess;
//    private TopupProcess topupProcess;
    private static LuckyGameService instance;

    public static synchronized LuckyGameService getInstance() {
        if (instance == null) {
            instance = new LuckyGameService();
        }
        return instance;
    }

    public static void startWs() {

        try {

            final Map<String, String> initParams = new HashMap<String, String>();
            System.setProperty("com.sun.grizzly.minWorkerThreads", Config.getInstance().getMinThread());
            System.setProperty("com.sun.grizzly.maxThreads", Config.getInstance().getMaxThread());
            initParams.put("com.sun.jersey.config.property.packages", "com.viettel.luckydraw.ws");
            serverLog.info("Starting lucky game api ........");
            
//           final  ResourceConfig rc = new ResourceConfig();
//            rc.register(LuckyGameWS.class);
         
            
            
  
            String baseUri = Config.getInstance().getUrl();
            if (baseUri != null) {
                GrizzlyWebContainerFactory.create(baseUri, initParams);
                
//                  GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri),rc, true);
                
                serverLog.info(String.format("Started Lucky Game with available at %s", baseUri, baseUri));
            }          

        } catch (Exception ex) {
            serverLog.error("Started Lucky Game Error" + ex.getMessage(), ex);
        }
    }

    public void start() {
        if (Config.getInstance().getFlatProcessLog() == 1) {
         //   logProcess = new InsertLogProcess("InsertLogProcess");
         //   logProcess.start();
        }
//        if (Config.getInstance().getFlatProcessTopup() == 1) {
//            topupProcess = new TopupProcess("TopupProcess");
//            topupProcess.start();
//        }
        startWs();
    }

    public void stop() {
        if (logProcess != null) {
            logProcess.stop();
        }
//        if (topupProcess != null) {
//            topupProcess.stop();
//        }
    }
}
