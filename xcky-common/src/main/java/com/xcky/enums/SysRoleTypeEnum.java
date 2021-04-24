package com.xcky.enums;

import lombok.Getter;

/**
 * 系统角色类型枚举接口<br>
 * <ul>
 *     <li>0-冻结</li>
 *     <li>1-可用</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum SysRoleTypeEnum {
    /**
     * 管理员角色
     */
    NORMAL("1", "管理员角色"),
    /**
     * 其他
     */
    FORZEN("2", "其他"),
    ;
    
    /**
     * 系统角色类型编码
     */
    private final String code;
    /**
     * 系统角色类型描述
     */
    private final String desc;
    
    SysRoleTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
