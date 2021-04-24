package com.xcky.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信小程序支付统一下单请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxMiniUnifiedorderReq {
    /******* 必填字段 *********/
    /**
     * 小程序ID
     */
    private String appid;
    /**
     * 商户号
     */
    @JsonProperty("mch_id")
    private String mchId;
    /**
     * 随机字符串
     */
    @JsonProperty("nonce_str")
    private String nonceStr;
    /**
     * 签名
     */
    private String sign;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商户订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    /**
     * 标价金额,订单总金额，单位为分
     */
    @JsonProperty("total_fee")
    private Integer totalFee;
    /**
     * 终端IP
     */
    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;
    /**
     * 通知地址
     */
    @JsonProperty("notify_url")
    private String notifyUrl;
    /**
     * 交易类型<br>
     * {@link com.xcky.enums.WxPayTradeTypeEnum}
     */
    @JsonProperty("trade_type")
    private String tradeType;
    
    /******* 选填字段 *********/
    /**
     * 签名类型,签名类型，默认为MD5，支持HMAC-SHA256和MD5
     */
    @JsonProperty("sign_type")
    private String signType = "MD5";
    /**
     * 标价币种
     */
    @JsonProperty("fee_type")
    private String feeType = "CNY";
    /**
     * 用户标识<br>
     * trade_type=JSAPI，此参数必传
     */
    private String openid;
    /**
     * 设备号
     */
    @JsonProperty("device_info")
    private String deviceInfo;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 附加数据
     */
    private String attach;
    
    /**
     * 交易起始时间,格式为yyyyMMddHHmmss
     */
    @JsonProperty("time_start")
    private String timeStart;
    /**
     * 交易结束时间,格式为yyyyMMddHHmmss
     */
    @JsonProperty("time_expire")
    private String timeExpire;
    /**
     * 订单优惠标记
     */
    @JsonProperty("goods_tag")
    private String goodsTag;
    /**
     * 商品ID
     */
    @JsonProperty("product_id")
    private String productId;
    /**
     * 指定支付方式
     */
    @JsonProperty("limit_pay")
    private String limitPay;

    /**
     * 电子发票入口开放标识
     */
    private String receipt;
    /**
     * 场景信息
     */
    @JsonProperty("scene_info")
    private WxMiniUnifiedorderSceneInfoReq sceneInfo;
}
