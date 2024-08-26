/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.openssl.PEMWriter;

/**
 *
 * @author NhatNT
 */
public class utils {

    public static String getSysdate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        return convertDateToString(calendar.getTime());
    }

    public static String convertDateToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {Constant.LogNo(e);
            throw e;
        }
    }

    public static Date convertStringToDate(String date) throws Exception {
        String pattern = "yyyy-MM-dd";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToTime(String date, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
        }
        return null;
    }

    public static Date convertStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static boolean isIntNumber(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String ConvertLang(String sCharacter, String lang) {
        if (lang == "e") {
            String sTemplate = "ĂẮẰẲẴẶăắằẳẵặÂẤẦẨẪẬâấầẩẫậÁÀẢÃẠáàảãạÔỐỒỔỖỘôốồổỗộƠỚỜỞỠỢơớờởỡợÓÒỎÕỌóòỏõọĐđÊẾỀỂỄỆêếềểễệÉÈẺẼẸéèẻẽẹƯỨỪỬỮỰưứừửữựÚÙỦŨỤúùủũụÍÌỈĨỊíìỉĩịÝỲỶỸỴýỳỷỹỵ";
            String sReplate = "AAAAAAaaaaaaAAAAAAaaaaaaAAAAAaaaaaOOOOOOooooooOOOOOOooooooOOOOOoooooDdEEEEEEeeeeeeEEEEEeeeeeUUUUUUuuuuuuUUUUUuuuuuIIIIIiiiiiYYYYYyyyyy";
            char[] arrChar = sTemplate.toCharArray();
            char[] arrReChar = sReplate.toCharArray();
            String sResult = "";//sCharacter;
            char digit;

            for (int ch = 0; ch < sCharacter.length(); ch++) {
                digit = sCharacter.substring(ch, ch + 1).charAt(0);
                for (int i = 0; i < arrChar.length; i++) {
                    if (digit == arrChar[i]) {
                        digit = arrReChar[i];
                    }
                }
                sResult += digit;
            }

            return sResult;
        } else {
            return sCharacter;
        }
    }

    public static String formatUrl(String str) {
        try {
            String temp = ConvertLang(str.toLowerCase(), "e").replace(" ", "_");
            String strSpecials = "[^\\w,\\.@-]";
            String output = temp.replaceAll(strSpecials, "_");
            return output;
        } catch (Exception ex) {Constant.LogNo(ex);
            return "Err";
        }
    }

    public static BigDecimal convertStringToBigDecimal(String s) {
        BigDecimal bigDecimal1 = new BigDecimal(s);
        return bigDecimal1;
    }

    public static BigDecimal convertIntToBigDecimal(int i) {
        BigDecimal bigDecimal1 = new BigDecimal(i);
        return bigDecimal1;
    }

    public static int convertStringToInt(String s) {
        if (isNumeric(s)) {
            return Integer.valueOf(s);
        } else {
            return -1;
        }
    }

    public static boolean isNumeric(String number) {
        boolean isValid = false;
        String expression = "^[0-9][0-9]*$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        utils.genKeyCP("lotus");
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        text = "MPS_MPS" + text;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static void genKeyCP(String service) throws NoSuchAlgorithmException, IOException {//gen key

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // Initialize and Generate
        kpg.initialize(4096, new SecureRandom());
        KeyPair pair = kpg.generateKeyPair();

        File destFileName = new File("");
        try {
            destFileName = new File(((destFileName.getAbsolutePath() + "/key/" + service).replace('\\', '/')).replace("/bin", ""));
            if (destFileName.exists() == false) {
                destFileName.mkdirs();
            } else {
            }
        } catch (Exception e) {Constant.LogNo(e);
        }
        //end tao thu muc

        PEMWriter privWriter = new PEMWriter(new FileWriter(destFileName + "/PrivateKeyCP.pem"));
        privWriter.writeObject(pair.getPrivate());

        privWriter.close();

        privWriter = new PEMWriter(new FileWriter(destFileName + "/PublicKeyCP.pem"));
        privWriter.writeObject(pair.getPublic());

        privWriter.close();
    }

    public static void genKeyVT(String service) throws NoSuchAlgorithmException, IOException {//gen key
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // Initialize and Generate
        kpg.initialize(4096, new SecureRandom());
        KeyPair pair = kpg.generateKeyPair();

        File destFileName = new File("");
        //tao thu muc chua key
        try {
            destFileName = new File(((destFileName.getAbsolutePath() + "/key/" + service).replace('\\', '/')).replace("/bin", ""));
            if (destFileName.exists() == false) {
                destFileName.mkdirs();
            } else {
            }
        } catch (Exception e) {Constant.LogNo(e);
        }
        //end tao thu muc

        PEMWriter privWriter = new PEMWriter(new FileWriter(destFileName + "/PrivateKeyVT.pem"));
        privWriter.writeObject(pair.getPrivate());

        privWriter.close();

        privWriter = new PEMWriter(new FileWriter(destFileName + "/PublicKeyVT.pem"));
        privWriter.writeObject(pair.getPublic());

        privWriter.close();
    }
}
