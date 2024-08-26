/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.bean.CdrLog;
import com.nh.proccess.InsertCdrProcess;
import com.nh.soap.SoapRequest;
import com.nh.util.CountryCode;
import com.nh.util.EncryptUtil;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;

public class RsaHandler extends AbstractHandler {

    public RsaHandler(SoapRequest request) {
        super(request);
    }

    @Override
    public String process(SoapRequest request) {
        String value = "0";
        try {
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                log.error(request.getClientIp() + " , send Method: " + request.getMethod());
                return "1|Method:  " + request.getMethod();
            }
            String xml = request.getOriginData();
            log.info(request.getClientIp() + " ,receive: " + xml);
            
        } catch (Exception ex) {
            log.error("error when excute rsa to mps", ex);
            value = "1|" + ex.toString();
        }

        return value;
    }

    public boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
}
