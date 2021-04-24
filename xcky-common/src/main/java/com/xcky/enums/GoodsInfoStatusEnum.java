package com.xcky.enums;

import lombok.Getter;

/**
 * 商品信息状态枚举接口<br>
 * <ul>
 *     <li>1-可用</li>
 *     <li>0-禁用</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum GoodsInfoStatusEnum {
    /**
     * 可用
     */
    ENABLE("1", "可用"),
    /**
     * 禁用
     */
    UNUSE("0", "禁用"),
    ;
    /**
     * 商品信息状态编码
     */
    private final String code;
    /**
     * 商品信息状态描述
     */
    private final String desc;
    
    GoodsInfoStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
