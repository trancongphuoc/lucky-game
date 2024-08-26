/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.Sub;
import com.viettel.luckydraw.util.AppContext;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author vtt-cntt-l-56
 */
public class BccsPosDAO {
     private static BccsPosDAO instance;
    private DataSource dataSource;
    public static final Logger log = Logger.getLogger(BccsPosDAO.class);
     public synchronized static BccsPosDAO getInstance() {
        if (instance == null) {
            try {
                instance = (BccsPosDAO) AppContext.getInstance().getContext().getBean("BccsPosDAO");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return instance;
    }
  
     public String getProductCode(String isdn){
         long startTime = System.currentTimeMillis();
      List<Sub> listSub = new ArrayList<Sub>();
      try{
          String sql = "SELECT ISDN , PRODUCT_CODE FROM CM_POS2.ALL_TEL_SERVICE_SUB_SELFCARE WHERE isdn=:isdn ";
          MapSqlParameterSource parameters = new MapSqlParameterSource();
          parameters.addValue("isdn",isdn);
          NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
          listSub = jdbcTemplateObject.query(sql, parameters, new SubRowMapper());
      }catch(Exception e){
          log.error(e.getMessage(), e);
      }finally{
          log.info("Time getProductCode:" + (System.currentTimeMillis()-startTime) + " ms");
      }
         return listSub.isEmpty()? null:listSub.get(0).getProductCode();
     }
     
     public DataSource getDataSource() {
        return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
