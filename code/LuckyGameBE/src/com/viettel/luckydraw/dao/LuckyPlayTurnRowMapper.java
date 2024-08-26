/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.dao;

import com.viettel.luckydraw.bo.LuckyGamePlayTurn;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author manhnv23
 */
public class LuckyPlayTurnRowMapper implements RowMapper {

    @Override
    public LuckyGamePlayTurn mapRow(ResultSet rs, int i) throws SQLException {
        LuckyGamePlayTurn item = new LuckyGamePlayTurn();
        
        item.setMsisdn(rs.getString("MSISDN"));
        item.setGiftDate(rs.getString("GIFT_DATE"));
        item.setGiftCode(rs.getString("GIFT_CODE"));
        item.setGiftDesc(rs.getString("GIFT_DESC"));
        item.setGiftOrder(rs.getString("GIFT_ORDER"));
        item.setGiftValue(rs.getString("GIFT_VALUE"));
        item.setGiftType(rs.getString("GIFT_TYPE"));
        item.setPlayId(rs.getString("PLAY_ID"));
//        item.setFee(rs.getLong("FEE"));
        item.setGroupType(rs.getString("GROUP_TYPE"));
        item.setLang(rs.getString("LANG"));
        
        return item;
    }

}
