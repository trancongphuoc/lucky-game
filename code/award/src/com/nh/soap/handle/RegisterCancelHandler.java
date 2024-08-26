/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.soap.handle;

import com.nh.bean.CreditCondition;
import com.nh.repository.CreditConditionDao;
import com.nh.soap.SoapRequest;
import com.nh.util.CallWS;
import com.nh.util.CountryCode;
import java.util.List;
import java.util.Map;

/**
 *
 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
 * xmlns:soap="http://soap.mps.ra.vntelco.com/">
<soapenv:Header/><soapenv:Body><soap:subRequest>
* <username>#USERNAME</username>
* <password>#PASSWORD</password>
<serviceid>#subservice_name</serviceid> <!-- La package -->
<msisdn>#MSISDN</msisdn>
<chargetime>#CHARGE_TIME</chargetime>
<params>#PARAMS</params> <!-- 0-dang ky, 1-fail - huy -->
<mode>#MODE</mode> <!-- check trang thai dich tu phia CP -->
<amount>#AMOUNT</amount>
<command>#FULLSMS</command> <!-- Noi dung SMS -->
<transid>#TRANSACTIONID</transid>
* </soap:subRequest>
</soapenv:Body>
</soapenv:Envelope>
 */
public class RegisterCancelHandler extends AbstractHandler{

    public RegisterCancelHandler(SoapRequest request) {
        super(request);
    }
    CreditConditionDao conditionDao = new CreditConditionDao();
    @Override
    public String process(SoapRequest request) {
        String value = "1|NOT_ENOUGH" ;
        
        try{
            if ("CHECK".equals(request.getDatas().get("mode"))){
                return "0|check success";
            }
            String msisdn = request.getDatas().get("msisdn");
            String transId = request.getDatas().get("transid");
            if (msisdn == null) {
                log.error("not found msisdn: ");
                return value;
            }
            List<CreditCondition> ocsConditions = conditionDao.findByType(CreditCondition.TYPE_OCS_CHECK);
            Map<String, String> ocsViews = CallWS.viewOcs(CountryCode.getCountryCode() + msisdn, transId);
            log.info("view ocs for: " + msisdn);
            boolean ocsValid = conditionDao.validCondition(ocsConditions, ocsViews);
            if (!ocsValid) {
                log.debug("No Advantise because, invalid ocs check: " + msisdn);
                return "1|ERROR";
            }
            value = "0|SUCCESS";
        } catch(Exception ex) {
            value = "1|ERROR";
            log.error("error when receive Register or Cancel request", ex);
        }
            
        return value;
    }

}
