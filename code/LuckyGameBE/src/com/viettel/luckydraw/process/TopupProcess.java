///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.viettel.luckydraw.process;
//
//import com.viettel.luckydraw.bo.MiniGame2018PlayTurn;
//import com.viettel.luckydraw.dao.LuckyGameDAO;
//import com.viettel.luckydraw.util.Config;
//import com.viettel.luckydraw.util.Constant;
//import com.viettel.luckydraw.util.SmsGwUtils;
//import com.viettel.luckydraw.ws.CallBccsWs;
//import com.viettel.luckydraw.ws.form.RequestBccsForm;
//import com.viettel.luckydraw.ws.form.ResponseBccsForm;
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.log4j.Logger;
//import utils.ProcessThreadMX;
//
///**
// *
// * @author HALT14
// */
//public class TopupProcess extends ProcessThreadMX {
//
////    private List<MiniGame2018PlayTurn> listPlayTurn = new ArrayList<MiniGame2018PlayTurn>();
//    private Logger log = Logger.getLogger(TopupProcess.class);
//
//    public TopupProcess(String threadName) {
//        super(threadName);
//    }
//
//    @Override
//    protected void process() {
//        try {
//
//            List<MiniGame2018PlayTurn> listPlayTurn = LuckyGameDAO.getInstance().getPlayTurn(Config.getInstance().getNumRecordPerCycle());
//            if (!listPlayTurn.isEmpty()) {
//                for (MiniGame2018PlayTurn playTurn : listPlayTurn) {
//                    processTopup(playTurn);
//                }
//            }
//            try {
//                Thread.sleep(Config.getInstance().getProcessSleepTime());
//            } catch (InterruptedException ex) {
//                log.error(ex.getMessage(), ex);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    public void processTopup(MiniGame2018PlayTurn playTurn) {
//
//        ResponseBccsForm topUpResponse = null;
//      
//        String smsContent = Config.getInstance().getSms(playTurn.getLang(), playTurn.getGiftCode());
//
//        String msisdn = playTurn.getMsisdn();
//
//        if (Config.getInstance().getGiftTypeTopup().contains(playTurn.getGiftType())) {
//            //Trao qua topup
//            topUpResponse = sendGift(msisdn, Config.getInstance().getAccountIdTopup(playTurn.getGiftType()), playTurn.getGiftValue());
//            if ("0".equals(topUpResponse.getErrorCode())) {
//                SmsGwUtils.getInstance().sendMtUnicode(msisdn, smsContent);
//            }
//        } else {
//            SmsGwUtils.getInstance().sendMtUnicode(msisdn, smsContent);
//        }
//        LuckyGameDAO.getInstance().updatePlayTurn(playTurn.getPlayId(), playTurn.getMsisdn(), topUpResponse != null ? topUpResponse.getErrorCode() : null, topUpResponse != null ? topUpResponse.getDescription() : null, "1");
//    }
//
//    public ResponseBccsForm sendGift(String msisdn, String accId, String value) {
//        //Goi sang CallBccsWs de thuc hien
//        RequestBccsForm request = new RequestBccsForm();
//        request.setRequestType(Constant.CALL_BCCS_ADD_BALANCE);
//        request.setMsisdn(msisdn);
//        request.setAccId(accId);
//        request.setValue(value);
//        request.setAddExpire(String.valueOf(Config.getInstance().getAddExpireData() * 60 * 60));
//        ResponseBccsForm response = null;
//
//        try {
//            response = CallBccsWs.getInstance().callBccsWs(request);
//        } catch (Exception e) {
//            log.error("Exception " + e.getMessage(), e);
//        }
//        return response;
//    }
//
//}
