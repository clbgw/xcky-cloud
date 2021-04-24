package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户领券关系表
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserCouponRela {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 券ID
     */
    private Long couponId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 券编码
     */
    private String couponNo;
    /**
     * 优惠券使用状态: {@link com.xcky.enums.CouponUseStatusEnum}<br>
     */
    private String useStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
