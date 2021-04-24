package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历项目经历值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlProjectVo {
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
}
