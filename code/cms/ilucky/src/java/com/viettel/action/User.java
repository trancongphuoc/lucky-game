/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import com.viettel.util.VGenLog;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
/**
 *
 * @author hanhnv62
 */
public class User implements HttpSessionBindingListener {

    private static Map<String, HttpSession> logins = Collections.synchronizedMap(new HashMap<String, HttpSession>());
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        if (logins.containsKey(getUsername())) {
            HttpSession session = logins.remove(getUsername());
            VGenLog.info("----Login:" + username + "----Login exist!");
            if (session != null) {
                session.invalidate();
            }
            logins.put(getUsername(), event.getSession());
        } else {
            logins.put(getUsername(), event.getSession());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        logins.remove(getUsername());
        VGenLog.info("----Login:" + username + "----Logout!");
    }
}
