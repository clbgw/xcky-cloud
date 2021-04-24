package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历项目经历实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlProject {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目描述
     */
    private String descStr;
    /**
     * 关联简历ID
     */
    private Long jlId;
    /**
     * 排序
     */
    private Long sortNum;
}
