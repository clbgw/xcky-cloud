package com.xcky.enums;

import lombok.Getter;

/**
 * 系统权限状态枚举接口<br>
 * <ul>
 *     <li>0-冻结</li>
 *     <li>1-正常</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum SysAclStatusEnum {
    
    /**
     * 正常
     */
    NORMAL("1", "正常"),
    /**
     * 冻结
     */
    FORZEN("0", "冻结"),
    ;
    
    /**
     * 系统权限状态编码
     */
    private final String code;
    /**
     * 系统权限状态描述
     */
    private final String desc;
    
    SysAclStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
