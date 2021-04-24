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
public class JlWorkInfoUpdateReq {
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
