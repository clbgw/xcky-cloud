package com.xcky.util.encry;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import lombok.extern.slf4j.Slf4j;

/**
 * 加密工具类
 *
 * @author ccb.CCDA.lbchen
 */
@Slf4j
public class EncryUtil {
    /**
     * 加密算法RSA
     */
    private static final String RSA_ALGORITHM = "RSA";
    /**
     * 默认字符集
     */
    private static final String CHARSET = "UTF-8";
    /**
     * 数字2
     */
    private static final int TWO = 2;
    /**
     * 消息摘要算法MD5
     */
    private static final String MD5 = "MD5";
    
    /**
     * 创建公钥私钥
     *
     * @param keySize 密钥位数1024/2048
     * @return 创建密钥对
     */
    public static void createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            // 初始化KeyPairGenerator对象,密钥长度
            kpg.initialize(keySize);
            // 生成密匙对
            KeyPair keyPair = kpg.generateKeyPair();
            // 得到公钥
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyStr = byteToHex(publicKey.getEncoded());
            // 得到私钥
            PrivateKey privateKey = keyPair.getPrivate();
            String privateKeyStr = byteToHex(privateKey.getEncoded());

            log.info("publicKey:\n" +publicKeyStr+"\n");
            log.info("privateKey:\n" + privateKeyStr +"\n");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
    }
    
    /**
     * 生成md5消息摘要
     *
     * @param str 源字符串
     * @return 消息摘要16进制字符串
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] array = md.digest(str.getBytes(CHARSET));
            return byteToHex(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 公钥加密
     *
     * @param data         需要加密的源数据
     * @param publicKeyStr 公钥字符串(16进制)
     * @return 公钥加密后的数据(16进制)
     */
    public static String encry(String data, String publicKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            RSAPublicKey publicKey = getPublicKey(publicKeyStr);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return byteToHex(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    /**
     * 私钥解密
     *
     * @param data          需要解密的数据
     * @param privateKeyStr 私钥字符串(16进制)
     * @return 私钥解密后的数据
     */
    public static String decry(String data, String privateKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            RSAPrivateKey privateKey = getPrivateKey(privateKeyStr);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, hexToByte(data),
                    privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    /**
     * 分段处理
     *
     * @param cipher  加密算法
     * @param opmode  操作模式
     * @param datas   源数据
     * @param keySize 密钥位数
     * @return RSA分组数组
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) throws IOException {
        int maxBlock;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        out.close();
        return resultDatas;
    }
    
    /**
     * 获取私钥pkcs8
     *
     * @param privateKey 密钥字符串（16进制）
     * @return pkcs8私钥对象
     * @throws NoSuchAlgorithmException 没有此RSA算法
     * @throws InvalidKeySpecException  错误的密钥字符串
     */
    private static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyArr = hexToByte(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyArr);
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }
    
    /**
     * 密钥字符串（经过base64编码）
     *
     * @param publicKey 公钥字符串(16进制)
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 没有此RSA算法
     * @throws InvalidKeySpecException  错误的密钥字符串
     */
    private static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyArr = hexToByte(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyArr);
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }
    
    /**
     * 字节数组转换为十六进制字符串
     *
     * @param arr byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    private static String byteToHex(byte[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("byte array is null! ");
        }
        StringBuilder hs = new StringBuilder();
        for (byte b : arr) {
            String stmp = Integer.toHexString(b & 0xff);
            if (stmp.length() == 1) {
                hs.append(0);
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }
    
    /**
     * 十六进制串转化为byte数组
     *
     * @param hex 16进制的字符
     * @return 二进制字节数组
     */
    private static byte[] hexToByte(String hex) {
        if (hex.length() % TWO != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = Integer.valueOf(byteint).byteValue();
        }
        return b;
    }
    
}
