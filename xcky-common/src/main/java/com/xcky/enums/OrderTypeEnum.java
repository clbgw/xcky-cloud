package com.xcky.enums;

import lombok.Getter;

/**
 * 订单类型枚举接口<br>
 * <ul>
 *     <li>1-虚拟订单</li>
 *     <li>2-实物订单</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum OrderTypeEnum {
    /**
     * 虚拟订单
     */
    VIRTUAL_ORDER("1", "虚拟订单"),
    /**
     * 实物订单
     */
    PHYSICAL_ORDER("2", "实物订单"),
    ;
    
    /**
     * 订单类型编码
     */
    private final String code;
    /**
     * 订单类型描述
     */
    private final String desc;
    
    OrderTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
