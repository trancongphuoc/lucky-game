/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.captcha;

/**
 *
 * @author QuyetTC
 */
import java.util.Random;

public class StringUtil
{

    public StringUtil()
    {
    }

    public static String getRandString(int ilen)
    {
        Random rand = new Random();
        String charset[] = {
            "2", "3", "4", "5", "6", "7", "8", "9","a","b",
            "c", "d", "e","f", "g", "h", "j", "k","m",
            "n", "p", "q", "r", "s","t","u", "x", "y", "z"
        };
        StringBuffer sb = new StringBuffer();
        for(int n = 0; n < ilen; n++)
            sb = sb.append(charset[rand.nextInt(29)]);// bỏ bớt 1 số chuỗi ký tự nhập nhằng

        mstrRand = sb.toString();
        return mstrRand;
    }

    public static String replaceString(String text, String pattern[], String replace[])
    {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < pattern.length; i++)
        {
            int startIndex = 0;
            result.setLength(0);
            int foundIndex;
            while((foundIndex = text.indexOf(pattern[i], startIndex)) >= 0)
            {
                result.append(text.substring(startIndex, foundIndex));
                result.append(replace[i]);
                startIndex = foundIndex + pattern[i].length();
            }
            result.append(text.substring(startIndex));
            text = result.toString();
        }

        return text;
    }

    public static String replaceString(String text, String pattern, String replace)
    {
        int startIndex = 0;
        StringBuffer result = new StringBuffer();
        int foundIndex;
        while((foundIndex = text.indexOf(pattern, startIndex)) >= 0)
        {
            result.append(text.substring(startIndex, foundIndex));
            result.append(replace);
            startIndex = foundIndex + pattern.length();
        }
        result.append(text.substring(startIndex));
        return result.toString();
    }

    public static String filter(String input)
    {
        StringBuffer filtered = new StringBuffer(input.length());
        for(int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if(c != '\'' && c != '`')
                filtered.append(c);
        }

        return filtered.toString();
    }

    public static String mstrRand = "";

}

