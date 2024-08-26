package com.viettel.database.utils;

//import com.vas.utils.LoadConfigs;
import com.viettel.utils.Constant;
import java.sql.*;

/**
 * User: admin Date: AUGUST 6, 2011 Time: 10:38:02 AM
 */
public class DataStore {

    //hungtv45 read config from code
    public static Connection getConnection() throws SQLException {
        return Database.getConnection();
    }
    
//    public static CallableStatement prepareCall(Connection conn, String sql) throws SQLException {
//        CallableStatement calStat = conn.prepareCall("{call " + Constant.SCHEMA_NAME + sql + "} ");
//        return calStat;
//    }
    
}
