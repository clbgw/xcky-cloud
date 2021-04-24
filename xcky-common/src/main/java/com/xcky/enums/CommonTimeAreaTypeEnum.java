package com.xcky.enums;

import lombok.Getter;

/**
 * 公共时间范围类型枚举接口<br>
 *
 * @author lbchen
 */
@Getter
public enum CommonTimeAreaTypeEnum {
    /**
     * 系统用户
     */
    SYS_USER("1", "系统用户"),
    /**
     * 角色
     */
    SYS_ROLE("2", "角色"),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;

    CommonTimeAreaTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
