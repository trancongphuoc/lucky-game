/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

/**
 *
 * @author trungvt9
 */
public class TaskSQL {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskSQL(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
}
