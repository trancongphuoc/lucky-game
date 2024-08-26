package com.viettel.database.utils;


/**
 * Object that contain information about Ems or Mms record
 * that store in database. Data include id, type, data,..
 *
 * @author Viet Quan
 * @version 2.0
 * @since 2.0
 */

public class Data {
    /**
     * code of data that is key of record in database
     */
    protected String name;
    protected String datatype;
    protected Object value;


    public Data(String name, String datatype, Object value) {
        this.name = name;
        this.datatype = datatype;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
