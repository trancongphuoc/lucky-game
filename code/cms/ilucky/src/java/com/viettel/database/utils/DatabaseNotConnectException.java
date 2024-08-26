package com.viettel.database.utils;

/**
 * Signals that an DB exception of some sort has occurred. This
 * class is the general class of exceptions produced by manipulate
 * with database before establish connection to.
 *
 * @author  Viet Quan
 * @version 2.0
 * @since   1.0
 */
public class DatabaseNotConnectException extends Exception{
    /**
     * short desciber about connection which exception occur
     */
    protected String connectname;

    /**
     * Constructs an <code>DatabaseNotConnectException</code>
     *  with "Database is not connnected" as its error detail message.
     */
    public DatabaseNotConnectException(String connectname){
        super("Database is not connnected");
        this.connectname=connectname;
    }

    /**
     * get describer about connection
     * @return short describer about connection
     */
    public String getConnectname() {
        return connectname;
    }
}
