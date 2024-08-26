/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.run;

import com.viettel.luckydraw.process.InsertLogProcess;
import com.viettel.security.PassTranformer;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author manhnv23
 */
public class Start {

    public static void main(String[] args) {
//        PropertyConfigurator.configure("../etc/log4j.conf");
        LuckyGameService.getInstance().start();
         
    }
}
