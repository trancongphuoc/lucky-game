/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author hanhnv62
 */
public class CpManagerForm {
    String provider_name;
    String status;


    String username;
    
    //lamnt22
    String decription;
    String company;
    String contact;
    String address;
    String contract_code;
    String contract_name;
    String note;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public CpManagerForm() {
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }
    
    
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String Status) {
//        this.Status = Status;
//    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * @return the decription
     */
    public String getDecription() {
        return decription;
    }

    /**
     * @param decription the decription to set
     */
    public void setDecription(String decription) {
        this.decription = decription;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the contract_code
     */
    public String getContract_code() {
        return contract_code;
    }

    /**
     * @param contract_code the contract_code to set
     */
    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    /**
     * @return the contract_name
     */
    public String getContract_name() {
        return contract_name;
    }

    /**
     * @param contract_name the contract_name to set
     */
    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

        
}
