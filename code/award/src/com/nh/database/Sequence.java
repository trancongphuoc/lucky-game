package com.nh.database;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;


/**
 * @author hadc
 * */
public class Sequence {
    private final static Logger log = Logger.getLogger(Sequence.class);
    /**
     * 
     * @author hadc
     * @since 18.08.2014
     * 
     * */
    public static synchronized String getNextID(String tableName) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
//        String sequenceName = getSequenceName(tableName);
//        String sqlSequence = "Select " + sequenceName + " from dual";
//        return DB.queryForLong(sqlSequence);
    }
	
    private static Map<String,String> sequeceNames = new HashMap<String, String>();
    /**
     * 
     * @author hadc
     * @since 18.08.2014
     * */
    public static synchronized String getSequenceName(String tableName) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
//        
//        String sequenceName = sequeceNames.get(tableName);
//        if (sequenceName != null) {
//            return sequenceName;
//        }
//        sequenceName = tableName + "_Seq";
//        
//        try{
//            createNewSequence(sequenceName);
//        } catch(Exception x){
////            log.error("error when creat sequence", x);
//        };
//        sequenceName = sequenceName + ".NEXTVAL"; 
//        sequeceNames.put(tableName, sequenceName);
//        return sequenceName;
    }

    /**
     * CREATE SEQUENCE C_POSITION_SEQ INCREMENT BY 1 START WITH 0 MINVALUE 0 NOCACHE
     * */
    public static synchronized int createNewSequence(String sequenceName){
        String sql = "CREATE SEQUENCE " + sequenceName + 
                        " INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE";

        int result = DB.update(sql);
        return result;
    }
	
    /**
     * kiem tra sequence xem dung chua? neu sequence nho hon ID max cua tableName thi
     * phai cap nhat lai.
     * truong primary key phai dat ten la: ID
     * @author hadc
     * */
    public static synchronized boolean updateSequence(String tableName){
        boolean state = true;
//        String sql = "SELECT MAX(ID) FROM " + tableName;
//        long maxId = DB.queryForLong(sql);
//        long nextVal = getNextID(tableName);
//        if (nextVal < maxId) {
//                sql = "ALTER SEQUENCE " + getSequenceName(tableName) 
//                        + " INCREMENT BY " + (maxId - nextVal);
//                int result = DB.update(sql);
//                state = result > 0;
//        } else {
//                return true;
//        }

        return state;
    }
	
}
