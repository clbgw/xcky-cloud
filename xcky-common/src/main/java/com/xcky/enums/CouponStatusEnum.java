package com.xcky.enums;

import lombok.Getter;

/**
 * 优惠券的状态<br>
 *
 * <ul>
 *     <li>1-上架</li>
 *     <li>2-下架</li>
 *     <li>3-删除</li>
 * </ul>
 * @author lbchen
 */
@Getter
public enum CouponStatusEnum {
    /**
     * 1-上架
     */
    ON_SHELF("1","上架"),
    /**
     * 2-下架
     */
    OFF_SHELF("2","下架"),
    /**
     * 3-删除
     */
    DELETED("3","删除"),
    ;
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
    
    CouponStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
