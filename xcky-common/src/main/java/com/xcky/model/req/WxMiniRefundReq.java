package com.xcky.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信小程序支付申请退款请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxMiniRefundReq {
    /******* 必填字段 *********/
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    @JSONField(name = "mch_id")
    private String mchId;
    /**
     * 随机字符串
     */
    @JSONField(name = "nonce_str")
    private String nonceStr;
    /**
     * 签名
     */
    private String sign;
    /**
     * 微信支付订单号,微信生成的订单号，在支付通知中有返回<br>
     * transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
     */
    @JSONField(name = "transaction_id")
    private String transactionId;
    /**
     * 商户订单号,商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一<br>
     * transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * 商户退款单号
     */
    @JSONField(name = "out_refund_no")
    private String outRefundNo;
    /**
     * 标价金额,订单总金额，单位为分
     */
    @JSONField(name = "total_fee")
    private String totalFee;
    /**
     * 退款金额，单位为分
     */
    @JSONField(name = "refund_fee")
    private String refundFee;
    /******* 选填字段 *********/
    /**
     * 签名类型,签名类型，默认为MD5，支持HMAC-SHA256和MD5
     */
    @JSONField(name = "sign_type")
    private String signType = "MD5";
    /**
     * 退款货币种类<br>
     * 需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    @JSONField(name = "refund_fee_type")
    private String refundFeeType = "CNY";
    /**
     * 退款原因
     */
    @JSONField(name = "refund_desc")
    private String refundDesc;
    /**
     * 退款资金来源<br>
     * 仅针对老资金流商户使用<br>
     * REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款（默认使用未结算资金退款）<br>
     * REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款<br>
     */
    @JSONField(name = "refund_account")
    private String refundAccount;
    /**
     * 退款结果通知url<br>
     * 异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数<br>
     * 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;
}
