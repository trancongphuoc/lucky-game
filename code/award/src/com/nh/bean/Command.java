/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.bean;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hadc
 */
public final class Command {
    public static final int IDLE_TIME = 10 * 1000;
    public static final int ONE_MINUTE = 60 * 1000;
    public static final int ONE_HOUR = 60 * 60 * 1000;
    public static final int ONE_DAY = 24 * 60 * 60 * 1000;
    
    /* (1-59) */
    private List<Integer> minutes;
    /* 1-24)*/
    private List<Integer> hours;
    /* 1-31)*/
    private List<Integer> dayOfMonths;
    /* 1-12)*/
    private List<Integer> months;
    /* (0-7)*/
    private List<Integer> dayOfWeeks;
    
    private String command;
    private long nextCheckTime;
    
    private String textPlain;
    
    public Command(String[] args){
        if (args.length < 6) {
            throw new IllegalArgumentException("wrong format crontab");
        }
        minutes = convertToInteger(args[0],Calendar.MINUTE);
        hours = convertToInteger(args[1],Calendar.HOUR_OF_DAY);
        dayOfMonths = convertToInteger(args[2],Calendar.DAY_OF_MONTH);
        months = convertToInteger(args[3],Calendar.MONTH);
        dayOfWeeks = convertToInteger(args[4],Calendar.DAY_OF_WEEK);
        
        command = "";
        for(int i = 5; i < args.length;i++) {
            command = command + " " +  args[i].trim();
        }
        command = command.trim();
        nextCheckTime = System.currentTimeMillis();
    }
    
    public List<Integer> convertToInteger(String args, int type){
        if (args == null) {
            return null;
        }
        
        List<Integer> integers = new LinkedList<>();
        
        if ("*".equals(args)){
            return integers;
        }
        
        if (args.startsWith("*/")){
            String tm_distance= args.replace("*/", "");
            if (!isNumeric(tm_distance)){
                throw new IllegalArgumentException("wrong format crontab, must be number: " + args);
            }
            
            int distance = Integer.parseInt(tm_distance);
            
            switch (type) {
                case Calendar.MINUTE:
                    for(int i = 0; i < 60 ; i= i+ distance) {
                        integers.add(i);
                    }
                    break;
                case Calendar.HOUR_OF_DAY:
                    for(int i = 0; i < 24 ; i= i+ distance) {
                        integers.add(i);
                    }
                    break;
                case Calendar.DAY_OF_MONTH:
                    for(int i = 0; i < 31 ; i= i+ distance) {
                        integers.add(i);
                    }
                    break;
                case Calendar.MONTH:
                    for(int i = 0; i < 12 ; i= i+ distance) {
                        integers.add(i);
                    }
                    break;
                case Calendar.DAY_OF_WEEK:
                    for(int i = 0; i < 7 ; i= i+ distance) {
                        integers.add(i);
                    }
                    break;
                default:
                    break;

            }
            
            return integers;
        }
        
        String[] tm = args.split(",");
        for (String s : tm) {
            if (!isNumeric(s)){
                throw new IllegalArgumentException("wrong format crontab, must be number");
            }
            
            int v = Integer.parseInt(s);
            integers.add(v);
        }
        return integers;
    }
    
    private boolean isNumeric(String strNumber) {
        return strNumber != null && strNumber.matches("[0-9]+$");
    }
    
    public boolean isRuntime(){
        if (System.currentTimeMillis() < nextCheckTime) {
            return false;
        }
        
        nextCheckTime = System.currentTimeMillis() + IDLE_TIME;
        
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        
        if (!minutes.isEmpty()) {
            if (!minutes.contains(minute)){
                return false;
            }
        }
        
        if (!hours.isEmpty()) {
            if (minutes.isEmpty()) {
                nextCheckTime = System.currentTimeMillis() + (60 - minute) * ONE_MINUTE + ONE_MINUTE;
            }
            
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (!hours.contains(hour)){
                return false;
            }
            
        }
        
        if (!dayOfMonths.isEmpty()) {
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            if (!dayOfMonths.contains(dayOfMonth)){
                return false;
            }
        }
        
        if (!months.isEmpty()) {
            int month = c.get(Calendar.MONTH);
            if (!months.contains(month)){
                return false;
            }
        }
        
        if (!dayOfWeeks.isEmpty()) {
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (!dayOfWeeks.contains(dayOfWeek)){
                return false;
            }
        }
        
        if (dayOfWeeks.isEmpty()
                && months.isEmpty()
                && dayOfMonths.isEmpty()
                && hours.isEmpty()
                && minutes.isEmpty()) {
            return false;
        }
        
        return true;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return textPlain;
    }

    public String getTextPlain() {
        return textPlain;
    }

    public void setTextPlain(String textPlain) {
        this.textPlain = textPlain;
    }
    
}
