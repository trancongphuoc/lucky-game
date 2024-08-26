package com.nh.repository;

import com.nh.bean.Winner;
import com.nh.database.DB;
import java.util.List;

public class WinnerDao extends GenericDao<Winner> {

    /**
     *
     */
    private static final long serialVersionUID = 687609519241303105L;

    public List<Winner> getWinnerWeekly() {
        String sql = "SELECT * FROM V_WINNER_WEEKLY";

        List<Winner> conditions = DB.query(sql, getMapper());

        return conditions;
    }
    

    public boolean updateProcessedWeekly(String id) {
        String sql = "Update LUCKY_WINNER Set status =2, DESCRIPTION='ALREADY PAID' WHERE id = ? ";
        int result = DB.update(sql, id);
        return result > 0;
    }
    
    
    public boolean updateProcessed(String id, int status, String description) {
        String sql = "Update LUCKY_WINNER Set status =?, DESCRIPTION=? WHERE id = ? ";
        int result = DB.update(sql,status,description, id);
        return result > 0;
    }
}
