/**
 *
 * handsome boy
 */

package com.nh.backend.bean;

import java.util.Hashtable;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 */
public class TransactionInfo{
    private static Logger logger = Logger.getLogger(TransactionInfo.class);
    
    private String transactionId;
    private String msidn;
    private String subServcieName;
    private Long created;
    private Map<String,String> params;
    private Map<String,Object> datas;

    public TransactionInfo(String transactionId, String msidn) {
        this.transactionId = transactionId;
        this.msidn = msidn;
        created = System.currentTimeMillis();
        params = new Hashtable<String, String>();
        datas = new Hashtable<String, Object>();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMsidn() {
        return msidn;
    }

    public void setMsidn(String msidn) {
        this.msidn = msidn;
    }
    
    public void put(String key, String value) {
        logger.info("put context:" + key + "=" + value + ", transactionId: " + transactionId );
        params.put(key, value);
    }

    public Map<String, String> getParams() {
        return params;
    }
    
    public String remove(String key) {
        return params.remove(key) ;
    }
    
    public Long getCreated() {
        return created;
    }

    public String getSubServcieName() {
        return subServcieName;
    }

    public void setSubServcieName(String subServcieName) {
        this.subServcieName = subServcieName;
    }
    
    public void putData(String key, Object data) {
        logger.info("put Data:" + key + "=" + data + ", transactionId: " + transactionId );
        datas.put(key, data);
    }
    
    public Object getData(String key) {
        return datas.get(key);
    }
    
    public Object removeData(String key) {
        return datas.remove(key);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("transactionId: ").append(getTransactionId());
        sb.append(", msidn: ").append(getMsidn());
        return sb.toString();
    }
    
    public String paresContext(String message) {
        if (params == null || message == null 
                || message.isEmpty()) {
            return message;
        }
        String result = message;
//        logger.debug("Begin Parse content : " + result);
        while (result.contains("#") && checkIncludeContext(result, params)){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String k = entry.getKey();
                String val = entry.getValue();
                result = result.replace(k, val);
//                logger.debug("-- Key:" + k + " Value: " + val);
            }
        }
        
        return result;
    }
    
    private boolean checkIncludeContext(String message, Map<String, String> context){
        if (message == null || context == null) {
            return false;
        }
        for (Map.Entry<String, String> entry : context.entrySet()) {
            String string = entry.getKey();
            if (message.contains(string)) {
                return true;
            }
        }
        return false;
    }
    
}
