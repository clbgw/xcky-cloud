package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台部门实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysDept extends BaseBackEntity {
    /**
     * 部门ID
     */
    private Long id;
    /**
     * 上级部门ID
     */
    private Long parentId;
    /**
     * 部门在当前层级下的顺序，由小到大
     */
    private Long seq;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门层级
     */
    private String level;
    /**
     * 备注
     */
    private String remark;
}
