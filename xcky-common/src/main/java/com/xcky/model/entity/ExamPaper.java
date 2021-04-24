package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaper {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 试卷名称
     */
    private String name;
    /**
     * 生效开始时间
     */
    private Date beginTime;
    /**
     * 生效结束时间
     */
    private Date endTime;
    /**
     * 状态(1-启用，0-禁用)
     */
    private String status;
    /**
     * 考试分钟数
     */
    private Integer durationMinus;
    /**
     * 总分
     */
    private Integer totalScore;
    /**
     * 及格分
     */
    private Integer passScore;
    /**
     * 试卷描述
     */
    private String paperDesc;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
}
