package com.xcky.enums;

import lombok.Getter;

/**
 * 用户用券使用状态<br>
 * <ul>
 *     <li>1-已领用</li>
 *     <li>2-已使用</li>
 *     <li>3-已过期</li>
 * </ul>
 * @author lbchen
 */
@Getter
public enum CouponUseStatusEnum {
    /**
     * 1-已领用
     */
    COLLECTED("1", "已领用"),
    /**
     * 2-已使用
     */
    USED("2", "已使用"),
    /**
     * 3-已过期
     */
    EXPIRED("3", "已过期"),
    ;
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
    
    CouponUseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
