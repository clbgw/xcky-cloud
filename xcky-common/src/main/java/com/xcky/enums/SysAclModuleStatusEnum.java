package com.xcky.enums;

import lombok.Getter;

/**
 * 系统权限模块状态枚举接口<br>
 * <ul>
 *     <li>0-冻结</li>
 *     <li>1-正常</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum SysAclModuleStatusEnum {
    
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
     * 系统权限模块状态编码
     */
    private final String code;
    /**
     * 系统权限模块状态描述
     */
    private final String desc;
    
    SysAclModuleStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
