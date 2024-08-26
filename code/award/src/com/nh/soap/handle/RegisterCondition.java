/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.bean.CreditCondition;
import com.nh.database.DB;
import com.nh.repository.CreditConditionDao;
import com.nh.soap.SoapRequest;
import static com.nh.soap.handle.AbstractHandler.log;
import com.nh.util.CallWS;
import com.nh.util.CountryCode;
import java.util.List;
import java.util.Map;


public class RegisterCondition extends AbstractHandler{

    public RegisterCondition(SoapRequest request) {
        super(request);
    }
    CreditConditionDao conditionDao = new CreditConditionDao();
    
    @Override
    public String process(SoapRequest request) {
        String value = "1|NOT_ENOUGH_CONDITION" ;
        
        try{
            String msisdn = request.getDatas().get("msisdn");
            String transId = request.getDatas().get("transid");
            if (msisdn == null) {
                log.error("not found msisdn: ");
                return value;
            }
            
            msisdn = CountryCode.formatMobile(msisdn);
            
            if (GlobalConfig.getInt("trial_enable", 0) > 0){
                int i = DB.queryForInt(GlobalConfig.get("trial_sql_check"), msisdn);
                if (i <= 0) {
                    log.info("not trial user: " + msisdn);
                    return "1|NOT_TRIAL";
                }
                log.info("trial user: " + msisdn);
            }
            
            List<CreditCondition> ocsConditions = conditionDao.findByType(CreditCondition.TYPE_OCS_REGISTER);
            if (ocsConditions != null && !ocsConditions.isEmpty()) {
                Map<String, String> ocsViews = CallWS.viewOcs(CountryCode.getCountryCode() + msisdn, transId);
                log.info("view ocs for: " + msisdn + ", transid:" + transId + ", view: " + ocsViews);
                boolean ocsValid = conditionDao.validCondition(ocsConditions, ocsViews);
                if (!ocsValid) {
                    log.debug("not enough condition, invalid ocs check: " + msisdn);
                    return "1|NOT_ENOUGH_CONDITION";
                }
            }
            value = "0|ENOUGH_CONDITION";
        } catch(Exception ex) {
            value = "-1|ERROR";
            log.error("error when receive Register or Cancel request", ex);
        }
            
        return value;
    }

}

