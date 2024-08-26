/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.repository;

import com.nh.backend.bean.WhiteList;
import java.util.LinkedList;
import java.util.List;


public class WhiteListDao  extends GenericDao<WhiteList> {

    public WhiteList getActiveContent(){
        List<String> columnNames = new LinkedList<>();
        columnNames.add("status");
        List<Object> values = new LinkedList<>();
        values.add("1");
        
        return findByColumNames(columnNames, values);
    }
}
