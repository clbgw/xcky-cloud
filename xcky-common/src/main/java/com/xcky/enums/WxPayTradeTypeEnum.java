package com.xcky.enums;

import lombok.Getter;

/**
 * 微信支付交易类型枚举接口<br>
 * <ul>
 *     <li>JSAPI-JSAPI支付(或小程序支付)</li>
 *     <li>NATIVE-Native支付</li>
 *     <li>APP-APP支付</li>
 *     <li>MWEB-H5支付</li>
 *     <li>MICROPAY-付款码支付</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum WxPayTradeTypeEnum {
    /**
     * JSAPI支付(或小程序支付)
     */
    JSAPI("JSAPI", "JSAPI支付"),
    /**
     * Native支付
     */
    NATIVE("NATIVE", "Native支付"),
    /**
     * app支付
     */
    APP("APP", "APP支付"),
    /**
     * H5支付
     */
    MWEB("MWEB", "H5支付"),
    /**
     * 付款码支付
     */
    MICROPAY("MICROPAY", "付款码支付");
    
    /**
     * 微信支付交易类型编码
     */
    private final String code;
    /**
     * 微信支付交易类型描述
     */
    private final String desc;
    
    WxPayTradeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
