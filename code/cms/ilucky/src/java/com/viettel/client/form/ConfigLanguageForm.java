/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class ConfigLanguageForm implements Serializable{

    String key;
    String value;
    String id;

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public ConfigLanguageForm() {
    }

}
