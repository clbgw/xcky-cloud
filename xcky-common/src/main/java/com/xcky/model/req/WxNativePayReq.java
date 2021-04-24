package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信扫码支付请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxNativePayReq {
    /**
     * 微信服务号APPID
     */
    private String appId;
    /**
     * 商户号ID
     */
    private String mchId;
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品数量
     */
    private Integer goodsNum = 1;
}
