package com.xcky.enums;

import lombok.Getter;

/**
 * 账号类型枚举接口<br>
 * <ul>
 *     <li>ttMini-头条</li>
 *     <li>wxMini-微信小程序</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum AccountTypeEnum {
    /**
     * 虚拟订单
     */
    TOUTIAO("ttMini", "头条"),
    /**
     * 实物订单
     */
    WXMINI("wxMini", "微信小程序"),
    ;

    /**
     * 账号类型编码
     */
    private final String code;
    /**
     * 账号类型描述
     */
    private final String desc;

    AccountTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
