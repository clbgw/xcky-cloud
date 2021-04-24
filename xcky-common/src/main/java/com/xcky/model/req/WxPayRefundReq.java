package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信支付退款请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxPayRefundReq {
    /**
     * 微信公众号APPID
     */
    private String appId;
    /**
     * 微信商户号
     */
    private String mchId;
    /**
     * 订单编号
     */
    private String outTradeNo;
    /**
     * 总价(单位分)
     */
    private Integer totalFee;
    /**
     * 退款金额(单位分)
     */
    private Integer refundFee;
}
