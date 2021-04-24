package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 订单信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class OrderInfo extends BaseSimpleTimeEntity {
    /**
     * 公众号APPID
     */
    private String appid;
    /**
     * 商户号ID
     */
    private String mchId;
    /**
     * 付款人
     */
    private String openid;
    /**
     * 订单类型<br>
     * {@link com.xcky.enums.OrderTypeEnum}
     */
    private String orderType;
    /**
     * 订单编号(orderTradeNo)
     */
    private String orderNo;
    /**
     * 支付订单编号(通知支付回调时的订单号)
     */
    private String payOrderNo;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单状态
     * {@link com.xcky.enums.OrderStatusEnum}
     */
    private String orderStatus;
    /**
     * 购买的商品数量
     */
    private BigDecimal goodsBuyNum;
    /**
     * 订单应支付金额(直接商品金额,不计算折扣和优惠券)
     */
    private BigDecimal orderPriceAmount;
    /**
     * 订单应支付金额(直接商品金额,计算折扣)
     */
    private BigDecimal orderCouponPriceAmount;
    /**
     * 用户实付金额(直接商品金额,计算折扣和所有优惠券)
     */
    private BigDecimal orderPayAmount;
    /**
     * 收货地址相关信息(订单类型(orderType=2)时才有收货地址信息)
     */
    private String shippingAddress;
}
