package com.xcky.enums;

import lombok.Getter;

/**
 * 系统权限类型枚举接口<br>
 * <ul>
 *     <li>1-菜单</li>
 *     <li>2-按钮</li>
 *     <li>3-其他</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum SysAclTypeEnum {
    /**
     * 菜单
     */
    MENU("1", "菜单"),
    /**
     * 其他
     */
    BUTTON("2", "按钮"),
    /**
     * 其他
     */
    OTHER("3", "其他"),
    ;
    
    /**
     * 系统权限类型编码
     */
    private final String code;
    /**
     * 系统权限类型描述
     */
    private final String desc;
    
    SysAclTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
