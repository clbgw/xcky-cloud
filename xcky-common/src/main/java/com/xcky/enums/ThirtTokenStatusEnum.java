package com.xcky.enums;

import lombok.Getter;

/**
 * 第三方令牌信息状态枚举接口<br>
 * <ul>
 *     <li>Y-激活</li>
 *     <li>N-失效</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum ThirtTokenStatusEnum {
    /**
     * 激活
     */
    ACTIVE("Y", "激活"),
    /**
     * 失效
     */
    INVALID("N", "失效"),
    ;
    /**
     * 令牌信息状态编码
     */
    private final String code;
    /**
     * 令牌状态描述
     */
    private final String desc;
    
    ThirtTokenStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
