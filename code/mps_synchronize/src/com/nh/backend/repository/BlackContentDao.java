/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.backend.repository;

import com.nh.backend.bean.BlackContent;
import java.util.LinkedList;
import java.util.List;


public class BlackContentDao  extends GenericDao<BlackContent> {

    public BlackContent getActiveContent(){
        List<String> columnNames = new LinkedList<>();
        columnNames.add("status");
        List<Object> values = new LinkedList<>();
        values.add("1");
        
        return findByColumNames(columnNames, values);
    }
}
