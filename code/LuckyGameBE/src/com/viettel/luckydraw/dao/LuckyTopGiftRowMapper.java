/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;


import com.viettel.luckydraw.bo.LuckyTopGift;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author No Name
 */
public class LuckyTopGiftRowMapper implements RowMapper {

    @Override
    public LuckyTopGift mapRow(ResultSet rs, int i) throws SQLException {
        LuckyTopGift item = new LuckyTopGift();
        
        item.setMsisdn(rs.getString("MSISDN"));
     //   item.setGiftDate(rs.getString("GIFT_DATE"));
        item.setId(rs.getString("ID"));
        item.setStatus(rs.getString("status"));
    
        item.setTotalGift(rs.getLong("toal_gift"));
        
        return item;
    }

}
