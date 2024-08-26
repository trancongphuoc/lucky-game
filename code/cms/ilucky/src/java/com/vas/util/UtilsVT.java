/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.util;

/**
 *
 * @author Trongtv
 */
import com.viettel.utils.Constant;
import java.io.*;
import java.util.*;

public class UtilsVT {
    /* member class not found */

    class ExecInfo {
    }

    public UtilsVT() {
    }

    public static String zerofill(int x, int desiredWidth) {
        StringBuffer buf = new StringBuffer();
        if (x < 0) {
            buf.append("-");
            desiredWidth--;
            x = -x;
        }
        for (; desiredWidth > 7; desiredWidth--) {
            buf.append("0");
        }

        switch (desiredWidth) {
            default:
                break;

            case 7: // '\007'
                if (x < 0xf4240) {
                    buf.append("0");
                }
            // fall through

            case 6: // '\006'
                if (x < 0x186a0) {
                    buf.append("0");
                }
            // fall through

            case 5: // '\005'
                if (x < 10000) {
                    buf.append("0");
                }
            // fall through

            case 4: // '\004'
                if (x < 1000) {
                    buf.append("0");
                }
            // fall through

            case 3: // '\003'
                if (x < 100) {
                    buf.append("0");
                }
            // fall through

            case 2: // '\002'
                if (x < 10) {
                    buf.append("0");
                }
                break;
        }
        buf.append(x);
        return buf.toString();
    }

    public static void printIndent(PrintWriter out, int indent) {
        out.print(indent(indent));
    }

    public static String indent(int indent) {
        switch (indent) {
            case 8: // '\b'
                return "        ";

            case 7: // '\007'
                return "       ";

            case 6: // '\006'
                return "      ";

            case 5: // '\005'
                return "     ";

            case 4: // '\004'
                return "    ";

            case 3: // '\003'
                return "   ";

            case 2: // '\002'
                return "  ";

            case 1: // '\001'
                return " ";
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buf.append(" ");
        }

        return buf.toString();
    }

    /**
     * @deprecated Method commaList is deprecated
     */
    public static String commaList(Object a[]) {
        return commaList(Arrays.asList(a).iterator());
    }

    /**
     * @deprecated Method commaList is deprecated
     */
    public static String commaList(Collection c) {
        return commaList(c.iterator());
    }

    /**
     * @deprecated Method commaList is deprecated
     */
    public static String commaList(Iterator i) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        printCommaList(pw, i);
        pw.close();
        return sw.toString();
    }

    public static void printCommaList(PrintWriter out, Iterator i) {
        boolean first = true;
        for (; i.hasNext(); out.print(i.next())) {
            if (first) {
                first = false;
            } else {
                out.print(", ");
            }
        }

    }

    public static boolean isWhitespace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String abbreviate(String s, int max) {
        if (max < 4) {
            throw new IllegalArgumentException("Minimum abbreviation is 3 chars");
        }
        if (s.length() <= max) {
            return s;
        } else {
            return s.substring(0, max - 3) + "...";
        }
    }

    public static String pad(String s, int length) {
        if (s.length() < length) {
            return s + indent(length - s.length());
        } else {
            return s.substring(0, length);
        }
    }

    public static String strdiff(String s1, String s2) {
        int at = strdiffat(s1, s2);
        if (at == -1) {
            return "";
        } else {
            return s2.substring(at);
        }
    }

    public static int strdiffat(String s1, String s2) {
        int i;
        for (i = 0; i < s1.length() && i < s2.length() && s1.charAt(i) == s2.charAt(i); i++);
        if (i < s2.length() || i < s1.length()) {
            return i;
        } else {
            return -1;
        }
    }

    public static String strdiffVerbose(String expected, String actual) {
        int at = strdiffat(actual, expected);
        if (at == -1) {
            return null;
        }
        int length = 60;
        int back = 20;
        int start = at - back;
        if (start < 3) {
            start = 0;
        }
        StringBuffer buf = new StringBuffer(length * 2 + 100);
        buf.append("strings differ at character ").append(at);
        buf.append("\n");
        buf.append("Expected: ");
        appendWithEllipses(buf, expected, start, length);
        buf.append("\n");
        buf.append("  Actual: ");
        appendWithEllipses(buf, actual, start, length);
        buf.append("\n");
        return buf.toString();
    }

    private static void appendWithEllipses(StringBuffer buf, String s, int start, int length) {
        if (start > 0) {
            buf.append("...");
        }
        buf.append(javaEscape(abbreviate(s.substring(start), length)));
    }

    /**
     * @deprecated Method count is deprecated
     */
    public static int count(String s, char ch) {
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ch) {
                c++;
            }
        }

        return c;
    }

    public static String replace(String source, String target, String replacement) {
        StringBuffer result = new StringBuffer(source.length());
        int i = 0;
        int j = 0;
        int len = source.length();
        do {
            if (i >= len) {
                break;
            }
            j = source.indexOf(target, i);
            if (j == -1) {
                result.append(source.substring(i, len));
                break;
            }
            result.append(source.substring(i, j));
            result.append(replacement);
            i = j + target.length();
        } while (true);
        return result.toString();
    }

    public static String rtrim(String orig) {
        int len = orig.length();
        int st = 0;
        int off = 0;
        for (char val[] = orig.toCharArray(); st < len && val[(off + len) - 1] <= ' '; len--);
        return st <= 0 && len >= orig.length() ? orig : orig.substring(st, len);
    }

    public static String ltrim(String orig) {
        int len = orig.length();
        int st = 0;
        int off = 0;
        for (char val[] = orig.toCharArray(); st < len && val[off + st] <= ' '; st++);
        return st <= 0 && len >= orig.length() ? orig : orig.substring(st, len);
    }

    public static int getMaxLength(Iterator i) {
        int max = 0;
        do {
            if (!i.hasNext()) {
                break;
            }
            String s = i.next().toString();
            int c = s.length();
            if (c > max) {
                max = c;
            }
        } while (true);
        return max;
    }

    public static String htmlescape(String s1) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < s1.length(); i++) {
            char ch = s1.charAt(i);
            String entity = (String) i2e.get(new Integer(ch));
            if (entity == null) {
                if (ch > '\200') {
                    buf.append("&#" + (int) ch + ";");
                } else {
                    buf.append(ch);
                }
            } else {
                buf.append("&" + entity + ";");
            }
        }

        return buf.toString();
    }

    public static String htmlunescape(String s1) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < s1.length(); i++) {
            char ch = s1.charAt(i);
            if (ch == '&') {
                int semi = s1.indexOf(';', i + 1);
                if (semi == -1) {
                    buf.append(ch);
                    continue;
                }
                String entity = s1.substring(i + 1, semi);
                Integer iso;
                if (entity.charAt(0) == '#') {
                    iso = new Integer(entity.substring(1));
                } else {
                    iso = (Integer) e2i.get(entity);
                }
                if (iso == null) {
                    buf.append("&" + entity + ";");
                } else {
                    buf.append((char) iso.intValue());
                }
                i = semi;
            } else {
                buf.append(ch);
            }
        }

        return buf.toString();
    }

    public static String jsEscape(String source) {
        try {
            StringWriter sw = new StringWriter();
            jsEscape(source, ((Writer) (sw)));
            sw.flush();
            return sw.toString();
        } catch (IOException ioe) {Constant.LogNo(ioe);
//            io//e.printStackTrace();
        }
        return null;
    }

    public static String javaEscape(String source) {
        try {
            StringWriter sw = new StringWriter();
            javaEscape(source, ((Writer) (sw)));
            sw.flush();
            return sw.toString();
        } catch (IOException ioe) {Constant.LogNo(ioe);
//            io//e.printStackTrace();
        }
        return null;
    }

    public static void jsEscape(String source, Writer out)
            throws IOException {
        stringEscape(source, out, true);
    }

    public static void javaEscape(String source, Writer out)
            throws IOException {
        stringEscape(source, out, false);
    }

    private static void stringEscape(String source, Writer out, boolean escapeSingleQuote)
            throws IOException {
        char chars[] = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            switch (ch) {
                case 8: // '\b'
                    out.write("\\b");
                    break;

                case 9: // '\t'
                    out.write("\\t");
                    break;

                case 10: // '\n'
                    out.write("\\n");
                    break;

                case 11: // '\013'
                    out.write("\\v");
                    break;

                case 12: // '\f'
                    out.write("\\f");
                    break;

                case 13: // '\r'
                    out.write("\\r");
                    break;

                case 34: // '"'
                    out.write("\\\"");
                    break;

                case 39: // '\''
                    if (escapeSingleQuote) {
                        out.write("\\'");
                    } else {
                        out.write("'");
                    }
                    break;

                case 92: // '\\'
                    out.write("\\\\");
                    break;

                default:
                    if (ch < ' ' || ch > '\177') {
                        out.write("\\u");
                        zeropad(out, Integer.toHexString(ch).toUpperCase(), 4);
                    } else {
                        out.write(ch);
                    }
                    break;
            }
        }

    }

    private static void zeropad(Writer out, String s, int width)
            throws IOException {
        for (; width > s.length(); width--) {
            out.write(48);
        }

        out.write(s);
    }

    public static String uncurlQuotes(String input) {
        if (input == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int code = ch;
            if (code == 210 || code == 211 || code == 147 || code == 148) {
                ch = '"';
            } else if (code == 212 || code == 213 || code == 145 || code == 146) {
                ch = '\'';
            }
            sb.append(ch);
        }

        return sb.toString();
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String lowerize(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    public static String pluralize(String s) {
        if (s.endsWith("y")) {
            return s.substring(0, s.length() - 1) + "ies";
        }
        if (s.endsWith("s")) {
            return s + "es";
        } else {
            return s + "s";
        }
    }

    public static boolean ok(String s) {
//        return s != null && !s.isEmpty();
        return s != null && !s.isEmpty();
    }

    public static String toUnderscore(String s) {
        StringBuffer buf = new StringBuffer();
        char ch[] = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (Character.isUpperCase(ch[i])) {
                buf.append('_');
                buf.append(Character.toLowerCase(ch[i]));
            } else {
                buf.append(ch[i]);
            }
        }

        return buf.toString();
    }

    /**
     * @deprecated Method stripWhitespace is deprecated
     */
    public static String stripWhitespace(String s) {
        StringBuffer buf = new StringBuffer();
        char ch[] = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (!Character.isWhitespace(ch[i])) {
                buf.append(ch[i]);
            }
        }

        return buf.toString();
    }

    public static String getStackTrace(Throwable t) {
        StringWriter s = new StringWriter();
        PrintWriter p = new PrintWriter(s);
        t.printStackTrace(p);
        p.close();
        return s.toString();
    }

    public static void sleep(long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException ie) {
        }
    }

    static Object entities[][] = {
        {
            "quot", new Integer(34)
        }, {
            "amp", new Integer(38)
        }, {
            "lt", new Integer(60)
        }, {
            "gt", new Integer(62)
        }, {
            "nbsp", new Integer(160)
        }, {
            "copy", new Integer(169)
        }, {
            "reg", new Integer(174)
        }, {
            "Agrave", new Integer(192)
        }, {
            "Aacute", new Integer(193)
        }, {
            "Acirc", new Integer(194)
        }, {
            "Atilde", new Integer(195)
        }, {
            "Auml", new Integer(196)
        }, {
            "Aring", new Integer(197)
        }, {
            "AElig", new Integer(198)
        }, {
            "Ccedil", new Integer(199)
        }, {
            "Egrave", new Integer(200)
        }, {
            "Eacute", new Integer(201)
        }, {
            "Ecirc", new Integer(202)
        }, {
            "Euml", new Integer(203)
        }, {
            "Igrave", new Integer(204)
        }, {
            "Iacute", new Integer(205)
        }, {
            "Icirc", new Integer(206)
        }, {
            "Iuml", new Integer(207)
        }, {
            "ETH", new Integer(208)
        }, {
            "Ntilde", new Integer(209)
        }, {
            "Ograve", new Integer(210)
        }, {
            "Oacute", new Integer(211)
        }, {
            "Ocirc", new Integer(212)
        }, {
            "Otilde", new Integer(213)
        }, {
            "Ouml", new Integer(214)
        }, {
            "Oslash", new Integer(216)
        }, {
            "Ugrave", new Integer(217)
        }, {
            "Uacute", new Integer(218)
        }, {
            "Ucirc", new Integer(219)
        }, {
            "Uuml", new Integer(220)
        }, {
            "Yacute", new Integer(221)
        }, {
            "THORN", new Integer(222)
        }, {
            "szlig", new Integer(223)
        }, {
            "agrave", new Integer(224)
        }, {
            "aacute", new Integer(225)
        }, {
            "acirc", new Integer(226)
        }, {
            "atilde", new Integer(227)
        }, {
            "auml", new Integer(228)
        }, {
            "aring", new Integer(229)
        }, {
            "aelig", new Integer(230)
        }, {
            "ccedil", new Integer(231)
        }, {
            "egrave", new Integer(232)
        }, {
            "eacute", new Integer(233)
        }, {
            "ecirc", new Integer(234)
        }, {
            "euml", new Integer(235)
        }, {
            "igrave", new Integer(236)
        }, {
            "iacute", new Integer(237)
        }, {
            "icirc", new Integer(238)
        }, {
            "iuml", new Integer(239)
        }, {
            "igrave", new Integer(236)
        }, {
            "iacute", new Integer(237)
        }, {
            "icirc", new Integer(238)
        }, {
            "iuml", new Integer(239)
        }, {
            "eth", new Integer(240)
        }, {
            "ntilde", new Integer(241)
        }, {
            "ograve", new Integer(242)
        }, {
            "oacute", new Integer(243)
        }, {
            "ocirc", new Integer(244)
        }, {
            "otilde", new Integer(245)
        }, {
            "ouml", new Integer(246)
        }, {
            "oslash", new Integer(248)
        }, {
            "ugrave", new Integer(249)
        }, {
            "uacute", new Integer(250)
        }, {
            "ucirc", new Integer(251)
        }, {
            "uuml", new Integer(252)
        }, {
            "yacute", new Integer(253)
        }, {
            "thorn", new Integer(254)
        }, {
            "yuml", new Integer(255)
        }, {
            "euro", new Integer(8364)
        }
    };
    static Map e2i;
    static Map i2e;

    static {
        e2i = new HashMap();
        i2e = new HashMap();
        for (int i = 0; i < entities.length; i++) {
            e2i.put(entities[i][0], entities[i][1]);
            i2e.put(entities[i][1], entities[i][0]);
        }

    }
}
