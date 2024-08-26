/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;

import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author loint9
 */
public class DataSourceFactory {

    
    private static final Logger log = Logger.getLogger(DataSourceFactory.class);

    public static DataSource createDataSouce(String driverClass, String databaseUrl,
            String userName, String password, String testSql) {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDataSourceName("DataSourceFactory");
            comboPooledDataSource.setDriverClass(driverClass);
            //loads the jdbc driver
            comboPooledDataSource.setPreferredTestQuery(testSql);
            comboPooledDataSource.setIdleConnectionTestPeriod(1200);
            comboPooledDataSource.setAcquireRetryAttempts(2);
            comboPooledDataSource.setTestConnectionOnCheckin(true);
            comboPooledDataSource.setTestConnectionOnCheckout(false);
            comboPooledDataSource.setCheckoutTimeout(0);
            comboPooledDataSource.setMaxIdleTimeExcessConnections(1200);
            comboPooledDataSource.setMaxIdleTime(1200);
            comboPooledDataSource.setInitialPoolSize(5);
            comboPooledDataSource.setMinPoolSize(2);
            comboPooledDataSource.setMaxPoolSize(50);
            comboPooledDataSource.setMaxStatementsPerConnection(0);
            comboPooledDataSource.setUnreturnedConnectionTimeout(1200);
            comboPooledDataSource.setDebugUnreturnedConnectionStackTraces(true);
            
            comboPooledDataSource.setJdbcUrl(databaseUrl);
            comboPooledDataSource.setUser(userName);
            comboPooledDataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            log.error(e);
        }
        return comboPooledDataSource;
    }

}
