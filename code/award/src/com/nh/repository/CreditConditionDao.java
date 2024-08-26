/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.repository;

import com.nh.bean.CreditCondition;
import com.nh.cache.HCache;
import com.nh.database.DB;
import com.nh.util.CommonUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreditConditionDao extends GenericDao<CreditCondition> {
    
    private final static String PARTER_TIME = "yyyy-MM-dd HH:mm:ss";
    private final static String PARTER_DAY = "yyyy-MM-dd";
    private final static String HOUR_SAPARATE = ":";
    private final static String VALUES_AND = "&amp;";
    
    private SimpleDateFormat timeFormat = new SimpleDateFormat(PARTER_TIME);
    private SimpleDateFormat dayFormat = new SimpleDateFormat(PARTER_DAY);
    
    private static final HCache<String, List<CreditCondition>> cache_type 
            = new HCache<>("CreditConditionDao_Type", 5, 100, 20);

    public List<CreditCondition> findByType(String type) {
        List<CreditCondition> conditions = cache_type.get(type);
        if (conditions != null) {
            return conditions;
        }
        String sql = "SELECT * FROM CREDIT_CONDITION WHERE TYPE =? "
                + " AND STATUS ='1' order by piority asc";

        conditions = DB.query(sql, getMapper(), type);
        if (conditions != null) {
            cache_type.put(type, conditions);
        }

        return conditions;
    }
    
    public boolean validCondition(List<CreditCondition> conditions, String[] values) {
        if (conditions == null || conditions.isEmpty() 
                || values == null || values.length == 0) {
            return false;
        }
        for (CreditCondition condition : conditions) {
            String key = condition.getKey();
            
            if (!CommonUtil.isNumeric(key)) {
                return false;
            }
            int index = Integer.parseInt(key);
            if (values.length < index || index < 0) {
                return false;
            }
            
            String value = values[index];
            
            if (!valid(condition, value)) {
                log.debug("invalid condition: " + condition + ", for: " + Arrays.toString(values));
                return false;
            }
        }
        return true;
    }
    
    private boolean valid(CreditCondition condition, String value){
        String expectValue = condition.getValue();
        String operator = condition.getOperator();
        String dataType = condition.getDataType();
        if (CreditCondition.OPERATOR_EQUAL.equals(operator)) {
            return expectValue.equals(value);
        } else if (CreditCondition.OPERATOR_IN.equalsIgnoreCase(operator)) {
            List<String> expectValues = Arrays.asList(expectValue.split(CreditCondition.VALUES_SPLIT));
            return expectValues.contains(value);
        } else if (CreditCondition.OPERATOR_BIGGER.equalsIgnoreCase(operator)
                || CreditCondition.OPERATOR_SMALLER.equalsIgnoreCase(operator)
                ) {
            if (CreditCondition.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType)) {
                if (value == null || !CommonUtil.isNumeric(value)) {
                    return false;
                }
                double v = Double.parseDouble(value);
                double ev = Double.parseDouble(expectValue);
                if (CreditCondition.OPERATOR_BIGGER.equalsIgnoreCase(operator)) {
                    return v > ev;
                }
                
                return v < ev;
            }
            
            if (CreditCondition.DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                if (value == null || !CommonUtil.isNumeric(value)) {
                    return false;
                }
                
                if (value.contains(VALUES_AND)) {
                    if (!expectValue.contains(VALUES_AND)) {
                        return false;
                    }
                    String[] evs = expectValue.split(VALUES_AND);
                    String[] es = expectValue.split(VALUES_AND);
                    if (evs.length != es.length) {
                        return false;
                    }
                    
                    int i = 0;
                    for (String ev : evs) {
                        if (ev.length() > 1) {
                            break;
                        }
                        i++;
                    }
                    value = es[i].trim();
                    expectValue = evs[i].trim();
                }
                
                Date v = getTime(value);
                Date ev = getTime(expectValue);
                
                if (v == null || ev == null){
                    return false;
                }
                
                if (CreditCondition.OPERATOR_BIGGER.equalsIgnoreCase(operator)) {
                    return v.after(ev);
                }
                
                return v.before(ev);
            }
        }
        
        return false;
    }
    
    private Date getTime(String value) {
        Date v = null;
        try {
            if (value != null
                    && PARTER_TIME.length() == value.length()
                    && value.contains(HOUR_SAPARATE)) {

                v = timeFormat.parse(value);
            } else if (value != null
                    && PARTER_DAY.length() == value.length()
                    && !value.contains(HOUR_SAPARATE)) {
                v = dayFormat.parse(value);
            }
        } catch (ParseException ex) {
            v = null;
        }
        return v;
    }
    
    public boolean validCondition(List<CreditCondition> conditions, Map<String,String> values) {
        if (conditions == null || conditions.isEmpty() 
                || values == null || values.isEmpty()) {
            return true;
        }
        for (CreditCondition condition : conditions) {
            String key = condition.getKey();
            
            if (!values.containsKey(key)) {
                return false;
            }
            
            String value = values.get(key);
            
            if (!valid(condition, value)) {
                log.debug("invalid condition: " + condition + ", for: " + value);
                return false;
            }
            
            log.info("valid condition: " + condition + ", for: " + value);
        }
        return true;
    }
}
