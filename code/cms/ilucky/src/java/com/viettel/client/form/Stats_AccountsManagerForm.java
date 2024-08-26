/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.client.form;

import com.viettel.utils.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author hanhnv62
 */
public class Stats_AccountsManagerForm {
    String service_name;
    Date from_date;
    Date to_date;

    public Stats_AccountsManagerForm() {
         from_date = DateTimeUtils.getPreviousMonthDate();
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }
    
    
}
