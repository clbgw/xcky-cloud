package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券信息值对象
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CouponInfoVo {
    /**
     * 券ID
     */
    private Long id;
    /**
     * 优惠券标题
     */
    private String couponTitle;
    /**
     * 优惠券使用规则
     */
    private String couponUseRule;
    /**
     * 优惠券描述
     */
    private String couponDesc;
    /**
     * 优惠券类型(1-总价折扣)
     */
    private String couponType;
    /**
     * 折扣类型:1-金额,2-折数
     */
    private String couponPriceType;
    /**
     * 折扣的金额/折数
     */
    private BigDecimal disPrice;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 优惠券状态:{@link com.xcky.enums.CouponStatusEnum}<br>
     */
    private String couponStatus;
    
    
    /********* 附加属性 ***************/
    /**
     * 用户领券关系主键ID
     */
    private Long userCouponId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户用券状态
     */
    private String useStatus;
    /**
     * 券编码
     */
    private String couponNo;
}
