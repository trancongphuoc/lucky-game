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
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 *
 * @author HALT14
 */
public class BccsPreDAO {

    private static BccsPreDAO instance;
    private DataSource dataSource;
    public static final Logger log = Logger.getLogger(BccsPreDAO.class);
//    private JdbcTemplate jdbcTemplateObject;
//    private SimpleJdbcCall jdbcCall;

    public synchronized static BccsPreDAO getInstance() {
        if (instance == null) {
            try {
                instance = (BccsPreDAO) AppContext.getInstance().getContext().getBean("BccsPreDAO");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return instance;
    }

    

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    


    public String getProductCode(String isdn){
        long startTime = System.currentTimeMillis();
      List<Sub> listSub = new ArrayList<Sub>();
      try{
          String query = "SELECT ISDN , PRODUCT_CODE FROM CM_PRE2.ALL_CUST_SUB_FOR_SELFCARE WHERE isdn=:isdn ";
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("isdn",isdn);
          NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
          listSub = jdbcTemplateObject.query(query, params, new SubRowMapper());
      }catch(Exception e){
          log.error(e.getMessage(), e);
      }finally{
          log.info("Time getProductCode:" + (System.currentTimeMillis()-startTime) + " ms");
      }
         return listSub.isEmpty()? null:listSub.get(0).getProductCode();
     }
    
    
}
