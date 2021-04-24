package com.xcky.enums;

import lombok.Getter;

/**
 * 系统用户状态枚举接口<br>
 * <ul>
 *     <li>0-冻结</li>
 *     <li>1-正常</li>
 *     <li>2-删除</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum SysUserStatusEnum {
    
    /**
     * 正常
     */
    NORMAL("1", "正常"),
    /**
     * 冻结
     */
    FORZEN("0", "冻结"),
    /**
     * 删除
     */
    DELETED("2", "删除"),
    ;
    
    /**
     * 系统用户状态编码
     */
    private final String code;
    /**
     * 系统用户状态描述
     */
    private final String desc;
    
    SysUserStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
