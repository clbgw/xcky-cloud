package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class OrderDetailInfo {
    /**
     * 商品编号
     */
    private Long goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品单位
     */
    private String goodsUnit;
    /**
     * 商品单价(原价)
     */
    private BigDecimal price;
    /**
     * 商品折后价
     */
    private BigDecimal couponPrice;
    /**
     * 商品开始有效期
     */
    private Date beginDate;
    /**
     * 商品结束有效期
     */
    private Date endDate;
    /**
     * 商品类型
     */
    private String goodsType;
    
    /********** 需要加工的字段 ****************/
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 商品购买数量
     */
    private BigDecimal buyNum;
    /**
     * 商品实付价(商品折后价-优惠券价格)
     */
    private BigDecimal payPrice;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
}
