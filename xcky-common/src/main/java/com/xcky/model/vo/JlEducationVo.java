package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历教育学历基本信息值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlEducationVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 学校
     */
    private String school;
    /**
     * 专业
     */
    private String major;
    /**
     * 始末年份
     */
    private String year;
    /**
     * 学校介绍
     */
    private String descStr;
}
