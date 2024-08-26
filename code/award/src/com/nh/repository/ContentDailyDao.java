/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.repository;

import com.nh.bean.ContentDaily;
import com.nh.database.DB;
import java.util.List;

/**
 *
 * @author hadc
 */
public class ContentDailyDao  extends GenericDao<ContentDaily> {
    
    public List<ContentDaily> getContentDailyToday(){
        String sql = "SELECT  *  from V_CONTENT_DAILY_TODAY " ;
        List<ContentDaily> es = DB.query(sql,  mapper);
        if (es != null && !es.isEmpty()){
            return es;
        }
        
        return null;
    }
    
}
