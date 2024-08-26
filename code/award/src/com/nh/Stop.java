/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh;

import com.nh.excutor.TaskExcutor;
import com.nh.excutor.TaskManagement;


public class Stop {
    
    public static void main(String[] args) {
//        TaskExcutor.getInstance().stop();
        TaskManagement.getInstance().stop();
    }
}
