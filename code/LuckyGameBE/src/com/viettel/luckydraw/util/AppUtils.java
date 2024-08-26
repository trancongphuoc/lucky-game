/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.util;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.json.simple.parser.ParseException;
import org.springframework.util.StringUtils;
/**
 *
 * @author MrCAP
 */
public class AppUtils {
    
    
    public static final int PHONE_LENGTH=10 ;
    
     public static final Logger logger = Logger.getLogger(AppUtils.class);
     public static String getElementXml(String xml, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        int startPos = xml.indexOf(startTag);
        int endPos = xml.indexOf(endTag);
        if (startPos != -1 && endPos != -1) {
            int startLen = startTag.length();
            startPos = startPos + startLen;
            return xml.substring(startPos, endPos);
        } else {
            return null;
        }
    }
     public static String convertToMsisdn(String input){
         if(input.startsWith("0")){
             input = Config.getInstance().getOtpPrefix() + input.substring(1);
         }else if(!input.startsWith(Config.getInstance().getOtpPrefix())){
             input = Config.getInstance().getOtpPrefix() + input;
         }
         return input;
     }
     
     public static String convertToIsdn(String input){
         if(input.startsWith("0")){
             input = input.substring(1);
         }else if(input.startsWith(Config.getInstance().getOtpPrefix())){
             input = input.substring(Config.getInstance().getOtpPrefix().length());
         }
         return input;
     }
     
     public static boolean isLaptopProductCode(String input){
         boolean isCheck = false;
         String listLaptopProductCode = Config.getInstance().getLaptopProductCode();
            

        String[] tmp = listLaptopProductCode.split(",");
        if (tmp.length > 0) {
            for (String productCode : tmp) {
                if (productCode.trim().equalsIgnoreCase(input)) {
                    isCheck = true;
                    break;
                }
            }
        }
         return  isCheck;
     }
     
     public static String iMD5(byte[] data)  {
        MD5Digest md5digest = new MD5Digest();
        md5digest.reset();
        md5digest.update(data, 0, data.length);
        int length = md5digest.getDigestSize();
        byte[] md5 = new byte[length];
        md5digest.doFinal(md5, 0);
        return byteArrayToHexString(md5);
    }

    private static String byteArrayToHexString(byte byteValues[]) {
        byte singleChar;
        if (byteValues == null || byteValues.length <= 0) {
            return null;
        }

        String entries[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f"};

        StringBuffer out = new StringBuffer(byteValues.length * 2);

        for (int i = 0; i < byteValues.length; i++) {
            singleChar = (byte) (byteValues[i] & 0xF0);
            singleChar = (byte) (singleChar >>> 4);
            // shift the bits down
            singleChar = (byte) (singleChar & 0x0F);
            out.append(entries[(int) singleChar]);
            singleChar = (byte) (byteValues[i] & 0x0F);
            out.append(entries[(int) singleChar]);
        }
        String rslt = new String(out);
        return rslt;
    }

     public static String parseJson(String response, String tag) {
        String res = null;
        try {
            if (response != null && !"".equals(response)) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(response);
                JSONObject objson = (JSONObject) obj;
                res = (String) objson.get(tag);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return res;
    }
     
     
      public static String parseJson(String response, String tagParent, String tagChild) {
        String res = null;
        try {
            if (response != null && !"".equals(response)) {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(response);
                logger.info("obj " + obj);
                JSONObject objson = (JSONObject) obj.get(tagParent);
                               
                res = (String) objson.get(tagChild);
             //   System.out.println("");
            }
        } catch (ParseException e) {
            
            logger.error(e.getMessage(), e);
        }
        return res;
    }
     
     
     
       public static String standardizeIsdn(String isdn) {
        if(!StringUtils.isEmpty(isdn) && isdn.startsWith("+856")){
            isdn = isdn.substring(4);
        }
        
        else if(!StringUtils.isEmpty(isdn) && isdn.startsWith("856")){
            isdn = isdn.replaceFirst("856", "");
        }
        else if(!StringUtils.isEmpty(isdn) && isdn.startsWith("00856")){
            isdn = isdn.replaceFirst("00856", "");
        }
        
        if (!StringUtils.isEmpty(isdn) && isdn.length() > PHONE_LENGTH) {
            isdn = isdn.substring(isdn.length() - PHONE_LENGTH);

        }
        if (isdn.startsWith("0")) {
            isdn = isdn.replaceFirst("0", "");
        }
        return isdn;

    }
}
