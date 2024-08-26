/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.process;

import com.viettel.luckydraw.bo.ActionLog;
import com.viettel.luckydraw.dao.LuckyGameDAO;
import com.viettel.luckydraw.util.Constant;
import java.util.ArrayList;
import utils.BlockQueue;
import utils.ProcessThreadMX;

/**
 *
 * @author HALT14
 */


public class InsertLogProcess extends ProcessThreadMX {
    private static BlockQueue logQueue ;
    private ArrayList<ActionLog> listLog;
    public InsertLogProcess(String threadName) {
        super(threadName);
        this.logQueue = new BlockQueue(5000);
        listLog = new ArrayList<ActionLog>();
    }

    @Override
    protected void process() {
        try {
            ActionLog log = (ActionLog) logQueue.dequeue();
            if (log != null) {
                listLog.add(log);
            }
            if (listLog.size() >= Constant.NUM_RECORD_PER_INSERT_LOG || (listLog.size() > 0 && log == null)) {
                LuckyGameDAO.getInstance().insertActionLog(listLog);
                listLog.clear();
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    public static void endQueue(ActionLog log){
        logQueue.enqueue(log);
    }
}
