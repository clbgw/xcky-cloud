package com.xcky.util;

import com.alibaba.fastjson.JSONObject;
import com.xcky.util.encry.Sm2Util;
import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint;

/**
 * 国密算法工具类测试
 *
 * @author lbchen
 */
public class Sm2UtilTest {
    /**
     * 源消息串
     */
    private static String dataStr = "当你问的";
    
    public static void main(String[] args) {
        Sm2Util sm2 = new Sm2Util();
        Sm2Util.Sm2KeyPair keyPair = sm2.generateKeyPair();
        if (null == keyPair) {
            System.out.println("生成私钥对失败");
            return;
        }
        ECPoint publicKey =keyPair.getPublicKey();
        BigInteger privateKey = keyPair.getPrivateKey();

        System.out.println("公钥: " + JSONObject.toJSONString(publicKey));
        byte[] data = sm2.encrypt(dataStr, publicKey);
        System.out.println("加密后的内容(十六进制):" + Util.byteToHex(data));
        System.out.println("私钥: " + privateKey);
        String oldData = sm2.decrypt(data, privateKey);
        System.out.println("原文:" + oldData);

        System.out.println("==============");
        staticKeyPair("women doush{}/,[]");
    }
    
    private static void staticKeyPair(String src) {
        Sm2Util sm2 = new Sm2Util();
        String publicKeyStr =
                "048C90851AABA6BA9C71A4C0B4716B6FB43FC3E3DC90471634D0D9D4321B2ABE50CD28AD4111E27C22BE3A134EFA2A5D60C93134D57119FFA59293E41D1C35637F";
        BigInteger privateKey = new BigInteger("90251747268231698876545153490233702969131092779216126789018733816279569617385");
        ECPoint publicKey = sm2.getPublicKey(Util.hexToByte(publicKeyStr));
        System.out.println("公钥: " + JSONObject.toJSONString(publicKey));
        byte[] data = sm2.encrypt(src, publicKey);
        System.out.println("加密后的内容(十六进制):" + Util.byteToHex(data));
        System.out.println("私钥: " + privateKey);
        String oldData = sm2.decrypt(data, privateKey);
        System.out.println("原文:" + oldData);
    }
}
