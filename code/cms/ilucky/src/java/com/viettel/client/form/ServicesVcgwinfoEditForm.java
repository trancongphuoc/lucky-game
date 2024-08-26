/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author TRIEUNGUYEN
 */
public class ServicesVcgwinfoEditForm {

    private String id;
    private String providerName;
    private String serviceName;
    private String descriptions;
    private String vcgwUser;
    private String vcgwPassword;
    private String command;
    private String hash;
    private String status;

    public ServicesVcgwinfoEditForm() {
    }
  

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getVcgwUser() {
        return vcgwUser;
    }

    public void setVcgwUser(String vcgwUser) {
        this.vcgwUser = vcgwUser;
    }

    public String getVcgwPassword() {
        return vcgwPassword;
    }

    public void setVcgwPassword(String vcgwPassword) {
        this.vcgwPassword = vcgwPassword;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
