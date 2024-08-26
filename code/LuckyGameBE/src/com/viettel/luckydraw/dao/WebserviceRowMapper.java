/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;


import com.viettel.luckydraw.bo.Webservice;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author MrNP
 */
public class WebserviceRowMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
         Webservice item = new Webservice();
         item.setId(rs.getString("id"));
         item.setRawXml(rs.getString("raw_xml"));
         item.setRspCodeSucc(rs.getString("rsp_code_succ"));
         item.setUrl(rs.getString("url"));
         item.setWsName(rs.getString("ws_name"));
         item.setTimeout(rs.getInt("timeout"));
         item.setXpathResponseCode(rs.getString("xpath_response_code"));
         item.setXpathExtension01(rs.getString("xpath_extension_01"));
         item.setXpathExtension02(rs.getString("xpath_extension_02"));
         item.setXpathExtension03(rs.getString("xpath_extension_03"));

        return item;
    }
    
}
