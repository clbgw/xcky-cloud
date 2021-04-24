package com.xcky.util.encry;

import com.xcky.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * @author lbchen
 */
@Slf4j
public class AesUtil {
    /**
     * AES解密
     *
     * @param encryData  加密后的数据
     * @param iv         初始向量
     * @param sessionKey 会话密钥
     * @return 解密后的字符串
     */
    public static String decode(String encryData, String iv, String sessionKey) {
        byte[] dataByte = Base64Util.decode(encryData);
        byte[] ivByte = Base64Util.decode(iv);
        byte[] keyByte = Base64Util.decode(sessionKey);
        return aes128CbcDecrypt(dataByte, ivByte, keyByte);
    }
    
    /**
     * AES解密结果
     *
     * @param dataByte 加密的数组
     * @param ivByte   初始向量
     * @param keyByte  会话密钥
     * @return 解密结果
     */
    private static String aes128CbcDecrypt(byte[] dataByte, byte[] ivByte, byte[] keyByte) {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, Constants.CHARSET);
                log.error("解密结果:" + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
