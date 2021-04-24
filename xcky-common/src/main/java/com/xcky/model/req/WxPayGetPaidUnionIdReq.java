package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信支付后获用户UNIONID
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxPayGetPaidUnionIdReq {
    /**
     * 微信用户OPENID
     */
    private String openid;
    /**
     * 微信支付订单号(如果有该字段,可以不使用下面的mchId和outTradeNo)
     */
    private String transactionId;
    /**
     * 微信支付分配的商户号，和商户订单号配合使用(如果使用此字段和outTrade,则不需要传输transcationId)
     */
    private String mchId;
    /**
     * 微信支付商户订单号，和商户号配合使用(如果使用此字段和mchId,则不需要传输transcationId)
     */
    private String outTradeNo;
}
