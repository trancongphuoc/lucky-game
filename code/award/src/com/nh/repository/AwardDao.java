package com.nh.repository;

import com.nh.bean.Award;
import com.nh.database.DB;
import java.util.List;

public class AwardDao extends GenericDao<Award> {

    /**
     *
     */
    private static final long serialVersionUID = 68760241303105L;

    public Award getAwardByCode( String code) {
        String sql = "SELECT * FROM LUCKY_AWARD where code = ? and status = '1' ";

        List<Award> conditions = DB.query(sql, getMapper(), code);
        if (conditions != null && !conditions.isEmpty()) {
            return conditions.get(0);
        }
        return null;
    }
    

}
