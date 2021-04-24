package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.model.resp.R;
import com.xcky.util.ResponseUtil;
import com.xcky.util.Util;
import com.xcky.util.encry.Sm2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * SM2工具类控制器
 *
 * @author lbchen
 */
@RestController
public class Sm2Controller {
    @Autowired
    private Sm2Util sm2Util;
    
    /**
     * 生成SM2密钥对
     *
     * @return 响应对象
     */
    @GetMapping("/sm2/key")
    public R generateKeyPair() {
        Sm2Util.Sm2KeyPair sm2KeyPair = sm2Util.generateKeyPair();
        Map<String, Object> map = new HashMap<>(4);
        map.put("public", Util.byteToHex(sm2KeyPair.getPublicKey().getEncoded()));
        map.put("private", "" + sm2KeyPair.getPrivateKey());
        return ResponseUtil.ok(map);
    }
    
    /**
     * SM2公钥加密
     *
     * @param src       源字符串
     * @param publicKey 公钥
     * @return 响应对象
     */
    @GetMapping("/sm2/encode")
    public R encode(String src, String publicKey) {
        byte[] singArr = sm2Util.encrypt(src, sm2Util.getPublicKey(Util.hexToByte(publicKey)));
        return ResponseUtil.ok(Util.byteToHex(singArr));
    }
    
    /**
     * SM2私钥解密
     *
     * @param encodeStr  加密后的字符串
     * @param privateKey 私钥
     * @return 响应对象
     */
    @GetMapping("/sm2/decode")
    public R decode(String encodeStr, String privateKey) {
        try {
            String src = sm2Util.decrypt(Util.hexToByte(encodeStr), new BigInteger(privateKey));
            return ResponseUtil.ok(src);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(ResponseEnum.DECODE_FAIL);
        }
    }
}
