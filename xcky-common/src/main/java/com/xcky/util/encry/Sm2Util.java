package com.xcky.util.encry;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.ShortenedDigest;
import org.bouncycastle.crypto.generators.KDF1BytesGenerator;
import org.bouncycastle.crypto.params.ISO18033KDFParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.stereotype.Component;

/**
 * 国密2算法【公钥非对称加密】工具类
 *
 * <b>说 明<b/>:SM2的非对称加解密工具类，椭圆曲线方程为：y^2=x^3+ax+b 使用Fp-256
 *
 * @author lbchen
 */
@Component
public class Sm2Util {
    /**
     * 基点G, g=(xg,yg),其介记为n
     */
    private static BigInteger N = new BigInteger(
            "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "7203DF6B" + "21C6052B" + "53BBF409" + "39D54123", 16);
    /**
     * 素数p
     */
    private static BigInteger P = new BigInteger(
            "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFF", 16);
    /**
     * 系数a
     */
    private static BigInteger A = new BigInteger(
            "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFC", 16);
    /**
     * 系数b
     */
    private static BigInteger B = new BigInteger(
            "28E9FA9E" + "9D9F5E34" + "4D5A9E4B" + "CF6509A7" + "F39789F5" + "15AB8F92" + "DDBCBD41" + "4D940E93", 16);
    /**
     * 坐标x
     */
    private static BigInteger XG = new BigInteger(
            "32C4AE2C" + "1F198119" + "5F990446" + "6A39C994" + "8FE30BBF" + "F2660BE1" + "715A4589" + "334C74C7", 16);
    /**
     * 坐标y
     */
    private static BigInteger YG = new BigInteger(
            "BC3736A2" + "F4F6779C" + "59BDCEE3" + "6B692153" + "D0A9877C" + "C62A4740" + "02DF32E5" + "2139F0A0", 16);

    private static final SecureRandom RANDOM = new SecureRandom();
    private final ECCurve.Fp curve;
    private final ECPoint g;

    public Sm2Util() {
        curve = new ECCurve.Fp(P, A, B);
        g = curve.createPoint(XG, YG,true);
    }
    
    /**
     * 根据公钥加密字节数组获取公钥对象
     *
     * @param publicKeyEncodedArr 公钥加密字节数组
     * @return 公钥对象
     */
    public ECPoint getPublicKey(byte[] publicKeyEncodedArr) {
        return this.curve.decodePoint(publicKeyEncodedArr);
    }

    /**
     * 获取随机的大整数
     *
     * @param max 最大的大整数
     * @return 随机的大整数
     */
    private BigInteger random(BigInteger max) {
        BigInteger r = new BigInteger(256, RANDOM);
        while (r.compareTo(max) >= 0) {
            r = new BigInteger(128, RANDOM);
        }
        return r;
    }
    
    /**
     * 判断数组中是否都为0
     *
     * @param buffer 字节数组
     * @return 如果数组中全是0则返回true, 否则为false
     */
    private boolean allZero(byte[] buffer) {
        for (byte b : buffer) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 加密
     *
     * @param input     待加密消息M
     * @param publicKey 公钥
     * @return byte[] 加密后的字节数组
     */
    public byte[] encrypt(String input, ECPoint publicKey) {
        byte[] inputBuffer = input.getBytes();
        
        /* 1 产生随机数k，k属于[1, n-1] */
        BigInteger k = random(N);
        
        /* 2 计算椭圆曲线点C1 = [k]g = (x1, y1) */
        ECPoint c1 = g.multiply(k);
        byte[] c1Buffer = c1.getEncoded();
        // 4.计算 [k]PB = (x2, y2)
        ECPoint kpb = publicKey.multiply(k);
        
        // 5.计算 t = KDF(x2||y2, klen)
        byte[] kpbBytes = kpb.getEncoded();
        DerivationFunction kdf = new KDF1BytesGenerator(new ShortenedDigest(new SHA256Digest(), 20));
        byte[] t = new byte[inputBuffer.length];
        kdf.init(new ISO18033KDFParameters(kpbBytes));
        kdf.generateBytes(t, 0, t.length);
        
        if (allZero(t)) {
            System.err.println("all zero");
        }
        
        /* 6 计算C2=M^t */
        byte[] c2 = new byte[inputBuffer.length];
        for (int i = 0; i < inputBuffer.length; i++) {
            c2[i] = (byte) (inputBuffer[i] ^ t[i]);
        }
        
        /* 7 计算C3 = Hash(x2 || M || y2) */
        byte[] c3 = calculateHash(kpb.getX().toBigInteger(), inputBuffer, kpb.getY().toBigInteger());
        
        /* 8 输出密文 C=C1 || C2 || C3 */
        byte[] encryptResult = new byte[c1Buffer.length + c2.length + c3.length];
        System.arraycopy(c1Buffer, 0, encryptResult, 0, c1Buffer.length);
        System.arraycopy(c2, 0, encryptResult, c1Buffer.length, c2.length);
        System.arraycopy(c3, 0, encryptResult, c1Buffer.length + c2.length, c3.length);
        return encryptResult;
    }
    
    public String decrypt(byte[] encryptData, BigInteger privateKey) {
        byte[] c1Byte = new byte[65];
        System.arraycopy(encryptData, 0, c1Byte, 0, c1Byte.length);
        ECPoint c1 = curve.decodePoint(c1Byte);
        /* 计算[dB]C1 = (x2, y2) */
        ECPoint dBc1 = c1.multiply(privateKey);
        /* 计算t = KDF(x2 || y2, klen) */
        byte[] dBc1Bytes = dBc1.getEncoded();
        DerivationFunction kdf = new KDF1BytesGenerator(new ShortenedDigest(new SHA256Digest(), 20));
        int klen = encryptData.length - 65 - 20;
        byte[] t = new byte[klen];
        kdf.init(new ISO18033KDFParameters(dBc1Bytes));
        kdf.generateBytes(t, 0, t.length);
        if (allZero(t)) {
            System.err.println("all zero");
        }
        
        /* 5 计算M'=C2^t */
        byte[] m = new byte[klen];
        for (int i = 0; i < m.length; i++) {
            m[i] = (byte) (encryptData[c1Byte.length + i] ^ t[i]);
        }
        /* 6 计算 u = Hash(x2 || M' || y2) 判断 u == C3是否成立 */
        byte[] c3 = new byte[20];
        System.arraycopy(encryptData, encryptData.length - 20, c3, 0, 20);
        byte[] u = calculateHash(dBc1.getX().toBigInteger(), m, dBc1.getY().toBigInteger());
        if (Arrays.equals(u, c3)) {
            return new String(m);
        } else {
            return "";
        }
    }
    
    private byte[] calculateHash(BigInteger x2, byte[] m, BigInteger y2) {
        ShortenedDigest digest = new ShortenedDigest(new SHA256Digest(), 20);
        byte[] buf = x2.toByteArray();
        digest.update(buf, 0, buf.length);
        digest.update(m, 0, m.length);
        buf = y2.toByteArray();
        digest.update(buf, 0, buf.length);
        
        buf = new byte[20];
        digest.doFinal(buf, 0);
        return buf;
    }
    
    private boolean between(BigInteger param, BigInteger min, BigInteger max) {
        return param.compareTo(min) >= 0 && param.compareTo(max) < 0;
    }
    
    /**
     * 公钥校验
     *
     * @param publicKey 公钥
     * @return boolean true或false
     */
    private boolean checkPublicKey(ECPoint publicKey) {
        if (!publicKey.isInfinity()) {
            BigInteger x = publicKey.getX().toBigInteger();
            BigInteger y = publicKey.getY().toBigInteger();
            if (between(x, BigInteger.ZERO, P) && between(y, BigInteger.ZERO, P)) {
                BigInteger xResult = x.pow(3).add(A.multiply(x)).add(B).mod(P);
                BigInteger yResult = y.pow(2).mod(P);
                return yResult.equals(xResult) && publicKey.multiply(N).isInfinity();
            }
        }
        return false;
    }
    
    /**
     * 获得公私钥对
     *
     * @return 公钥和私钥的密钥对
     */
    public Sm2KeyPair generateKeyPair() {
        BigInteger d = random(N.subtract(BigInteger.ONE));
        Sm2KeyPair keyPair = new Sm2KeyPair(g.multiply(d), d);
        if (checkPublicKey(keyPair.getPublicKey())) {
            return keyPair;
        } else {
            return null;
        }
    }
    
    /**
     * SM2算法密钥对
     *
     * @author lbchen
     */
    public class Sm2KeyPair {
        /**
         * 公钥
         */
        private final ECPoint publicKey;
        /**
         * 私钥
         */
        private final BigInteger privateKey;
        
        /**
         * 构造密钥对
         *
         * @param publicKey  公钥
         * @param privateKey 私钥
         */
        Sm2KeyPair(ECPoint publicKey, BigInteger privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
        
        /**
         * 获取公钥
         *
         * @return 公钥对象
         */
        public ECPoint getPublicKey() {
            return publicKey;
        }
        
        /**
         * 获取私钥
         *
         * @return 私钥对象
         */
        public BigInteger getPrivateKey() {
            return privateKey;
        }
    }
    
}