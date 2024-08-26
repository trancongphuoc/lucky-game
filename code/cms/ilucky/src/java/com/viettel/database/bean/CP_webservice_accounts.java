/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.bean;

/**
 *
 * @author hanhnv62
 */
public class CP_webservice_accounts {
    String url;
    String sub_service_name;
    String webservice_name;
    String descriptions;

    public CP_webservice_accounts(String url, String sub_service_name, String webservice_name, String descriptions) {
        this.url = url;
        this.sub_service_name = sub_service_name;
        this.webservice_name = webservice_name;
        this.descriptions = descriptions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getWebservice_name() {
        return webservice_name;
    }

    public void setWebservice_name(String webservice_name) {
        this.webservice_name = webservice_name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    
    
}
