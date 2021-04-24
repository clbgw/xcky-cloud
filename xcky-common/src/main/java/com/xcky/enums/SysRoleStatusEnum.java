package com.xcky.enums;

import lombok.Getter;

/**
 * 系统角色状态枚举接口<br>
 * <ul>
 *     <li>0-冻结</li>
 *     <li>1-可用</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum SysRoleStatusEnum {
    
    /**
     * 可用
     */
    NORMAL("1", "可用"),
    /**
     * 冻结
     */
    FORZEN("0", "冻结"),
    ;
    
    /**
     * 系统角色状态编码
     */
    private final String code;
    /**
     * 系统角色状态描述
     */
    private final String desc;
    
    SysRoleStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
