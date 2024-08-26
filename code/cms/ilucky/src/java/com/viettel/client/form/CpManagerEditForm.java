/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.io.File;
import java.sql.Timestamp;

/**
 *
 * @author hanhnv62
 */
public class CpManagerEditForm {
    String provider_name;
    String Oldprovider_name;
    String username;
    String Descriptions;
    String Status;
    Timestamp Time_create;
    String ID;
    
    String company;
    String contact;
    String address;
    String contract_code;
    String contract_name;
    String note;
    
    public CpManagerEditForm() {
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
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String Descriptions) {
        this.Descriptions = Descriptions;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Timestamp getTime_create() {
        return Time_create;
    }

    public void setTime_create(Timestamp Time_create) {
        this.Time_create = Time_create;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
