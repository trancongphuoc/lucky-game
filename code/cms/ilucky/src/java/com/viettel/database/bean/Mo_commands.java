/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.bean;

/**
 *
 * @author hanhnv62
 */
public class Mo_commands {
    private String cmd;
    private String sub_service_name;
    private String status;
   

    public Mo_commands(String cmd, String sub_service_name, String status) {
        this.cmd = cmd;
        this.sub_service_name = sub_service_name;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
