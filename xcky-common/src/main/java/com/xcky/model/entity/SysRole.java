package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台角色实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysRole extends BaseBackEntity {
    /**
     * 角色ID
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色的类型<br>
     * {@link com.xcky.enums.SysRoleTypeEnum}
     */
    private String type;
    /**
     * 状态<br>
     * {@link com.xcky.enums.SysRoleStatusEnum}
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
}
