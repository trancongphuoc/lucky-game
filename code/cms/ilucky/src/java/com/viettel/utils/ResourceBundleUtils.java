/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.utils;

import java.util.ResourceBundle;


/**
 *
 * @author NhatNT
 */
public class ResourceBundleUtils {
 static private ResourceBundle rb = null;
    /** Creates a new instance of ResourceBundleUtils */
    public ResourceBundleUtils()
    {

    }

    public static String getConfigResource(String key){
        rb = ResourceBundle.getBundle("config");
        return rb.getString(key);
    }    
}
