/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.utils;

import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.viettel.utils.Constant;
import com.viettel.utils.ResourceBundleUtils;
import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author hungtv45
 */
@Deprecated
public class MySQLConnect {

    private static ComboPooledDataSource cpds = null;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://10.58.128.66:3306/neoivr";
    private static String username = "neoivr";
    private static String password = "neoivr123#";
    /**kich thuoc toi thieu cua pool*/
    private static int minPollSize = 5;
    /** kich thuong toi da cua pool*/
    private static int maxPollSize = 10;
    /** connection time out*/
    private static int checkTimeOut = 30000;
    private static int idleTestPeriod = 100;
    private static final Logger mMySQLConnectLogger = Logger.getLogger("Dao");

    public MySQLConnect() {
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            if (cpds == null) {
                driver = ResourceBundleUtils.getConfigResource("driver");
                url = ResourceBundleUtils.getConfigResource("connectionUrl");
                username = ResourceBundleUtils.getConfigResource("usernameMysql");
                password = ResourceBundleUtils.getConfigResource("passwordMysql");
                minPollSize = Integer.parseInt(ResourceBundleUtils.getConfigResource("MINPOLLSIZE"));
                maxPollSize = Integer.parseInt(ResourceBundleUtils.getConfigResource("MAXPOLLSIZE"));
                checkTimeOut = Integer.parseInt(ResourceBundleUtils.getConfigResource("CHECK_TIMEOUT"));
                idleTestPeriod = Integer.parseInt(ResourceBundleUtils.getConfigResource("IDLE_TEST_PERIOD"));

                cpds = new ComboPooledDataSource();
                cpds.setDriverClass(driver);
                cpds.setJdbcUrl(url);
                cpds.setUser(username);
                cpds.setPassword(password);
                cpds.setMinPoolSize(minPollSize);
                cpds.setMaxPoolSize(maxPollSize);
                cpds.setCheckoutTimeout(checkTimeOut);
                cpds.setIdleConnectionTestPeriod(idleTestPeriod);
                mMySQLConnectLogger.info("-----------new mySQL connect------------");
            }
            conn = cpds.getConnection();
        } catch (SQLException e) {
            mMySQLConnectLogger.error(e.toString());
        } catch (Exception e) {Constant.LogNo(e);
            mMySQLConnectLogger.error(e.toString());
        }
        return conn;
    }

    /***
     * @param rs 
     */
    public static void closeResource(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex)  {Constant.LogNo(ex);
                mMySQLConnectLogger.error(ex.toString());
            }
        }
    }

    /***
     * @param stmt stmt
     */
    public static void closeResource(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex)  {Constant.LogNo(ex);
                mMySQLConnectLogger.error(ex.toString());
            }
        }
    }
    
    public static void closeResource(CallableStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex)  {Constant.LogNo(ex);
                mMySQLConnectLogger.error(ex.toString());
            }
        }
    }
    public static void closeResource(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex)  {Constant.LogNo(ex);
                mMySQLConnectLogger.error(ex.toString());
            }
        }
    }

    /***
     * @param conn conn
     */
    public static void closeResource(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex)  {Constant.LogNo(ex);
                mMySQLConnectLogger.error(ex.toString());
            }
        }
    }
}
