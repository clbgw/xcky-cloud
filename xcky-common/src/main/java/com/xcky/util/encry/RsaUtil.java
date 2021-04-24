package com.xcky.util.encry;

import com.xcky.enums.RsaSignatureAlgorithmEnum;
import com.xcky.util.Constants;
import com.xcky.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密工具类
 *
 * @author lbchen
 */
@Slf4j
public class RsaUtil {
    /**
     * 加密算法RSA
     */
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * 默认签名算法
     */
    public static final RsaSignatureAlgorithmEnum DEFAULT_SIGNATURE_ALGORITHM = RsaSignatureAlgorithmEnum.SHA1WithRSA;
    
    /**
     * 创建公钥私钥
     *
     * @param keySize 1024 2048
     * @return 创建密钥对
     */
    public static Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
        
        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        // 得到私钥
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        
        Map<String, String> keyPairMap = new HashMap<>(8);
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKeyOfPKCS8", privateKeyStr);
        keyPairMap.put("privateKeyOfPKCS1", getPrivateKeyOfPkcs1(privateKeyStr));
        return keyPairMap;
    }
    
    /**
     * 密钥字符串（经过base64编码）
     *
     * @param publicKey 公钥字符串(Base64加密)
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 非法加密算法异常
     * @throws InvalidKeySpecException  非法的公钥异常
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyArr = Base64.decodeBase64(publicKey);
        return getPublicKey(publicKeyArr);
    }
    
    /**
     * 获取公钥
     *
     * @param publicKeyArr 公钥字节数组
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 非法加密算法异常
     * @throws InvalidKeySpecException  非法的公钥异常
     */
    public static RSAPublicKey getPublicKey(byte[] publicKeyArr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyArr);
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }
    
    /**
     * 获取私钥pkcs8
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @return pkcs8私钥对象
     * @throws NoSuchAlgorithmException 没有此RSA算法
     * @throws InvalidKeySpecException 错误的密钥字符串
     */
    public static RSAPrivateKey getPrivateKey(String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyArr = Base64.decodeBase64(privateKey);
        return getPrivateKey(privateKeyArr);
    }
    
    /**
     * 获取私钥pkcs8
     * @param privateKeyArr 私钥二进制数组
     * @return pkcs8私钥对象
     * @throws NoSuchAlgorithmException 没有此RSA算法
     * @throws InvalidKeySpecException 错误的密钥字符串
     */
    public static RSAPrivateKey getPrivateKey(byte[] privateKeyArr)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyArr);
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }
    
    public static String getPrivateKeyOfPkcs1(String privateKey) {
        byte[] privateKeyArr = Base64.decodeBase64(privateKey);
        return getPrivateKeyOfPkcs1(privateKeyArr);
    }
    
    public static String getPrivateKeyOfPkcs1(byte[] privateKeyArr) {
        try {
            PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privateKeyArr);
            byte[] privateKeyPkcs1 = pkInfo.getEncoded();
            return pkcs1ToPem(privateKeyPkcs1, false);
        } catch (Exception e) {
            log.error("PKCS8ToPKCS1 error", e);
        }
        return null;
    }
    
    public static String pkcs1ToPem(byte[] pcks1KeyArr, boolean isPublic) throws Exception {
        String type;
        if (isPublic) {
            type = "RSA PUBLIC KEY";
        } else {
            type = "RSA PRIVATE KEY";
        }
        PemObject pemObject = new PemObject(type, pcks1KeyArr);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        return stringWriter.toString();
    }
    
    /**
     * 公钥加密
     *
     * @param data      性需要加密的源数据
     * @param publicKey 公钥对象
     * @return 公钥加密后的数据
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(Constants.CHARSET),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    /**
     * 私钥解密
     *
     * @param data       源数据
     * @param privateKey 私钥对象
     * @return 私钥解密后的数据
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    privateKey.getModulus().bitLength()), Constants.CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    /**
     * 私钥加密
     *
     * @param data 源数据
     * @param privateKey 私钥对象
     * @return 私钥机密后的字符串
     */
    
    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(Constants.CHARSET),
                    privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    /**
     * 公钥解密
     *
     * @param data 待解密的源数据
     * @param publicKey 公钥对象
     * @return 解密后的字符串
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    publicKey.getModulus().bitLength()), Constants.CHARSET);
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
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
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
        IOUtils.closeQuietly(out);
        return resultDatas;
    }
    
    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥(Base64加密)
     * @param encode     字符集编码
     * @return 签名结果字符串
     */
    public static String sign(String content, String privateKey, String encode) {
        byte[] contentArr = new byte[0];
        if (StringUtils.isEmpty(encode)) {
            encode = Constants.CHARSET;
        }
        try {
            contentArr = content.getBytes(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] privateKeyArr = Base64.decodeBase64(privateKey);
        return sign(contentArr, privateKeyArr, DEFAULT_SIGNATURE_ALGORITHM);
    }
    
    /**
     * RSA签名
     *
     * @param contentArr                待签名数据
     * @param privateKeyArr             商户私钥
     * @param rsaSignatureAlgorithmEnum 签名算法
     * @return 签名值
     */
    public static String sign(byte[] contentArr, byte[] privateKeyArr, RsaSignatureAlgorithmEnum rsaSignatureAlgorithmEnum) {
        if (null == rsaSignatureAlgorithmEnum) {
            rsaSignatureAlgorithmEnum = DEFAULT_SIGNATURE_ALGORITHM;
        }
        try {
            PKCS8EncodedKeySpec priPkcs8 = new PKCS8EncodedKeySpec(privateKeyArr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PrivateKey priKey = keyFactory.generatePrivate(priPkcs8);
            java.security.Signature signature = java.security.Signature.getInstance(rsaSignatureAlgorithmEnum.toString());
            signature.initSign(priKey);
            signature.update(contentArr);
            byte[] signed = signature.sign();
            return Util.byteToHex(signed);
        } catch (Exception e) {
            throw new RuntimeException("签名发生异常", e);
        }
    }
    
    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值(十六进制)
     * @param publicKey 分配给开发商公钥(Base64加密)
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey, String encode) {
        byte[] contentArr = new byte[0];
        if (StringUtils.isEmpty(encode)) {
            encode = Constants.CHARSET;
        }
        try {
            contentArr = content.getBytes(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] publicKeyArr = Base64.decodeBase64(publicKey);
        byte[] signArr = Util.hexToByte(sign);
        return verify(contentArr, signArr, publicKeyArr, DEFAULT_SIGNATURE_ALGORITHM);
    }
    
    /**
     * RSA验签名检查
     *
     * @param contentArr                待签名数据
     * @param signArr                   签名值
     * @param publicKeyArr              分配给开发商公钥
     * @param rsaSignatureAlgorithmEnum 签名算法枚举类
     * @return 布尔值
     */
    public static boolean verify(byte[] contentArr, byte[] signArr, byte[] publicKeyArr, RsaSignatureAlgorithmEnum rsaSignatureAlgorithmEnum) {
        if (null == rsaSignatureAlgorithmEnum) {
            rsaSignatureAlgorithmEnum = DEFAULT_SIGNATURE_ALGORITHM;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyArr));
            java.security.Signature signature = java.security.Signature.getInstance(rsaSignatureAlgorithmEnum.toString());
            signature.initVerify(pubKey);
            signature.update(contentArr);
            return signature.verify(signArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
