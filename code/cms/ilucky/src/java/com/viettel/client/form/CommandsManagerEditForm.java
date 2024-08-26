/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.sql.Timestamp;

/**
 *
 * @author hanhnv62
 */
public class CommandsManagerEditForm {
    String cmd;
    String Oldcmd;
    String sub_service_name;
    Timestamp datetime;
    String Status;    
    String details;

    public CommandsManagerEditForm() {
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getOldcmd() {
        return Oldcmd;
    }

    public void setOldcmd(String Oldcmd) {
        this.Oldcmd = Oldcmd;
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
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
