package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历技能值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlSkillVo {
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
}
