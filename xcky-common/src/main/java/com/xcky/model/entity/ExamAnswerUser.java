package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户与提交答案关联关系
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamAnswerUser {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户提交的答案
     */
    private String submitAnswer;
    /**
     * 是否正确(1-正确,0-错误,2-部分答对)
     */
    private String isRight;

    /**
     * 试卷ID(如果是按试卷提交则有值)
     */
    private Long paperId;
}
