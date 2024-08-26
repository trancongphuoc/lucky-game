/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.cdr;


public class CdrFile {
    private final long created;
    private String cdrFile;

    public CdrFile(String cdrFile) {
        this.cdrFile = cdrFile;
        this.created = System.currentTimeMillis();
    }

    public long getCreated() {
        return created;
    }

    public String getCdrFile() {
        return cdrFile;
    }

    public void setCdrFile(String cdrFile) {
        this.cdrFile = cdrFile;
    }

    @Override
    public String toString() {
        return cdrFile;
    }
    
    
}
