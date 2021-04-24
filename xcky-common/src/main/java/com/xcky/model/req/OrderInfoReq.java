package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 下单请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class OrderInfoReq {
    /**
     * 公众号APPID
     */
    private String appid;
    /**
     * 商户号ID
     */
    private String mchId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单类型(1-虚拟订单,2-实物订单)
     */
    private String orderType;
    
    /**
     * 收货地址ID(订单类型(orderType=2)时才有收货地址信息)
     */
    private Long shippingId;
    /**
     * 商品列表
     */
    private List<OrderGoodsInfoReq> goods;
    /**
     * 使用优惠券列表
     */
    private List<CouponInfoReq> coupons;
    /**
     * 购买的商品数量
     */
    private BigDecimal goodsBuyNum;
    /**
     * 用户实付金额
     */
    private BigDecimal payAmount;
    
    /**
     * 抵扣的积分数
     */
    private BigDecimal integralAmount;
    
    //TODO 订单代金券
    //TODO 订单成交时间
    //TODO 订单备注
}
