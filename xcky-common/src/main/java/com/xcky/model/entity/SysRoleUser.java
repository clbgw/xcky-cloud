package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台角色用户关系表实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysRoleUser extends BaseBackEntity {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 生效开始时间
     */
    private Date startTime;
    /**
     * 生效结束时间
     */
    private Date endTime;
}
