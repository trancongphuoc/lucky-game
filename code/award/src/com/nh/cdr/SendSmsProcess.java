/**
 *
 * handsome boy
 */

package com.nh.cdr;

import com.nh.GlobalConfig;
import com.nh.bean.MTSMS;
import com.nh.bean.SmsData;
import static com.nh.cdr.SendSmsProcess.log;
import com.nh.util.CallWS;
import com.nh.util.CommonUtil;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc 
 */
public class SendSmsProcess {
    protected final static Logger log = Logger.getLogger(SendSmsProcess.class);
    private final static int THREAD_SEND_SMS = 3;
    
    private final LinkedHashMap<String, SmsData> queue = new LinkedHashMap<>();
    private final Object lock = new Object();
    
    private static SendSmsProcess _instance = null;
    private final Set<String> advertiseHours;
    
    private SendSmsProcess() {
        String tmAdvertisHour = GlobalConfig.getAdvertiseHour();
        advertiseHours = CommonUtil.convertHashSet(tmAdvertisHour);
    }
    
    public static SendSmsProcess getInstance() {
        if (_instance == null) {
            _instance = new SendSmsProcess();
            for(int i = 0; i < THREAD_SEND_SMS; i++) {
                SmsSender sender = new SmsSender(i, _instance);
                Thread thread = new Thread(sender);
                thread.setName("SendSmsProcess-" + i);
                thread.start();
            }
        }
        return _instance;
    }
    
    public boolean isInSentTime() {
        if (advertiseHours == null || advertiseHours.isEmpty()) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);

        return advertiseHours.contains(String.valueOf(hour));
    }
   
    public void enqueue(SmsData sms) {
        synchronized (lock) {
            queue.put(sms.getId(), sms);
            log.info("sms enqueue" + queue.size() + ": " + sms);
        }
    }

    public SmsData dequeue() {
        synchronized (lock) {
            if (!isInSentTime()) {
                return null;
            }
            
            if (queue.isEmpty()) {
                return null;
            }
            
            for (Map.Entry<String, SmsData> entry : queue.entrySet()) {
                String key = entry.getKey();
                SmsData smsData = entry.getValue();
                if (System.currentTimeMillis() > smsData.getSendTime()) {
                    return queue.remove(key);
                }
            }
            
            return null;
        }
    }

}
    
class SmsSender implements Runnable {
        
        private final String threadName;
        private final SendSmsProcess queue;
        
        public SmsSender(int i, SendSmsProcess queue) {
            this.threadName = SmsSender.class.getName() + "-" + i;
            this.queue = queue;
        }
        
        public String getThreadName() {
            return this.threadName;
        }
        
        @Override
        public void run() {
            while (true) {
                try {
                    SmsData smsData = queue.dequeue();
                    if (smsData != null) {
                        process(smsData);
                    } else {
                        Thread.sleep(3000);// sleep 3s
                    }
                } catch (Exception ex) {
                    log.error(ex.toString(), ex);
                }
            }
        }
        
        private void process(SmsData sms) {
            long startTime = System.currentTimeMillis();
            
            int result = CallWS.sendMT(sms.getUrl(), sms.getUser(), sms.getPass(), 
                    sms.getMsisdn(),sms.getContent(), sms.getShortCode(), 
                    sms.getAlias(), sms.getSmsType(), 1);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info(getThreadName() + ": send result " + result + ", " + sms + ", duartion (ms): " + duration);
//            if (result == 0) {
                MTSMS mtsms = new MTSMS(sms.getShortCode(), sms.getMsisdn(),
                        sms.getContent(), sms.getShortCode(),
                        MTSMS.SMS_TYPE_TEXT, sms.getUser());
//                mtsms.setSubServiceName(sms.getSubServiceName());
                mtsms.setTransId(sms.getId());
                mtsms.setResult(String.valueOf(result));
//                mtsms.setContentId(sms.getContentId());
                MTQueue.getInstance().enqueue(mtsms);
//            }
        }
}


    
    

