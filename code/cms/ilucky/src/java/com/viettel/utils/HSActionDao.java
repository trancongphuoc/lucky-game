/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.utils;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.database.utils.DataStore;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

public class HSActionDao extends ActionSupport {

 
 public static DataStore ivrDb;
    @SuppressWarnings("empty-statement")
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public static void main(String[] args) throws Exception {
    }
}
