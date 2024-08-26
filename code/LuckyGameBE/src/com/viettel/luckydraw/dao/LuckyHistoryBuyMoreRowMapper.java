/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.LuckyGiftHistory;
import com.viettel.luckydraw.bo.LuckyHistoryBuyMore;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author manhnv23
 */
public class LuckyHistoryBuyMoreRowMapper implements RowMapper {

    @Override
    public LuckyHistoryBuyMore mapRow(ResultSet rs, int i) throws SQLException {
        LuckyHistoryBuyMore item = new LuckyHistoryBuyMore();
        
        item.setMsisdn(rs.getString("MSISDN"));
     //   item.setGiftDate(rs.getString("GIFT_DATE"));
        item.setPrice(rs.getString("PRICE"));
     //   item.setGiftDesc(rs.getString("GIFT_DESC"));
    
      //  item.setGiftValue(rs.getString("GIFT_VALUE"));
     //   item.setGiftType(rs.getString("GIFT_TYPE"));

        item.setRequestTime(rs.getString("REQUEST_TIME"));
        
        return item;
    }

}
