package com.viettel.database.utils;

/**
 * User: quandv
 * Date: Jan 26, 2007
 * Time: 4:44:14 PM
 */
public class DataSource {

    protected Data data[];

    public DataSource() {
    }

    public DataSource(Data[] data) {
        this.data = data;
    }

    public void add(Data d) {
        if (data == null) {
            data = new Data[]{d};
        } else {
            Data[] newData = new Data[data.length + 1];
            System.arraycopy(data, 0, newData, 0, data.length);
            newData[data.length] = d;
            data = newData;
        }
    }

    public Data getData(String name) {
        if (data == null || data.length == 0) {
            return null;
        }
        for (Data d : data) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }
}
