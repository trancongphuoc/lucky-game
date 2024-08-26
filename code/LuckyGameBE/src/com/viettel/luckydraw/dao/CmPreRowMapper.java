/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.CmPre;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author HALT14
 */
public class CmPreRowMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        CmPre cmPre = new CmPre();
        cmPre.setIsdn(rs.getString("ISDN"));
        cmPre.setProductCode(rs.getString("PRODUCT_CODE"));
        return  cmPre;
    }
    
}
