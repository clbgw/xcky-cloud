package com.xcky.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author lbchen
 */
public class StringUtil {
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SYMBOLS_NUM = "0123456789";
    private static final Random RANDOM = new SecureRandom();
    /**
     * 获取随机字符串
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr(int size) {
        char[] nonceChars = new char[size];
        int symbolsSize = SYMBOLS.length();
        for (int index = 0; index < size; index++) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(symbolsSize));
        }
        return new String(nonceChars);
    }
    
    /**
     * 获取随机数字
     *
     * @return String 随机数字
     */
    public static String generateNonceNum(String prefix,int size) {
        char[] nonceChars = new char[size];
        int symbolsSize = SYMBOLS_NUM.length();
        for (int index = 0; index < size; index++) {
            nonceChars[index] = SYMBOLS_NUM.charAt(RANDOM.nextInt(symbolsSize));
        }
        return prefix + new String(nonceChars);
    }
}
