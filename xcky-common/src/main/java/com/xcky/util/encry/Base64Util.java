package com.xcky.util.encry;

import java.util.Base64;

/**
 * @author lbchen
 */
public class Base64Util {
    /**
     * Base64编码
     *
     * @param src 源字节数组
     * @return 加密后的字符串
     */
    public static String encode(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }
    
    /**
     * Base64解码
     *
     * @param str 加密后的字符串
     * @return 未加密的字节数组
     */
    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str);
    }
}
