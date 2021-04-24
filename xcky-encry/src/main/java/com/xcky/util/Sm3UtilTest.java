package com.xcky.util;

import com.xcky.util.encry.Sm3Util;

/**
 * 国密算法-消息摘要工具类测试
 * @author lbchen
 */
public class Sm3UtilTest {
    
    public static void main(String[] args) {
        Sm3Util sm3Util = new Sm3Util();
        byte[] src = "ho".getBytes();
        byte[] out = sm3Util.sign(src);
        System.out.println("数组长度 = " +Sm3Util.BYTE_LENGTH);
        System.out.println(Util.byteToHex(out));
        System.out.println(sm3Util.verify(src, out));
    }

}
