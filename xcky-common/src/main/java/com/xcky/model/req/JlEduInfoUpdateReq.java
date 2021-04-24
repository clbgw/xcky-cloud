package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新简历教育信息请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlEduInfoUpdateReq {
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
    /**
     * 关联简历ID
     */
    private Long jlId;
    /**
     * 排序
     */
    private Long sortNum = 1L;
}
