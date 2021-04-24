package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 答案实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamAnswer {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 选项或者答案
     */
    private String answer;
    /**
     * 是否正确(0-错误,1-正确)
     */
    private String isRight;
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
}
