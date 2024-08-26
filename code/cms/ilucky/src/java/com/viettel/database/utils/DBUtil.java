package com.viettel.database.utils;

import com.viettel.database.dao.DatabaseBO;
import com.viettel.utils.Constant;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

/**
 * @author HuongNV-Telsoft
 */
public class DBUtil {
    private static final Logger LOG = Logger.getLogger(Database.class);
    /////////////////////////////////////////////////////////////////

    /**
     * Close objects which are used to interact with database
     *
     * @param obj Object need to be closed such as Statment, PrepareStatment, Connection, ResuletSet, CallableStatment, BatchStatement
     */
    /////////////////////////////////////////////////////////////////
    public static void closeObject(CallableStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {Constant.LogNo(e);
            LOG.error("Error", e);
        }
    }

    /////////////////////////////////////////////////////////////////

    /**
     * Close objects which are used to interact with database
     *
     * @param obj Object need to be closed such as Statment, PrepareStatment, Connection, ResuletSet, CallableStatment, BatchStatement
     */
    /////////////////////////////////////////////////////////////////
    public static void closeObject(Statement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {Constant.LogNo(e);
            LOG.error("Error", e);
        }
    }

    /////////////////////////////////////////////////////////////////

    /**
     * Close objects which are used to interact with database
     *
     * @param obj Object need to be closed such as Statment, PrepareStatment, Connection, ResuletSet, CallableStatment, BatchStatement
     */
    /////////////////////////////////////////////////////////////////
    public static void closeObject(ResultSet obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {Constant.LogNo(e);
            LOG.error("Error", e);
        }
    }


    /**
     * @param obj Connection
     */
    public static void closeObject(Connection obj) {
        try {
            if (obj != null) {
                if (!obj.isClosed()) {
                    if (!obj.getAutoCommit()) {
                        obj.rollback();
                    }
                    obj.close();
                }
            }
        } catch (Exception e) {Constant.LogNo(e);
            LOG.error("Error", e);
        }
    }

    public static long onQueryLong(PreparedStatement stmt, String strParam) throws Exception {
        ResultSet rs = null;
        try {
            stmt.setString(1, strParam);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } finally {
            closeObject(rs);
        }
        return 0;
    }


    /////////////////////////////////////////////////////////////////
    public static List<DatabaseBO> getDatabase(Connection conn) throws SQLException {
        String strSql = "SELECT code, code||'-'||name FROM " + Constant.SCHEMA_NAME + ".databases ORDER BY name";
        List<DatabaseBO> lst = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                lst.add(new DatabaseBO(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            throw ex;
        } finally {
            closeObject(rs);
            closeObject(stmt);
        }
        return lst;
    }
}
