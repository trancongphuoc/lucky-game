/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class DbResourceBundle extends ResourceBundle {

    private Properties properties;

    public DbResourceBundle(Properties inProperties) {
        properties = inProperties;
    }

    @Override
    protected Object handleGetObject(String key) {
        return properties.getProperty(key);
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public Enumeration<String> getKeys() {
        return properties != null ? ((Enumeration<String>) properties.propertyNames()) : null;
    }

    public static ResourceBundle.Control getMyControl() {
        return new ResourceBundle.Control() {

            @Override
            public List<String> getFormats(String baseName) {
                if (baseName == null) {
                    throw new NullPointerException();
                }
                return Arrays.asList("Language");
            }

            @Override
            public ResourceBundle newBundle(String baseName,
                  Locale locale,
                  String format,
                  ClassLoader loader,
                  boolean reload) throws IllegalAccessException,
                  InstantiationException, IOException {
                if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
                    throw new NullPointerException();
                }
                ResourceBundle bundle = null;
                if (format.equals("Language")) {
                    Properties p = new Properties();
                    Connection con = null;
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {

                        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                        //Define URL of database server for 
                        // database named db_resource_bundle_article on the localhost 
                        // with the default port number 3306. 
                        String url = "jdbc:oracle:thin:@123.31.41.20:1521/Hitex";
                        //Get a connection to the database 
                        //with an id and password 
                        con = DriverManager.getConnection(url, "mps", "mpsperu");
                        //Get a Statement object 
                        stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                              ResultSet.CONCUR_READ_ONLY);
                        StringBuilder query = new StringBuilder();
                        query.append("select key, value from CONFIG_SYSTEM_LANGUAGE ");

                        rs = stmt.executeQuery(query.toString());
                        while (rs.next()) {
                            p.setProperty(rs.getString(1), rs.getString(2));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Can not build properties: " + e);
                    } finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    bundle = new DbResourceBundle(p);
                }
                return bundle;
            }

            @Override
            public long getTimeToLive(String baseName, Locale locale) {
                return 1000 * 60 * 30;
            }

            @Override
            public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle, long loadTime) {
                return true;
            }

        };

    }
}
