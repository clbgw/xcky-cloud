package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 券码详细信息
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CouponNoInfo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 优惠券ID
     */
    private Long couponId;
    /**
     * 优惠券编码
     */
    private String couponNo;
    /**
     * 优惠券状态：{@link com.xcky.enums.CouponNoStatusEnum}
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
