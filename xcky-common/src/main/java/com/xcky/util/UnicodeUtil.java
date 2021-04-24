package com.xcky.util;

/**
 * Unicode工具类
 *
 * @author lbchen
 */
public class UnicodeUtil {
    
    /**
     * Unicode编码
     *
     * @param chinese 中文
     * @return 编码后的字符串
     */
    public static String unicodeEncode(String chinese) {
        char[] utfBytes = chinese.toCharArray();
        String unicodeBytes = "";
        for (char c : utfBytes) {
            String hexB = Integer.toHexString(c);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
    
    /**
     * Unicode解码
     *
     * @param dataStr 编码后的字符串
     * @return 中文
     */
    public static String unicodeDecode(final String dataStr) {
        int start = 0;
        int end;
        final StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr;
            if (end == -1) {
                charStr = dataStr.substring(start + 2);
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(Character.valueOf(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
}
