package com.viettel.database.utils;

/**
 * Delived class from <code>Infor</code> that contain
 * information about connection to Oracle Database. This
 * include information about connection as ip, port,user,
 * pass, sid and a <code>String</code> specify module
 * that will be loaded command and a <code>boolean</code>
 * specify Service is run with active database or not
 *
 * @author admin
 * @version 2.0
 * @since 2.0
 */

public class DbConfig  {
    /**
     * the driver name for thin connection
     */
    private String driver;
    /**
     * the url connection string
     */
    private String cnurl;

    /**
     * username used as a part of connection account
     */
    private String username;

    /**
     * password used as a part of connection account
     */
    private String password;

    /**
     * <code> boolean</code> value specified for create
     * DbThread or not when init Server
     */
    private boolean isActive;

    /**
     * create new <code>DbConfig</code> with default value as follow
     * host=<code>"192.168.1.23" </code><br>
     * port=<code>1521</code><br>
     * username=<code>"sms2004"</code><br>
     * password=<code>"sms2004"</code><br>
     * module=<code>"CP"</code><br>
     * isActive=<code>false</code><br>
     */
    public DbConfig() {
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.cnurl = "";
        this.username = "cgw";
        this.password = "admin";
        this.isActive = false;
    }

    /**
     * create new <code>DbConfig</code> with specified all informations
     *
     * @param driver  the database driver
     * @param cnurl    the url connection
     * @param username user as part of account
     * @param password pas as part of account
     * @param active   boolean value specify start db thread or not when
     *                 server is started
     */
    public DbConfig(String driver, String cnurl, String username,
                    String password, boolean active) {
        this.driver = driver;
        this.cnurl = cnurl;
        this.username = username;
        this.password = password;
        isActive = active;
    }


    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCnurl() {
        return cnurl;
    }

    public void setCnurl(String cnurl) {
        this.cnurl = cnurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }      
}
