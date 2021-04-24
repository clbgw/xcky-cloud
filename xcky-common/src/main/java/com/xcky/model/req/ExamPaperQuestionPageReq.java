package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷题目分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaperQuestionPageReq extends PageReq {
    /**
     * 试卷ID
     */
    private Long paperId;


    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户考试记录ID
     */
    private Long paperUserId;
}
