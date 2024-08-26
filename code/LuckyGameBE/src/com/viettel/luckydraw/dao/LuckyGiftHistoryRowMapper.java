/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.LuckyGiftHistory;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author manhnv23
 */
public class LuckyGiftHistoryRowMapper implements RowMapper {

    @Override
    public LuckyGiftHistory mapRow(ResultSet rs, int i) throws SQLException {
        LuckyGiftHistory item = new LuckyGiftHistory();
        
        item.setMsisdn(rs.getString("MSISDN"));
     //   item.setGiftDate(rs.getString("GIFT_DATE"));
        item.setGiftCode(rs.getString("GIFT_CODE"));
     //   item.setGiftDesc(rs.getString("GIFT_DESC"));
    
      //  item.setGiftValue(rs.getString("GIFT_VALUE"));
     //   item.setGiftType(rs.getString("GIFT_TYPE"));

        item.setGiftCount(rs.getLong("GIFT_COUNT"));
        
        return item;
    }

}
