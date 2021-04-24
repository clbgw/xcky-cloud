package com.xcky.controller;

import com.xcky.model.resp.R;
import com.xcky.util.ResponseUtil;
import com.xcky.util.Util;
import com.xcky.util.encry.Sm3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SM3工具类控制器
 *
 * @author lbchen
 */
@Api( tags = "国密3算法控制器")
@RestController
public class Sm3Controller {
    /**
     * SM3签名
     *
     * @param src 源字符串
     * @return 响应对象
     */
    @ApiOperation("SM3签名")
    @GetMapping("/sm3/sign")
    public R sign(String src) {
        Sm3Util sm3Util = new Sm3Util();
        byte[] signArr = sm3Util.sign(src.getBytes());
        return ResponseUtil.ok(Util.byteToHex(signArr));
    }
    
    /**
     * SM3验签
     *
     * @param src     源字符串
     * @param signStr 签名字符串
     * @return 响应对象
     */
    @ApiOperation("SM3验签")
    @GetMapping("/sm3/verify")
    public R verify(String src, String signStr) {
        Sm3Util sm3Util = new Sm3Util();
        boolean result = sm3Util.verify(src.getBytes(), Util.hexToByte(signStr));
        return ResponseUtil.ok(result);
    }
}
