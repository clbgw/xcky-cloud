package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台角色权限关系实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysRoleAcl extends BaseBackEntity {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long aclId;
}
