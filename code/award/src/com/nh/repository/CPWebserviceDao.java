/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.repository;

import com.nh.bean.CPWebservice;
import com.nh.cache.HCache;


public class CPWebserviceDao extends GenericDao<CPWebservice>{
   
    private static final HCache<String, CPWebservice> cache_name = 
            new HCache<>("CPWebserviceDao_findByName", 5, 100, 20);
    
    public CPWebservice findByName(String webserviceName) {
        CPWebservice webservice = cache_name.get(webserviceName);
        if (webservice != null) {
            return webservice;
        }
        webservice = findByColumName("WEBSERVICE_NAME", webserviceName);
        if (webservice != null) {
            cache_name.put(webserviceName, webservice);
        }
        return webservice;
    }
    
}
