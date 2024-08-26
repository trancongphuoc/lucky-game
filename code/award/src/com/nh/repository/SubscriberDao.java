package com.nh.repository;

import com.nh.bean.Subscriber;
import com.nh.database.DB;
import java.util.LinkedList;
import java.util.List;

public class SubscriberDao extends GenericDao<Subscriber> {
    
    public Subscriber findByMsisdnAndServiceCode(String msisdn, String serviceCode){
        List<String> columnNames = new LinkedList<>();
        columnNames.add("user_id");
        columnNames.add("SERVICE_CODE");
        List<Object> values = new LinkedList<>();
        values.add(msisdn);
        values.add(serviceCode);
        
        return findByColumNames(columnNames, values);
    }
    
    public Subscriber findByMsisdn(String msisdn){
        String sql = "SELECT ROWID, a.* " +
                " from " + getTableName() + " a where rownum <= 1 and a.msisdn=? " ;
        
        List<Object> values = new LinkedList<>();
        values.add(msisdn);
        List<Subscriber> es = DB.query(sql,  values, mapper);
        if (es != null && !es.isEmpty()){
            return es.remove(0);
        }
        
        return null;
    }
    
    public List<Subscriber> getSubNotify(){
        String sql = "SELECT * " +
                " from V_SUBSCRIBER_NOTIFY " ;
        List<Subscriber> es = DB.query(sql,  mapper);
        if (es != null && !es.isEmpty()){
            return es;
        }
        
        return null;
    }
    
    public boolean updateStatusByMsisdn(int status, String msisdn){
        String sql = "Update " + getTableName() + " Set Status =? WHERE Msisdn = ?";
    	int result = DB.update(sql, status, msisdn);
    	return result > 0;
    }
    
    public boolean updateLastNotifyByMsisdn(String msisdn){
        String sql = "Update " + getTableName() 
                + " Set last_send_notify =sysdate, WHERE Msisdn = ? ";
    	int result = DB.update(sql, msisdn);
    	return result > 0;
    }
    
    public boolean updateRenewByMsisdn(int status, String msisdn){
        String sql = "Update " + getTableName() + " Set LAST_MONFEE_CHARGE_TIME = sysdate, " 
                + " NEXT_MONFEE_CHARGE_TIME = sysdate + 1, "
                + " MONFEE_SUCCESS_COUNT = MONFEE_SUCCESS_COUNT + 1, "
                + " Status =? WHERE Msisdn = ? ";
    	int result = DB.update(sql, status, msisdn);
    	return result > 0;
    }
    
    public boolean updateRegisterByMsisdn(int status, String msisdn){
        String sql = "Update " + getTableName() + " Set REGISTER_TIME = sysdate, " 
                + " LAST_MONFEE_CHARGE_TIME = sysdate + 1, "
                + " Status =? WHERE Msisdn = ? ";
    	int result = DB.update(sql, status, msisdn);
    	return result > 0;
    }
    
    public boolean updateCancelByMsisdn(int status, String msisdn){
        String sql = "Update " + getTableName() + " Set CANCEL_TIME = sysdate, " 
                + " Status =? WHERE Msisdn = ? ";
    	int result = DB.update(sql, status, msisdn);
    	return result > 0;
    }
    
}