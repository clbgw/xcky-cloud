package com.xcky.enums;

import lombok.Getter;

/**
 * 用户简历关系状态枚举接口<br>
 * <ul>
 * <li>0-删除或失效</li>
 * <li>1-生效</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum UserJlRelaStatusEnum {
    /**
     * 删除或失效
     */
    INVALID("0", "删除或失效"),
    /**
     * 生效
     */
    VALID("1", "生效"),;

    /**
     * 用户简历关系状态编码
     */
    private final String code;
    /**
     * 用户简历关系状态描述
     */
    private final String desc;

    UserJlRelaStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
