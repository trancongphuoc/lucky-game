/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.backend.bean;

import java.util.List;

/**
 *
 */
public class DynamicMenu {

    private List<KeyValue<String, String>> menus;
    private int currentPage;
    private int lengthPage;

    private int recordPerPage ;
    private String lineDown      ;
    private String nextChar      ;
    private String backChar      ;
    private String nextContent   ;
    private String backContent   ;
    private String splitMenu     ;
    
    private String contextName;
    private String contextValue;
    
    public DynamicMenu() {

    }

    public void clear() {
        this.menus.clear();
        this.menus = null;
        this.currentPage = 0;
    }

    public void setMenu(List<KeyValue<String, String>> menus) {
        this.menus = menus;
        
        this.lengthPage = menus.size() / recordPerPage;
        if (menus.size() % recordPerPage != 0) {
            lengthPage++;
        }
        this.currentPage = 0;
    }

    public String nextPage() {
        if (this.currentPage < this.lengthPage -1) {
            ++this.currentPage;
        }
        return seekPage(this.currentPage);
    }

    public String backPage() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }
        return seekPage(this.currentPage);
    }
    
    public String firstPage() {
        this.currentPage = 0;
        return seekPage(this.currentPage);
    }
    
    /**
     * phai kiem tra so page hien hanh co chon dung khong
     */
    public KeyValue<String, String> getMenu(int index) {
        if (index <= 0 || index > menus.size()) {
            return null;
        }
        
        if (index > recordPerPage) {
            return null;
        }
        
        int startPage = currentPage * recordPerPage + index -1;
        if (startPage < 0) {
           startPage = 0;
        }

        if (startPage >= menus.size()) {
            return null;
        }
        
        return menus.get(startPage);
    }
    
    private String seekPage(int page) {
        int startPage = page * recordPerPage;
        if (startPage >= menus.size()) {
            startPage = menus.size() - 1;
        }

        int endPage = startPage + recordPerPage ;

        if (endPage > menus.size()) {
            endPage = menus.size();
        }

        String pageContent = null;
        
        int index = 0;
        for (int i = startPage; i < endPage; i++) {
            KeyValue<String, String> menu = menus.get(i);
            if (pageContent == null) {
                pageContent = ++index + splitMenu + menu.getValue();
            } else {
                pageContent += lineDown + ++index + splitMenu + menu.getValue();
            }
        }
   
        String addition = "";
        
        if (page < lengthPage -1 && nextContent != null) {
            addition = lineDown + nextContent;
        } 
        
        if (page > 0 && backContent != null) {
            addition += lineDown + backContent;
        }
        
        if (!addition.isEmpty()) {
            pageContent += addition;
        }
        
        return pageContent;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public void setLineDown(String lineDown) {
        this.lineDown = lineDown;
    }

    public void setNextChar(String nextChar) {
        this.nextChar = nextChar;
    }

    public void setBackChar(String backChar) {
        this.backChar = backChar;
    }

    public void setNextContent(String nextContent) {
        this.nextContent = nextContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public void setSplitMenu(String splitMenu) {
        this.splitMenu = splitMenu;
    }

    public String getNextChar() {
        return nextChar;
    }

    public String getBackChar() {
        return backChar;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getContextValue() {
        return contextValue;
    }

    public void setContextValue(String contextValue) {
        this.contextValue = contextValue;
    }

    @Override
    public String toString() {
        return firstPage(); //To change body of generated methods, choose Tools | Templates.
    }

    
}
