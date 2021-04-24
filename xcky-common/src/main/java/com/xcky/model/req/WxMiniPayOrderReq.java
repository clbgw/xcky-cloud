package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxMiniPayOrderReq {
    /**
     * 付款的小程序APPID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 付款人
     */
    private String openid;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 支付价格
     */
    private String price;
    /**
     * 用户ID
     */
    private Long userId;
}
