/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

/**
 *
 * @author hanhnv62
 */
public class CP_webservice_accountsManagerEditForm {
    String webservice_id;
    String oldwebservice_id;
    String sub_service_name;
    String user_name;
    String password;
    String url;
    String params;
    String webservice_type;
    String soap_action;
    String tag_prefix;
    String webservice_name;
    String descriptions;
    String raw_xml;
    String timeout;

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
    String return_tag;

    public CP_webservice_accountsManagerEditForm() {
        
    }

    public String getWebservice_id() {
        return webservice_id;
    }

    public void setWebservice_id(String webservice_id) {
        this.webservice_id = webservice_id;
    }

    public String getOldwebservice_id() {
        return oldwebservice_id;
    }

    public void setOldwebservice_id(String oldwebservice_id) {
        this.oldwebservice_id = oldwebservice_id;
    }

    public String getSub_service_name() {
        return sub_service_name;
    }

    public void setSub_service_name(String sub_service_name) {
        this.sub_service_name = sub_service_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getWebservice_type() {
        return webservice_type;
    }

    public void setWebservice_type(String webservice_type) {
        this.webservice_type = webservice_type;
    }

    public String getSoap_action() {
        return soap_action;
    }

    public void setSoap_action(String soap_action) {
        this.soap_action = soap_action;
    }

    public String getTag_prefix() {
        return tag_prefix;
    }

    public void setTag_prefix(String tag_prefix) {
        this.tag_prefix = tag_prefix;
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

    public String getRaw_xml() {
        return raw_xml;
    }

    public void setRaw_xml(String raw_xml) {
        this.raw_xml = raw_xml;
    }

    public String getReturn_tag() {
        return return_tag;
    }

    public void setReturn_tag(String return_tag) {
        this.return_tag = return_tag;
    }
         
    
}
