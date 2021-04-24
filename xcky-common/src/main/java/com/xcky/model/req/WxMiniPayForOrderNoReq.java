package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 小程序订单继续付款请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxMiniPayForOrderNoReq {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 微信OPENID
     */
    private String openid;
}
