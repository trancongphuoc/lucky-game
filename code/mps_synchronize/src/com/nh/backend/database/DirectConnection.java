/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.database;


import com.nh.backend.bean.HttpApi;
import com.nh.backend.bean.MonthLucky;
import com.nh.cache.HCache;
import com.nh.util.MyLog;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.sql.DataSource;

/**
 *
 * @author thu
 */
public class DirectConnection implements Runnable {

    public static final int CONNECTION_TIMEOUT = 10000; //ms
    protected final static int CHECK_TIME = 30000;//20 giay
    protected boolean isRunning;
    protected Connection mConnection = null;
    protected String mId;
    protected boolean mIsConnected;
    private long mLastConnect;
    private ConnectionStrManager mConnManager;

    private DataSource dataSource = null;

    public DirectConnection(String id, String poolFile) {
        mId = id;
        isRunning = false;
        mIsConnected = false;
        mLastConnect = 0;
        mConnManager = new ConnectionStrManager(poolFile);

        registerDriver();
    }
    
    public DirectConnection(String id, Vector<String> mConnectionList) {
        mId = id;
        isRunning = false;
        mIsConnected = false;
        mLastConnect = 0;
        mConnManager = new ConnectionStrManager(mConnectionList);
        registerDriver();
    }
    
    private void registerDriver() {
        try {
            if (mConnManager.getUrl().startsWith("jdbc:oracle:thin") ) {
//                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                Class.forName("oracle.jdbc.driver.OracleDriver"); 
            } else if (mConnManager.getUrl().startsWith("jdbc:mariadb")) {
//                DriverManager.registerDriver(new org.mariadb.jdbc.Driver);
//                DriverManager.getDriver("org.mariadb.jdbc.Driver");
                Class.forName("org.mariadb.jdbc.Driver"); 
            }
            
        } catch (Exception e) {
            MyLog.Error("DBThread " + mId + "ERROR" + e.getMessage(), e);
            System.exit(0);
        }
    }

    public DirectConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DirectConnection(Connection connection) {
        this.mConnection = connection;
    }

    protected void init() {
        if (System.currentTimeMillis() - mLastConnect <= CONNECTION_TIMEOUT) {
            MyLog.Error("Wait for timeout .... ");
            return;
        }

        mLastConnect = System.currentTimeMillis();

        String conn = mConnManager.getConnectionStr();
        if (conn != null) {
            MyLog.Infor("DBThread " + mId + "Connecting to '" + conn + "'");

            if (mConnManager.getUser() != null
                    && mConnManager.getPassword() != null) {
                mConnection = DirectConnection.this.connectToDB(mConnManager.getUrl(), mConnManager.getUser(), mConnManager.getPassword());
            } else {
                mConnection = connectToDB(conn);
            }
            if (mConnection != null) {
                mIsConnected = true;
                MyLog.Infor("DBThread " + mId + "CONNECTED."); // to '" + conn + "'");
            } else {
                MyLog.Error("DBThread " + mId + "CAN NOT Connect to DB"); //'" + conn + "'");
            }
        } else {
            MyLog.Infor("DBThread " + mId + "get Connection String Failed");
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        mIsConnected = dataSource != null;
    }

    public boolean isConnected() {
        if (dataSource != null) {
            return true;
        }
        return mIsConnected;
    }

    private void reconnect() {
        /* neu la datasource thi ko can reconnect*/
        if (dataSource != null) {
            return;
        }
        mIsConnected = false;
        try {
            MyLog.Debug("DBThread " + mId + "Wait 5 secs before RECONNECT to DB ....");
            Thread.sleep(5000); // wait 5s before reconnect
            MyLog.Debug("DBThread " + mId + "Reconnecting ...");
            mConnection.close();
        } catch (Exception e) {
        } finally {
            mConnection = null;
            init();
        }
    }

    private String getThreadName() {
        return "DBThread " + mId + ": ";
    }

    protected Connection connectToDB(String conn) {
        try {
            Connection co = DriverManager.getConnection(conn);
            MyLog.Infor("DBThread " + mId + "CONNECTED.");
            return co;
        } catch (Exception e) {
            MyLog.Error("DBThread " + mId + "Error connect to Oracle DB server" + e.getMessage());
            return null;
        }
    }

    protected Connection connectToDB(String conn, String user, String pass) {
        try {
            Connection co = DriverManager.getConnection(conn, user, pass);

            MyLog.Infor("DBThread " + mId + "CONNECTED.");
            return co;
        } catch (Exception e) {
            MyLog.Error("DBThread " + mId + "Error connect to Oracle DB server" + e.getMessage());
            return null;
        }
    }

    public void startConnect() {
        init();

        isRunning = true;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (isRunning && dataSource == null) {
            try {
                Statement s = null;
                try {
                    String strSQL = "select 1 from dual";
                    s = mConnection.createStatement();
                    s.setQueryTimeout(30); // sec
                    java.sql.ResultSet r = s.executeQuery(strSQL);
                    r.next();
                    s.close();
                    MyLog.Debug("DBThread " + mId + "Check connection OK, sleep (s): " + (CHECK_TIME/1000));
                } catch (Exception ex) {
                    MyLog.Error("DBThread " + mId + "Check connection ERROR" + ex.getMessage() + ", RECONNECTING .... ");
                    reconnect();
                }
                Thread.sleep(CHECK_TIME);
            } catch (Exception e) {
                MyLog.Error("DBThread " + mId + "ERROR" + e.getMessage());
                MyLog.Error(e);
            }
        }
    }

    public CallableStatement getStatement(String strSql) {
        try {
            return getDatabaseConnection().prepareCall(strSql);
        } catch (Exception ex) {
            MyLog.Error("DBThread " + mId + "ERROR" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
            return null;
        }
    }

    public Connection getDatabaseConnection() {
        if (dataSource == null) {
            return mConnection;
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            MyLog.Error(ex);
        }
        return null;
    }

    public void closeConnection(Connection con) {
        if (dataSource == null || con == null) {
            return;
        }

        try {
            con.close();
        } catch (Exception ex) {
            MyLog.Error(ex);
        }
    }

    public boolean excuteUpdate(String strSQL) {
        MyLog.Debug(getThreadName() + "start execute'" + strSQL + "'");
        boolean result = false;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        int count = 0;
        Connection conn = null;
        try {
            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            count = s.executeUpdate();
            result = true;
            s.close();
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
            result = false;
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
            result = false;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }
        MyLog.Infor(getThreadName() + " Finish execute '" + strSQL + "'" + result + " ("
                + (System.currentTimeMillis() - start) + " ms) (Count" + count + ")");

        return result;
    }

    
    /**
     * Get additional date for Service
     *
     * @param msisdn
     * @param subType
     * @param subService
     * @param service
     * @param cmd
     * @param cat
     * @param shortCode
     * @param cmdType
     * @return
     */
    public long getAdditonalRegisterDate(String msisdn, int subType, String subService, String service,
            String cmd, String cat, String shortCode, int cmdType) {
        String log = "getAdditonalRegisterDate (MSISDN: " + msisdn
                + "; SubType: " + subType
                + "; Service: " + service
                + "; SubService: " + subService
                + "; ShortCode: " + shortCode
                + "; CMD: " + cmd
                + "; CAT: " + cat
                + "; CMDType: " + cmdType
                + ")";
        MyLog.Debug(getThreadName() + "start " + log);
        int result = 30;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "{ ?=call fn_get_add_day_monfee_reg_sms(?, ?, ?, ?, ?, ?, ?, ?) }";
            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            s.setQueryTimeout(30);
            s.registerOutParameter(1, java.sql.Types.VARCHAR);
            s.setString(2, msisdn);
            s.setInt(3, subType);
            s.setString(4, cmd);
            s.setString(5, cat);
            s.setString(6, subService);
            s.setString(7, service);
            s.setString(8, shortCode);
            s.setInt(9, cmdType);

            s.executeUpdate();

            result = s.getInt(1);
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR " + e.getMessage());
            MyLog.Error(e);
            result = 30;
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR " + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
            result = 30;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish " + log + " -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }

    public long getAdditonalRegisterDateWS(String msisdn, int subType, String subService, String service) {
        String log = "getAdditonalRegisterDate (MSISDN: " + msisdn
                + "; SubType: " + subType
                + "; Service: " + service
                + "; SubService: " + subService
                + ")";
        MyLog.Debug(getThreadName() + "start getAdditonalRegisterDateWS" + log);
        int result = 30;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "{ ?=call fn_get_add_day_register_ws(?, ?, ?, ?) }";
            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            s.setQueryTimeout(30); // sec
            s.registerOutParameter(1, java.sql.Types.VARCHAR);
            s.setString(2, msisdn);
            s.setInt(3, subType);
            s.setString(4, subService);
            s.setString(5, service);

            s.executeUpdate();

            result = s.getInt(1);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR " + ex.getMessage());
            MyLog.Error(ex);
            result = 30;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish getAdditonalRegisterDateWS" + log + " -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }

    /**
     * Get additional day for charge monfee success
     *
     * @param msisdn
     * @param subType
     * @param subService
     * @param service
     * @return
     */
    public long getAdditonalDateMonfee(String msisdn, int subType, String subService, String service) {
        MyLog.Debug(getThreadName() + "start getAdditonalDateMonfee for '" + subType + "' - " + subService + " - " + service);
        int result = 30;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "{ ?=call fn_get_add_day_charge_monfee(?, ?, ?, ?) }";
            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30); // sec
            s.registerOutParameter(1, java.sql.Types.VARCHAR);
            s.setString(2, msisdn);
            s.setInt(3, subType);
            s.setString(4, subService);
            s.setString(5, service);

            s.executeUpdate();
            result = s.getInt(1);
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR " + e.getMessage());
            MyLog.Error(e);
            result = 30;
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR " + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
            result = 30;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish getAdditonalDateMonfee for'" + subType + " - " + subService + " - " + service + " -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }

    private long getTimeStamp(ResultSet r, String column, long defaultValue) {
        long result = defaultValue;
        try {
            if (r.getTimestamp(column) != null) {
                result = r.getTimestamp(column).getTime();
            }
        } catch (Exception ex) {
            MyLog.Debug(getThreadName() + " get Timestamp for " + column + " failed, use default: " + defaultValue);
            result = defaultValue;
        }

        return result;
    }
    
    
    public int getLuckyRoullet(String msisdn, String transid, String choice) {
        int status = -1;
        MyLog.Debug(getThreadName() + "start pr_lucky_routlet for " + msisdn + ", transid:" + transid);
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin pr_lucky_routlet(?, ?,?,?); end;";

            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, msisdn);
            s.setString(2, transid);
            s.setString(3, choice);
            s.registerOutParameter(4, java.sql.Types.INTEGER);
            s.execute();

            status = s.getInt(4);

        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish pr_lucky_routlet for " + msisdn + "' -> (" + status + ") ("
                + (System.currentTimeMillis() - start) + " ms)" + ", transid:" + transid);
        return status;
    }
    
    public MonthLucky luckyMonth(String msisdn, String transid) {
        MonthLucky monthLucky = null;
        int status = -1;
        MyLog.Debug(getThreadName() + "start pr_month_spin for " + msisdn + ", transid:" + transid);
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin pr_month_spin(?, ?,?,?,?); end;";

            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, msisdn);
            s.setString(2, transid);
            s.registerOutParameter(3, java.sql.Types.INTEGER);
            s.registerOutParameter(4, java.sql.Types.VARCHAR);
            s.registerOutParameter(5, java.sql.Types.VARCHAR);
            s.execute();

            status = s.getInt(3);
            String luckyCode = s.getString(4);
            String periodCode = s.getString(5);
            monthLucky = new MonthLucky(status, luckyCode, periodCode);
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish pr_month_spin for " + msisdn + "' -> (" + status + ") ("
                + (System.currentTimeMillis() - start) + " ms)" + ", transid:" + transid);
        return monthLucky;
    }
    
    public boolean buyLog(String msisdn, String transid, int type) {
        boolean status = false;
        MyLog.Debug(getThreadName() + "start pr_buy_log for " + msisdn + ", transid:" + transid);
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin pr_buy_log(?, ?,?); end;";

            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, msisdn);
            s.setString(2, transid);
            s.setInt(3, type);
            status = s.execute();
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish pr_buy_log for " + msisdn + "' -> (" + status + ") ("
                + (System.currentTimeMillis() - start) + " ms)" + ", transid:" + transid);
        return status;
    }
    
    public void cancelSub(String msisdn) {
        int status = -1;
        MyLog.Debug(getThreadName() + "start cancelSub for " + msisdn );
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "update subscriber set status = 0 where msisdn = ? and status in (1,2) ";

            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, msisdn);
            s.execute();
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish cancelSub for " + msisdn + "' -> (" + status + ") ("
                + (System.currentTimeMillis() - start) + " ms)" );
    }
    
    public Map<String,String> query(String sql) {
        Map<String,String> results = new HashMap<>();
        MyLog.Debug(getThreadName() + "start query for " + sql );
        Statement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        ResultSet r = null;
        try {
            conn = getDatabaseConnection();
            s = conn.prepareCall(sql);
            // Set timeout
            s.setQueryTimeout(30);
            
            r = s.executeQuery(sql) ;
            if (r.next()) {
                ResultSetMetaData meta = r.getMetaData();
                int coloumnCount = meta.getColumnCount();
                if (coloumnCount > 0) {
                    for (int i = 0; i < coloumnCount; i++) {
                        String key = meta.getColumnName((i+1));
                        String value = r.getString((i+1));
                       results.put(key, value);
                    }
                }
            }
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception ee) {
                }
            }
            
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish query for " + sql + "' -> ("
                + (System.currentTimeMillis() - start) + " ms)" );
        return results;
    }

    public String getContent(String msisdn, String transid, Date birthDay, String horoType) {
        String message = null;
        MyLog.Debug(getThreadName() + "start pr_horoscope for " + msisdn + ", transid:" + transid);
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin pr_horoscope(?, ?,?,?,?); end;";

            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, msisdn);
            s.setString(2, transid);
            s.setDate(3, birthDay);
            s.setString(4, horoType);
            s.registerOutParameter(5, java.sql.Types.VARCHAR);
            s.execute();

            message = s.getString(5);
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish pr_horoscope for " + msisdn + "' -> (" + birthDay + ") ("
                + (System.currentTimeMillis() - start) + " ms)" + ", transid:" + transid + "=>" + message);
        return message;
    }

    public int checkRemainLucky(String msisdn, String transid) {
        int status = -1;
        MyLog.Debug(getThreadName() + "start pr_lucky_remain for " + msisdn + ", transid:" + transid);
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin pr_lucky_remain(?, ?,?); end;";

            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, msisdn);
            s.setString(2, transid);
            s.registerOutParameter(3, java.sql.Types.INTEGER);
            s.execute();

            status = s.getInt(3);

        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "Finish pr_lucky_remain for " + msisdn + "' -> (" + status + ") ("
                + (System.currentTimeMillis() - start) + " ms)" + ", transid:" + transid);
        return status;
    }
    
    /**
     * status 1 dang ky
     * 0 huy
    */
    public String getUpdateSub(String msisdn,  String subServiceName, int status) {
        String message = null;
        MyLog.Infor(getThreadName() + "start pr_subscriber_iu for: msisdn: " + msisdn  + ", status:" + status + ", subServiceName:" + subServiceName );
        PreparedStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin PR_SUBSCRIBER_IU(?,?,?); end; ";

            conn = getDatabaseConnection();
            s = conn.prepareStatement(strSQL);
            // Set timeout
            int i=1;
            s.setQueryTimeout(30);
            s.setString(i++, msisdn);
            s.setString(i++, subServiceName);
            s.setInt(i++, status);
            
            boolean row = s.execute();

            message = "" + row;
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "finish pr_subscriber_iu for " + msisdn + ", status:" + status 
                + ", subServiceName:" + subServiceName  + ", duration:" 
                + (System.currentTimeMillis() - start) + "(ms)=>" + message );
        return message;
    }
    
    public String renew(String msisdn,  String subServiceName, int status) {
        String message = null;
        MyLog.Infor(getThreadName() + "start PR_renew for: msisdn: " + msisdn  + ", status:" + status + ", subServiceName:" + subServiceName );
        PreparedStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin PR_RENEW(?,?,?); end;";
            
            conn = getDatabaseConnection();
            s = conn.prepareStatement(strSQL);
            // Set timeout
            int i=1;
            s.setQueryTimeout(30);
            s.setString(i++, msisdn);
            s.setString(i++, subServiceName);
            s.setInt(i++, status);
            
            boolean row = s.execute();

            message = "" + row;
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "finish PR_renew for " + msisdn + ", status:" + status 
                + ", subServiceName:" + subServiceName  + ", duration:" 
                + (System.currentTimeMillis() - start) + "(ms)=>" + message );
        return message;
    }
    
    
    public HttpApi getHttpApi(String code) {
        HttpApi data = cache_httpapi_by_code.get(code);
        if (data != null) {
            MyLog.Infor("getHttpApi from Cache: " + data);
            return data;
        }
        
        data = getHttpApiReal(code);
        if (data != null) {
            cache_httpapi_by_code.put(code, data);
            MyLog.Infor("getHttpApi from Database: " + data);
        }
         
        return data;
    }
    
    private static final HCache<String,HttpApi> cache_httpapi_by_code = 
            new HCache<String, HttpApi>("cache_httpapi_by_code",200,
                    HCache.NO_MAX_CAPACITY, HCache.NO_TIMEOUT);
    
    private HttpApi getHttpApiReal(String code) {
        MyLog.Debug(getThreadName() + "start http_api (code: " + code + ")");
        HttpApi result = null;
        CallableStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "SELECT * FROM http_api WHERE code = ? and status =1 ";
            conn = getDatabaseConnection();
            s = conn.prepareCall(strSQL);
            // Set timeout
            s.setQueryTimeout(30);
            s.setString(1, code);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String id = r.getString("id");
                String user_name = r.getString("user_name");
                String password = r.getString("password");
                String url = r.getString("url");
                String params = r.getString("params");
                String webservice_type = r.getString("webservice_type");
                String descriptions = r.getString("descriptions");
                String body = r.getString("body");
                String return_tag = r.getString("return_tag");
                int timeout = r.getInt("timeout");
                String method = r.getString("method");
                String name = r.getString("name");
                String return_success_value = r.getString("return_success_value");
                String load_condition = r.getString("load_condition");
                String load_field = r.getString("load_field");
                String moduleCode = r.getString("MODULE_CODE");
                int RETURN_AUTO = r.getInt("RETURN_AUTO");

                result = new HttpApi(id, user_name, password, url, params,
                        webservice_type, code,
                        descriptions, body, return_tag, timeout, method, name,
                        return_success_value, load_condition, load_field, moduleCode);
                result.setReturnAuto(RETURN_AUTO);
            }
            r.close();
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "finish get http_api for (code: "
                + code + ") -> " + result + "("
                + (System.currentTimeMillis() - start) + " ms)");

        return result;
    }
    
    
    public String updateOtpHandle(String msisdn,  String subServiceName, String action, String response, 
            String otpType, String cate, String amount, String transid, String item, String content) {
        String message = null;
        MyLog.Infor(getThreadName() + "start updateOtpHandle for: msisdn: " + msisdn  
                + ", response:" + response + ", action:" + action 
                 + ", otpType:" + otpType + ", cate:" + cate 
                + ", amount:" + amount + ", subServiceName:" + subServiceName  + ", transid:" + transid );
        PreparedStatement s = null;
        long start = System.currentTimeMillis();
        Connection conn = null;
        try {
            String strSQL = "begin PR_OTP(?,?,?,?, ?,?,?,?,?,?); end;";

            conn = getDatabaseConnection();
            s = conn.prepareStatement(strSQL);
            // Set timeout
            int i=1;
            s.setQueryTimeout(30);
            s.setString(i++, msisdn);
            s.setString(i++, subServiceName);
            s.setString(i++, action);
            s.setString(i++, response);
            
            s.setString(i++, otpType);
            s.setString(i++, cate);
            s.setString(i++, amount);
            s.setString(i++, transid);
            s.setString(i++, item);
            s.setString(i++, content);
            boolean row = s.execute();

            message = "" + row;
        } catch (SQLException e) {
            MyLog.Error(getThreadName() + "ERROR" + e.getMessage());
            MyLog.Error(e);
        } catch (Exception ex) {
            MyLog.Error(getThreadName() + "ERROR3" + ex.getMessage());
            MyLog.Error(ex);
            reconnect();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ee) {
                }
            }
            closeConnection(conn);
        }

        MyLog.Infor(getThreadName() + "finish updateOtpHandle for " + msisdn + ", status:" + response 
                + ", subServiceName:" + subServiceName  + ", duration:" 
                + (System.currentTimeMillis() - start) + "(ms)=>" + message );
        return message;
    }
    
}
