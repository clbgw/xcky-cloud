package com.xcky.enums;

import lombok.Getter;

/**
 * 优惠券编码的状态
 * <ul>
 *     <li>Y-已领用</li>
 *     <li>N-未领用</li>
 * </ul>
 * @author lbchen
 */
@Getter
public enum CouponNoStatusEnum {
    /**
     * Y-已领用
     */
    USED("Y","已领用"),
    /**
     * N-未领用
     */
    CAN_USE("N","未领用"),
    ;
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
    
    CouponNoStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
