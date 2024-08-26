/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.process;


import com.viettel.luckydraw.dao.LuckyGameDAO;
import com.viettel.luckydraw.util.Config;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.ProcessThreadMX;

/**
 *
 * @author MrCAP
 */



public class AddTopPrizeProcess  {
    
    private static final Logger log = Logger.getLogger(AddTopPrizeProcess.class);

    public void processInsertPrize() {
        
    
        try {
             log.info("=====> Start get top prize every week");
            if(Config.getInstance().getFlatProcessTopup()==1){
                   LuckyGameDAO.getInstance().insertPrizeForWeek();
                                    
            }
         
        //    MwinGameDAO.getInstance().insertPrizeResult(Constant.PRIZE_TYPE_CHAMPION);
         //   MwinGameDAO.getInstance().insertPrizeResult(Constant.PRIZE_TYPE_GOALS);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        
    }
    public void processAddPrize() {
        
    
        try {
             log.info("=====> Start Add prize every week");
            if(Config.getInstance().getFlatProcessTopup()==1){
                   LuckyGameDAO.getInstance().addGiftForTopPlay();
                                    
            }
         
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        
    }

}
