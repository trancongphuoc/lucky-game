package com.nh.excutor;

import com.nh.GlobalConfig;
import com.nh.bean.Award;
import com.nh.bean.CdrLog;
import com.nh.bean.Winner;
import com.nh.bean.SmsData;
import com.nh.bean.Subscriber;
import com.nh.cdr.SendSmsProcess;
import com.nh.database.DatabaseConnectionPool;
import com.nh.proccess.InsertCdrProcess;
import com.nh.repository.AwardDao;
import com.nh.repository.SubscriberDao;
import com.nh.repository.WinnerDao;
import com.nh.util.CallWS;
import static com.nh.util.CallWS.HTTP_TIMEOUT;
import com.nh.util.CountryCode;
import com.nh.util.EncryptUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author hadc
 */
public class TaskExcutor implements Runnable {

    private static final Logger log = Logger.getLogger(TaskExcutor.class);
    private final static int DEFAULT_SLEEP = 5 * 1000; // 5 seconds
    private final static int MIN_THREAD_NEVER_YIELD = 1; // 1 thread

    private static final String SEQUENCE_TEMPLATE = "0000000";

    private final String name;
    private boolean running = false;
    private final int threadIndex;
//    private static TaskExcutor taskExcutor;
    
//     private final String smsAdvertis ;
    private final String smsUrl;
    private final String smsUser;
    private final String smsPass;
    private final String smsShortCode;
    private final String smsAlias;
//    private int smsWait = 45s000;
    
    public TaskExcutor(int threadIndex) {
        this.threadIndex = threadIndex;
        this.name = "TaskExcutor-" + threadIndex;
        
//        smsAdvertis = GlobalConfig.getSmsAdvertis();
         smsUrl = GlobalConfig.getSmsUrl();
         smsUser = GlobalConfig.getSmsUsername();
         smsPass = GlobalConfig.getSmsPassword();
         smsShortCode = GlobalConfig.getSmsShortcode();
         smsAlias = GlobalConfig.getSmsAlias();
//         smsWait = GlobalConfig.getInt("sms_advertise_wait", 45000);
    }

    public void start() {
        if (!running) {
            Thread t = new Thread(this);
            t.setName(name + "_" + System.currentTimeMillis());
            t.setDaemon(false);
            running = true;
            t.start();
        }
    }

    public void stop() {
        running = false;
    }

    private String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            for (;;) {
                String command = TaskQueue.getInstance().dequeue();
                if (command != null) {
                    process(command);
                }
                Thread.sleep(10000L);
            }
        } catch (Exception ex) {
            log.error(getName() + "error when excute: ", ex);
        }
    }
    
    private static final String JOB_WEEKLY = "WEEKLY";
    
    private void process(String command) {
        if (JOB_WEEKLY.equals(command)) {
            log.info("do job WEEKLY now");
            processWeekly();
        }  else {
            log.info("unknow job: " + command);
        }
        
    }
    
    private void processWeekly() {
        WinnerDao winnerDao = new WinnerDao();
        List<Winner> weekWin = winnerDao.getWinnerWeekly();
        if (weekWin == null || weekWin.isEmpty()) {
            log.info("dont have Winner now");
            return;
        }
        long startTime = System.currentTimeMillis();
        AwardDao awardDao = new AwardDao();
        
        for (Winner win : weekWin) {
            try {
                boolean updateOk = winnerDao.updateProcessedWeekly(win.getId());
                if (!updateOk) {
                    log.error("Winner update not updated: " + win);
                    continue;
                }
                log.info("Winner update processed: " + win);
                
                CdrLog cdr = new CdrLog();
                cdr.REQUEST_TIME = new Timestamp(System.currentTimeMillis());
                String msisdn = win.getMsisdn();
                String transid = UUID.randomUUID().toString();
                
                
                String sub_service = GlobalConfig.get("mps.sub_service");
                String serviceName = GlobalConfig.get("mps.service");
                String PRO =  GlobalConfig.get("mps.provider");
                
                String awardCode = win.getAwardCode();
                Award award = awardDao.getAwardByCode(awardCode);
                if (award == null) {
                    log.error("abort winner, not found award: " + awardCode);
                    continue;
                }
                String cmd = award.getMpsCmd();
                String amount = award.getPrice();//
                String cate = award.getMpsCate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String SESS = "998" + dateFormat.format(new Date());
                
                Map<String,String> moreParams = new HashMap<>();
                int point = win.getPoint();
//                moreParams.put("POINT", point + "");
                moreParams.put("CODE", win.getCode() + "");
                moreParams.put("RANK", win.getRank()+ "");
                
                String value = EncryptUtil.sendRequestToMps(PRO, serviceName, sub_service,
                        msisdn, cmd, amount,
                        sub_service + "|" + cmd, transid, SESS,
                        cate, sub_service, moreParams);
                log.info("command: " + cmd + ",msisdn:  " + msisdn + " => status: " + value);
                
                if ("REGISTER".equals(cmd) && value.startsWith("408")) {
                    log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                    value = "0";
                }
                
                if ("CANCEL".equals(cmd) && value.startsWith("411")) {
                    log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                    value = "0";
                }
                
                if ("CANCEL".equals(cmd) && value.startsWith("414")) {
                    log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                    value = "0";
                }
                
                if ("RESTORE".equals(cmd) && value.startsWith("408")) {
                    log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                    value = "0";
                }
                
                if (value.startsWith("0")) {
                    log.info("convert code: " + value + "=> 0, for cmd: " + cmd);
                    value = "0";
                }
                
                if (value.startsWith("401")) {
                    log.info("convert code: " + value + "=> 401, for cmd: " + cmd);
                    value = "401";
                }
                
                if (value.startsWith("408")) {
                    log.info("convert code: " + value + "=> 408, for cmd: " + cmd);
                    value = "408";
                }
                
                cdr.ID = win.getId();
                cdr.MSISDN = CountryCode.formatMobile(msisdn);
                cdr.TRANS_ID = transid;
                cdr.RESPONSE_TIME = new Timestamp(System.currentTimeMillis());;
                cdr.RESPONSE_CODE = value;
                if (cdr.RESPONSE_CODE != null && cdr.RESPONSE_CODE.length() > 20) {
                    cdr.RESPONSE_CODE = cdr.RESPONSE_CODE.substring(0, 19);
                }
                cdr.SERVICE_NAME = serviceName;
                cdr.SUB_SERVICE_NAME = sub_service;
                
                cdr.CMD = cmd;
                if (amount != null && !amount.isEmpty()) {
                    cdr.PRICE = Double.valueOf(amount);
                } else {
                    cdr.PRICE = 0;
                }
                
                cdr.CHANNEL = "SYS";
                InsertCdrProcess.getInstance().enqueue(cdr);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(TaskExcutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        log.info(getName() + "-end run: weekly, duration (ms):" + (System.currentTimeMillis() - startTime));
    }
    
    
    private Map<String,String> getWinnerProperties(Winner win) {
        Map<String,String> pros = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        pros.put("PRICE", win.getCode() == null ? "" : win.getAwardCode().replace("DAY", "").replace("MONTH", "").replace("YEAR", ""));
        pros.put("CODE", win.getCode() == null ? "" : win.getCode());
        pros.put("WIN_DAY", win.getWinDay()== null ? "" : format.format(win.getWinDay()));
        return pros;
    }
    
    private void sendSms(String content, String msisdn, String id) {
        SmsData sms = new SmsData(smsUrl, smsUser, smsPass, msisdn, content, smsShortCode, smsAlias, "TEXT");
        sms.setSendTime(System.currentTimeMillis() );
        sms.setId(id);
        SendSmsProcess.getInstance().enqueue(sms);
    }

}
