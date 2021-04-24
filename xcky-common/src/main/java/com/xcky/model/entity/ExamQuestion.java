package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestion {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 题目类别(依赖类别表)
     */
    private String type;
    /**
     * 题目
     */
    private String title;
    /**
     * 解析
     */
    private String desc;
    /**
     * 题型(1-单选,2-多选,3-解答题)
     */
    private Long answerType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
    /**
     * 状态(1-启用,0-禁用)
     */
    private String status;
    /**
     * 难度等级(1-5,数字越大越难)
     */
    private Integer difficultyLevel;
    /**
     * 问题得分数
     */
    private Integer score;
}
