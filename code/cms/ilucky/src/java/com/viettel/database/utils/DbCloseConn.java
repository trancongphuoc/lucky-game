//close connection
package com.viettel.database.utils;
import com.viettel.util.VGenLog;
import com.viettel.utils.Constant;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
* A collection of JDBC helper methods.  This class is thread safe.
*/
public final class DbCloseConn {

   /**
         * Close a <code>Connection</code>, avoid closing if null.
         *
         * @param conn Connection to close.
         * @throws SQLException if a database access error occurs
        */
        public static void close(Connection conn) throws SQLException {
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        }

        /**
         * Close a <code>ResultSet</code>, avoid closing if null.
         *
        * @param rs ResultSet to close.
         * @throws SQLException if a database access error occurs
         */
        public static void close(ResultSet rs) throws SQLException {
            if (rs != null) {
                rs.close();
            }
        }
        /**
         * Close a <code>Statement</code>, avoid closing if null.
         *
         * @param stmt Statement to close.
         * @throws SQLException if a database access error occurs
         */
        public static void close(Statement stmt) throws SQLException {
            if (stmt != null) {
                stmt.close();
            }
        }

        /**
         * Close a <code>Connection</code>, avoid closing if null and hide
         * any SQLExceptions that occur.
         *
         * @param conn Connection to close.
        */
        public static void closeQuietly(Connection conn) {
            try {
                if (conn != null) {
                    commitAndClose(conn);
                    close(conn);                    
                }
            } catch (SQLException e) {Constant.LogNo(e);
                VGenLog.info("conn close is UnSuccessful");
            }
        }
        /**
         * Close a <code>Connection</code>, <code>Statement</code> and
         * <code>ResultSet</code>.  Avoid closing if null and hide any
         * SQLExceptions that occur.
         *
         * @param conn Connection to close.
         * @param stmt Statement to close.
         * @param rs ResultSet to close.
         */
        public static void closeQuietly(String strDAO, String functions,Connection conn, Statement stmt,
                ResultSet rs) {
            try {
               closeQuietly(rs);
               VGenLog.info( strDAO + " " + functions + " rs close is Successful");
            } finally {
                try {
                    closeQuietly(stmt);
                    VGenLog.info( strDAO + " " + functions + " stmt close is Successful");
               } finally {
                    closeQuietly(conn);
                    VGenLog.info( strDAO + " " + functions + " conn close is Successful");
                }
            }

        }

        /**
         * Close a <code>ResultSet</code>, avoid closing if null and hide any
         * SQLExceptions that occur.
         *
         * @param rs ResultSet to close.
         */
        public static void closeQuietly(ResultSet rs) {
            try {
                if (rs != null) {
                    close(rs);
                }
            } catch (SQLException e) {Constant.LogNo(e);
                VGenLog.info("rs close is UnSuccessful");
            }
        }

        /**
        * Close a <code>Statement</code>, avoid closing if null and hide
        * any SQLExceptions that occur.
        *
        * @param stmt Statement to close.
        */
       public static void closeQuietly(Statement stmt) {
            try {
                if (stmt != null) {
                    close(stmt);
                }
                
            } catch (SQLException e) {Constant.LogNo(e);
               VGenLog.info("stmt close is UnSuccessful");
            }
        }
        /**
         * Commits a <code>Connection</code> then closes it, avoid closing if null.
         *
         * @param conn Connection to close.
         * @throws SQLException if a database access error occurs
         */
        public static void commitAndClose(Connection conn) throws SQLException {
            if (conn != null && !conn.isClosed()) {
                try {
                   conn.commit();
               } finally {
                    conn.close();
               }
          }
        }

        /**
        * Commits a <code>Connection</code> then closes it, avoid closing if null
        * and hide any SQLExceptions that occur.
         *
       * @param conn Connection to close.
         */
      public static void commitAndCloseQuietly(Connection conn) {
            try {
                commitAndClose(conn);
            } catch (SQLException e) {Constant.LogNo(e);
               // quiet
            }
        }

        public static boolean loadDriver(String driverClassName) {
            try {
               Class.forName(driverClassName).newInstance();
               return true;

            } catch (ClassNotFoundException e) {Constant.LogNo(e);
                return false;

            } catch (IllegalAccessException e) {Constant.LogNo(e);
              // Constructor is private, OK for DriverManager contract
                return true;

            } catch (InstantiationException e) {Constant.LogNo(e);
                return false;

            } catch (Throwable e) {Constant.LogNo(e);
                return false;
            }
        }

        public static void printStackTrace(SQLException e) {
            printStackTrace(e, new PrintWriter(System.err));
        }

        public static void printStackTrace(SQLException e, PrintWriter pw) {

           SQLException next = e;
           while (next != null) {
               next.printStackTrace(pw);
              next = next.getNextException();
                if (next != null) {
                    pw.println("Next SQLException:");
                }
            }
        }

        /**
18         * Print warnings on a Connection to STDERR.
19         *
220         * @param conn Connection to print warnings from
221         */
        public static void printWarnings(Connection conn) {
            printWarnings(conn, new PrintWriter(System.err));
        }
        /**
227         * Print warnings on a Connection to a specified PrintWriter.
228         *
229         * @param conn Connection to print warnings from
30         * @param pw PrintWriter to print to
231         */
        public static void printWarnings(Connection conn, PrintWriter pw) {
           if (conn != null) {
                try {
                    printStackTrace(conn.getWarnings(), pw);
                } catch (SQLException e) {
                    printStackTrace(e, pw);
                }
            }
        }

        /**
243         * Rollback any changes made on the given connection.
244         * @param conn Connection to rollback.  A null value is legal.
245         * @throws SQLException if a database access error occurs
        */
        public static void rollback(Connection conn) throws SQLException {
            if (conn != null) {
                conn.rollback();
            }
        }

        /**
         * Performs a rollback on the <code>Connection</code> then closes it,
         * avoid closing if null.
         *
         * @param conn Connection to rollback.  A null value is legal.
         * @throws SQLException if a database access error occurs
         * @since DbUtils 1.1
         */
        public static void rollbackAndClose(Connection conn) throws SQLException {
            if (conn != null) {
                try {
                    conn.rollback();
                } finally {
                    conn.close();
               }
            }
        }

        /**
         * Performs a rollback on the <code>Connection</code> then closes it,
         * avoid closing if null and hide any SQLExceptions that occur.
         *
         * @param conn Connection to rollback.  A null value is legal.
         * @since DbUtils 1.1
        */
       public static void rollbackAndCloseQuietly(Connection conn) {
           try {
               rollbackAndClose(conn);
           } catch (SQLException e) {Constant.LogNo(e);
                // quiet
            }
        }

    }
