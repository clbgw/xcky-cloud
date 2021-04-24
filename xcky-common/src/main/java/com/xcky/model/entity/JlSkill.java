package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历技能实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlSkill {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 技能名称
     */
    private String name;
    /**
     * 掌握百分比
     */
    private Integer percent;
    /**
     * 关联简历ID
     */
    private Long jlId;
    /**
     * 排序
     */
    private Long sortNum;
}
