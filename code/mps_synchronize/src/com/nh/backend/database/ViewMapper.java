package com.nh.backend.database;

import com.nh.backend.bean.Subscriber;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class ViewMapper implements RowMapper<Subscriber>{
	
	public Subscriber mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subscriber view = new Subscriber();

            return view;
	}
	
}