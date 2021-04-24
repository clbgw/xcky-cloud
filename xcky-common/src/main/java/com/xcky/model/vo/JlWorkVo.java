package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历工作简介值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlWorkVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 工作岗位
     */
    private String job;
    /**
     * 工作始末年份
     */
    private String year;
    /**
     * 工作描述
     */
    private String descStr;
}
