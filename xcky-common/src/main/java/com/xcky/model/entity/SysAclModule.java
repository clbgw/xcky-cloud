package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统权限模块实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysAclModule extends BaseBackEntity {
    /**
     * 权限模块ID
     */
    private Long id;
    /**
     * 上级权限模块ID
     */
    private Long parentId;
    /**
     * 权限模块名称
     */
    private String name;
    /**
     * 权限模块层级
     */
    private String level;
    /**
     * 权限模块在当前层级下的顺序，由小到大
     */
    private Long seq;
    /**
     * 状态<br>
     * {@link com.xcky.enums.SysAclModuleStatusEnum}
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
}
