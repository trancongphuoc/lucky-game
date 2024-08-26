/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.soap.handle;

import com.nh.backend.process.BlackListManager;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import org.apache.log4j.Logger;


public class BlackListCheck extends AbstractHandler {
protected final static Logger log = Logger.getLogger(BlackListCheck.class);
    public BlackListCheck(SoapRequest request) {
        super(request);
    }

    /**
     * 0 not in blacklist
     * 1 default error
     * 4 black list
     */
    @Override
    public String process(SoapRequest request) {
         String value = "0";
        try {
            log.debug("BlackListCheck , got: " + request.getRawXml());
            
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1";
            }
            
            String msisdn = request.getValue("msisdn", null);
            if (msisdn == null) {
                log.error(request.getClientIp() + " ,BlackListCheck send fail msisdn: " + request.getRawXml());
                return "1";
            }
            msisdn = CountryCode.formatMobile(msisdn);
            
            if (BlackListManager.getInstance().isBlackList(msisdn)) {
                log.info("BlackListCheck blacklist isBlackList: " + msisdn);
                return "4";
            }
            
            log.info("BlackListCheck not isBlackList: " + msisdn);
        } catch (Exception ex) {
            value = "1";
            log.error("update sign,update error:" , ex);
        }
        
        return value;
    }
    
}
