/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.io.File;

/**
 *
 * @author linhnd13
 */
public class CpManagerUploadForm {
    String provider_name;
    String Oldprovider_name;
    String Username;
    String Descriptions;
    String company;
    String contact;
    File path_local_file;
    
    public CpManagerUploadForm() {
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getOldprovider_name() {
        return Oldprovider_name;
    }

    public void setOldprovider_name(String Oldprovider_name) {
        this.Oldprovider_name = Oldprovider_name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String Descriptions) {
        this.Descriptions = Descriptions;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public File getPath_local_file() {
        return path_local_file;
    }

    public void setPath_local_file(File path_local_file) {
        this.path_local_file = path_local_file;
    }
    
}
