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
public class ServicesVcgwinfoForm {

    private String status;
    private String providerName;
    

    public ServicesVcgwinfoForm() {
    }

    public void resetForm(){
        this.status = "-1";
        this.providerName = "";
  
    }

       
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

   

}
