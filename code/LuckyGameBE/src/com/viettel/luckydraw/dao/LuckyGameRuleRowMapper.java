/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.LuckyGameRule;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Mr.NoOne
 */
public class LuckyGameRuleRowMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
         LuckyGameRule item = new LuckyGameRule();
         item.setRuleCode(rs.getString("RULE_CODE"));
     //    item.setLang(rs.getString("LANGUAGE"));
         item.setDescription(rs.getString("DESC_RULE"));

        return item;
    }
    
}
