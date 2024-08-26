/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.util;

/**
 *
 * @author hanhnv62
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class CharsetDetector {
    public static final String [] CHARSETS = new String[] {"ASCII", "UTF-16", "UTF-8"};
    
    public static Charset detectCharset(String str) {
        Charset charset = null;

        for (String charsetName : CHARSETS) {
            charset = detectCharset(str.getBytes(), Charset.forName(charsetName));
            if (charset != null) {
                break;
            }
        }

        return charset;
    }
    
    public static String detectCharsetStr(String str) {
        String result = null;

        for (String charsetName : CHARSETS) {
            Charset charset = detectCharset(str.getBytes(), Charset.forName(charsetName));
            if (charset != null) {
                result = charsetName;
                break;
            }
        }

        return result;
    }
    
    public static String detectCharsetStr(byte[] b) {
        String result = null;

        for (String charsetName : CHARSETS) {
            Charset charset = detectCharset(b, Charset.forName(charsetName));
            if (charset != null) {
                result = charsetName;
                break;
            }
        }

        return result;
    }

    private static Charset detectCharset(byte[] b, Charset charset) {
        try {
            BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(b));

            CharsetDecoder decoder = charset.newDecoder();
            decoder.reset();

            byte[] buffer = new byte[512];
            boolean identified = false;
            while ((input.read(buffer) != -1) && (!identified)) {
                identified = identify(buffer, decoder);
            }

            input.close();

            if (identified) {
                return charset;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    private static boolean identify(byte[] bytes, CharsetDecoder decoder) {
        try {
            decoder.decode(ByteBuffer.wrap(bytes));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }
    
    public static boolean isUFT16(byte[] b) {
        return (b[0] == (byte)0 || b[0] == (byte)1);
    }
}

