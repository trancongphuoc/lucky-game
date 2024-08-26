/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.utils;

import com.viettel.constant.GlobalConfig;
import com.viettel.securities.TextSecurity;
import com.viettel.utils.Constant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author loint9
 */
public class Database {

    private static final Logger log = Logger.getLogger(Database.class);
    
    private static Database database = null;
    
    private static final Object objet = new Object();

    

    public DataSource dataSource = null;
    
    private final String driver;
    private final String url;
    private final String username;
    private final String password;
    private final String testSql;
    
    private Database() {
        driver = GlobalConfig.get("connection.driver");
        url = GlobalConfig.get("connection.url");
        username = GlobalConfig.get("connection.username");
        String _password = GlobalConfig.get("connection.password");
//        password = _password ;
            String tmp = null;
        try {
             tmp = TextSecurity.getInstance().Decrypt(_password);
            
        } catch (Exception e) {
        }
        password = tmp == null ? _password : tmp;
        testSql = GlobalConfig.get("connection.testsql");
        
        init();
    }

    public static synchronized Database getInstance() {
        synchronized (objet) {
            if (database == null) {
                database = new Database();
            }

            return database;
        }
    }

    private void init() {
        try {
            log.debug("Load connection pool");
            dataSource = DataSourceFactory.createDataSouce(driver, 
                    url, username, password, testSql);
        } catch (Exception e) {Constant.LogNo(e);
            log.error(e);
        }
    }

    private Connection getConnnectionFromPool() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public static Connection getConnection() {
        try {
            Database _database = getInstance();
            
            return _database.getConnnectionFromPool();
        } catch (SQLException ex)  {Constant.LogNo(ex);
            log.error(ex);
        }

        return null;
    }

    public static Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException ex)  {Constant.LogNo(ex);
            log.error(ex);
        }

        return null;
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            return preparedStatement;
        } catch (SQLException ex)  {Constant.LogNo(ex);
            log.error(ex);
        }

        return null;
    }

    public static void closeStatement(Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
                log.debug("closeStatement: " + statement.toString());
            }
        } catch (SQLException ex)  {Constant.LogNo(ex);
            log.error(ex);
        }
    }
    
    
    public static int getInt(String key, ResultSet rs, int defaultValue) throws SQLException {
        ResultSetMetaData rsMeta = rs.getMetaData();
        int metaLength = rsMeta.getColumnCount();
        for (int i = 1; i <= metaLength; i++) {
            String metaColumn = rsMeta.getColumnName(i);
            if (key.equalsIgnoreCase(metaColumn)) {
                return rs.getInt(metaColumn);
            }
        }
        return defaultValue;
    }
    
    public static String getString(String key, ResultSet rs, String defaultValue) throws SQLException{
        String returnValue = null;
        ResultSetMetaData rsMeta = rs.getMetaData();
        int metaLength = rsMeta.getColumnCount();
        for (int i = 1; i <= metaLength; i++) {
            String metaColumn = rsMeta.getColumnName(i);
            if (key.equalsIgnoreCase(metaColumn)) {
                returnValue = rs.getString(metaColumn);
                break;
            }
        }
        if (returnValue == null) {
            return defaultValue;
        }
        return returnValue;
    }
    
}
