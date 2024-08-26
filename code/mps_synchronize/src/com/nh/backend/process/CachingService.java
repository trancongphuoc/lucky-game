/**
 *
 */

package com.nh.backend.process;


import com.nh.backend.bean.Database;
import com.nh.backend.bean.HttpMenu;
import com.nh.backend.bean.ModuleInfo;
import com.nh.backend.bean.SqlExcutor;
import com.nh.backend.bean.SqlMenu;
import com.nh.backend.database.ConnectionPoolManager;
import com.nh.cache.HCache;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 */
public class CachingService{
    private static Logger logger = Logger.getLogger(CachingService.class);
    
    public CachingService(){
    }

     private static final HCache<String,ModuleInfo> cache_ModuleInfo_by_code = 
            new HCache<String, ModuleInfo>("cache_ModuleInfo_by_code",200,
                    HCache.NO_MAX_CAPACITY, HCache.NO_TIMEOUT);
    private static final Object lockModuleInfo = new Object();
     
     
    public ModuleInfo getModuleInfo(String code) {
        synchronized(lockModuleInfo) {
            ModuleInfo data = cache_ModuleInfo_by_code.get(code);
            if (data != null) {
                logger.info("getModuleInfo from Cache: " + data);
                return data;
            }

            data = getModuleInfoDB(code);
            if (data != null) {
                cache_ModuleInfo_by_code.put(code, data);
                logger.info("getModuleInfo from Database: " + data);
            }

            return data;
        }
    }
    
    private ModuleInfo getModuleInfoDB(String code) {
        logger.debug( "start MODULE_INFO (code: " + code + ")");
        ModuleInfo result = null;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "SELECT * FROM MODULE_INFO WHERE code = ?";
            conn = getConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, code);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String id = r.getString("id");
                String name = r.getString("name");
                String email = r.getString("email");
                String msisdn = r.getString("msisdn");
                String description = r.getString("description");
                String status = r.getString("status");

                result = new ModuleInfo(id, code, name, email, msisdn, description, status);
            }
            r.close();
        } catch (SQLException e) {
            logger.error( "ERROR" + e.getMessage(), e);
        } catch (Exception ex) {
            logger.error( "ERROR" + ex.getMessage());
            logger.error(ex);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        logger.info("finish get MODULE_INFO for (code: "
                + code + ") -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }
    
     private static final HCache<String,HttpMenu> cache_HttpMenu_by_code = 
            new HCache<String, HttpMenu>("cache_HttpMenu_by_code",200,
                    HCache.NO_MAX_CAPACITY, HCache.NO_TIMEOUT);
    private static final Object lockHttpMenu = new Object();
    
    public HttpMenu getHttpMenu(String code) {
        synchronized(lockHttpMenu) {
            HttpMenu data = cache_HttpMenu_by_code.get(code);
            if (data != null) {
                logger.info("getHttpMenu from Cache: " + data);
                return data;
            }

            data = getHttpMenuDB(code);
            if (data != null) {
                cache_HttpMenu_by_code.put(code, data);
                logger.info("getHttpMenu from Database: " + data);
            }

            return data;
        }
    }
    
    
    private static final Map<String,SqlExcutor> cache_SqlExcutor_by_code = 
            new java.util.HashMap<String, SqlExcutor>();
    private static final Object lockSqlExcutor = new Object();
    
    public SqlExcutor getSqlExcutor(String code) {
        synchronized(lockSqlExcutor) {
            SqlExcutor data = cache_SqlExcutor_by_code.get(code);
            if (data != null) {
                logger.info("getSqlExcutor from Cache: " + data);
                return data;
            }

            data = getSqlExcutorDB(code);
            if (data != null) {
                cache_SqlExcutor_by_code.put(code, data);
                logger.info("getSqlExcutor from Database: " + data);
            }
            return data;
        }
    }

    
    private static final HCache<String,Database> cache_Database_by_code = 
            new HCache<String, Database>("cache_Database_by_code",200,
                    HCache.NO_MAX_CAPACITY, HCache.NO_TIMEOUT);
    private static final Object lockDatabase = new Object();
    
    public Database getDatabase(String databaseCode) {
        synchronized(lockDatabase) {
            Database data = cache_Database_by_code.get(databaseCode);
            if (data != null) {
                logger.info("getDatabase from Cache: " + data);
                return data;
            }

            data = getDatabaseDB(databaseCode);
            if (data != null) {
                cache_Database_by_code.put(databaseCode, data);
                logger.info("getDatabase from Database: " + data);
            }

            return data;
        }
    }
    
    private static final HCache<String,SqlMenu> cache_SqlMenu_by_code = 
            new HCache<String, SqlMenu>("cache_SqlMenu_by_code",200,
                    HCache.NO_MAX_CAPACITY, HCache.NO_TIMEOUT);
    private static final Object lockSqlMenu = new Object();
    
    public SqlMenu getSqlMenu(String code) {
        synchronized(lockSqlMenu) {
            SqlMenu data = cache_SqlMenu_by_code.get(code);
            if (data != null) {
                logger.info("getSqlMenu from Cache: " + data);
                return data;
            }

            data = getSqlMenuDB(code);
            if (data != null) {
                cache_SqlMenu_by_code.put(code, data);
                logger.info("getSqlMenu from Database: " + data);
            }

            return data;
        }
    }
    
    private HttpMenu getHttpMenuDB(String code) {
        logger.debug( "start Http_Menu (code: " + code + ")");
        HttpMenu result = null;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "SELECT * FROM Http_Menu WHERE code = ?";
            conn = getConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, code);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String id = r.getString("ID");
                String userName = r.getString("USER_NAME");
                String password = r.getString("PASSWORD");
                String url = r.getString("URL");
                String webserviceType = r.getString("WEBSERVICE_TYPE");
                String name = r.getString("NAME");
                String description = r.getString("DESCRIPTION");
                String body = r.getString("BODY");
                int timeout = r.getInt("TIMEOUT");
                String method = r.getString("METHOD");
                String status = r.getString("STATUS");
                String moduleCode = r.getString("MODULE_CODE");
                String returnTag = r.getString("RETURN_TAG");
                String returnSuccessValue = r.getString("RETURN_SUCCESS_VALUE");
                String menuParent = r.getString("MENU_PARENT");
                String menuName = r.getString("MENU_NAME");
                String menuValue = r.getString("MENU_VALUE");
                String contextName = r.getString("CONTEXT_NAME");
                String contextValue = r.getString("CONTEXT_VALUE");

                int recordPerPage = r.getInt("RECORD_PER_PAGE");
                String lineDown = r.getString("LINE_DOWN");
                String nextChar = r.getString("NEXT_CHAR");
                String backChar = r.getString("BACK_CHAR");
                String nextContent = r.getString("NEXT_CONTENT");
                String backContent = r.getString("BACK_CONTENT");
                String splitMenu = r.getString("SPLIT_MENU");

                result = new HttpMenu(id, userName, password, url, webserviceType,
                        name, code, description, body, timeout,
                        method, status, moduleCode, returnTag,
                        returnSuccessValue, menuParent, menuName,
                        menuValue, contextName, contextValue);
                result.setRecordPerPage(recordPerPage);
                result.setLineDown(lineDown);
                result.setNextChar(nextChar);
                result.setBackChar(backChar);
                result.setNextContent(nextContent);
                result.setBackContent(backContent);
                result.setSplitMenu(splitMenu);

            }
            r.close();
        } catch (SQLException e) {
            logger.error( "ERROR" + e.getMessage(), e);
        } catch (Exception ex) {
            logger.error( "ERROR" + ex.getMessage(), ex);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        logger.info( "finish get Http_Menu for (code: "
                + code + ") -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }

    private SqlExcutor getSqlExcutorDB(String code) {
        logger.debug( "start SQL_EXCUTE (code: " + code + ")");
        SqlExcutor result = null;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "SELECT * FROM LUCKY_SQL_COMMAND WHERE code = ? and status=1";
            conn = getConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, code);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String id = r.getString("ID");
                String name = r.getString("NAME");
                String sql = r.getString("SQL");
                int timeout = r.getInt("TIMEOUT");

                String databaseCode = get("DATABASE_CODE",r,"");
                int status = r.getInt("STATUS");

                result = new SqlExcutor(id, code, name, sql, timeout,
                        status, databaseCode);

            }
            r.close();
        } catch (SQLException e) {
            logger.error( "ERROR" + e.getMessage(), e);
        } catch (Exception ex) {
            logger.error( "ERROR" + ex.getMessage(), ex);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        logger.info( "finish get SQL_EXCUTE for (code: "
                + code + ") -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }

    private Database getDatabaseDB(String code) {
        logger.debug( "start Databases (code: " + code + ")");
        Database result = null;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "SELECT * FROM Databases WHERE code = ? and status=1";
            conn = getConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, code);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String id = r.getString("ID");
                String name = r.getString("NAME");
                String config = r.getString("CONFIG");
                int status = r.getInt("STATUS");
                String module_code = r.getString("MODULE_CODE");
                String driver = r.getString("DRIVER");
                String url = r.getString("URL");
                String username = r.getString("USERNAME");
                String password = r.getString("PASSWORD");
                int readOnly = getInt("READ_ONLY", r, 0);
                result = new Database(id, code, name, config, status, module_code);
                result.setDriver(driver);
                result.setUrl(url);
                result.setUsername(username);
                result.setPassword(password);
                result.setReadOnly(readOnly);
            }
            r.close();
        } catch (SQLException e) {
            logger.error( "ERROR" + e.getMessage(), e);
        } catch (Exception ex) {
            logger.error( "ERROR" + ex.getMessage(), ex);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        logger.info( "finish get Databases for (code: "
                + code + ") -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }

    private SqlMenu getSqlMenuDB(String code) {
        logger.debug( "start SqlMenu (code: " + code + ")");
        SqlMenu result = null;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "SELECT * FROM SQL_MENU WHERE code = ? and status=1";
            conn = getConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, code);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String id = r.getString("ID");
                String name = r.getString("NAME");
                String description = r.getString("DESCRIPTION");
                String selectSql = r.getString("SELECT_SQL");
                int timeout = r.getInt("TIMEOUT");
                int status = r.getInt("STATUS");
                String databaseCode = r.getString("DATABASE_CODE");
                int recordPerPage = r.getInt("RECORD_PER_PAGE");
                String lineDown = r.getString("LINE_DOWN");
                String nextChar = r.getString("NEXT_CHAR");
                String backChar = r.getString("BACK_CHAR");
                String nextContent = r.getString("NEXT_CONTENT");
                String backContent = r.getString("BACK_CONTENT");
                String splitMenu = r.getString("SPLIT_MENU");

                result = new SqlMenu(id, name, code, description, selectSql,
                        timeout, status, databaseCode, recordPerPage, lineDown,
                        nextChar, backChar, nextContent, backContent, splitMenu);
            }
            r.close();
        } catch (SQLException e) {
            logger.error( "ERROR" + e.getMessage(), e);
        } catch (Exception ex) {
            logger.error( "ERROR" + ex.getMessage(), ex);
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                closeConnection(conn);
            } catch (Exception ee) {
            }
        }

        logger.info( "finish get SqlMenu for (code: "
                + code + ") -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }
    
    
    private Connection getConnection() throws SQLException {
        Connection connection =  ConnectionPoolManager.getConnection("dbapp");
        if (connection == null) {
            logger.error("error can not getConnection");  
        }
        return connection;
    }
    
    private void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ee) {
        }
    }
    
    private int getInt(String key, ResultSet rs, int defaultValue) throws SQLException {
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
    
    private String get(String key, ResultSet rs, String defaultValue) throws SQLException {
        ResultSetMetaData rsMeta = rs.getMetaData();
        int metaLength = rsMeta.getColumnCount();
        for (int i = 1; i <= metaLength; i++) {
            String metaColumn = rsMeta.getColumnName(i);
            if (key.equalsIgnoreCase(metaColumn)) {
                return rs.getString(metaColumn);
            }
        }
        return defaultValue;
    }
}
