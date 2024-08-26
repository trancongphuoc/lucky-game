/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author hanhnv62
 */
public class CommandsBO implements Serializable{
    private String cmd;
    private String sub_service_name;
    private Timestamp datetime;
    private String status;    
    private String details;

    public CommandsBO(String cmd, String sub_service_name, Timestamp datetime, String status, String details) {
        this.cmd = cmd;
        this.sub_service_name = sub_service_name;
        this.datetime = datetime;
        this.status = status;
        this.details = details;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
