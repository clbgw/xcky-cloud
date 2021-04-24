package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class GoodsInfo {
    /**
     * 商品编号
     */
    private Long id;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品单位
     */
    private String goodsUnit;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 商品折后价
     */
    private BigDecimal couponPrice;
    /**
     * 商品库存
     */
    private BigDecimal leftNum;
    /**
     * 每人每次限购库存
     */
    private BigDecimal peerNum;
    /**
     * 商品类型
     */
    private String goodsType;
    /**
     * 商品开始有效期
     */
    private Date beginDate;
    /**
     * 商品结束有效期
     */
    private Date endDate;
    /**
     * 商品状态<br>
     * {@link com.xcky.enums.GoodsInfoStatusEnum}
     */
    private String goodsStatus;
    /**
     * 主图URL
     */
    private String mainImg;
    /**
     * 商品简要描述
     */
    private String goodsDesc;
    
}
