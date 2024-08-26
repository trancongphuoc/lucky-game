/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.util;

import com.viettel.security.PassTranformer;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *
 * @author quangvx
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private String encryptFlag = ".enc";
    private static final Logger logger = Logger.getLogger(PropertyConfigurer.class);

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
          
        try {
            String value;
             
            if (placeholder.endsWith(encryptFlag)) {
                value = PassTranformer.decrypt(props.getProperty(placeholder));
               
            } else {
                value = props.getProperty(placeholder);
            }
            return value;
        } catch (Exception ex) {
            logger.error("ERR_PLACE_HOLDER: " + ex.getMessage(), ex);
            return convertPropertyValue(props.getProperty(placeholder));
        }
    }

    public void setEncryptFlag(String encryptFlag) {
        this.encryptFlag = encryptFlag;
    }
}
