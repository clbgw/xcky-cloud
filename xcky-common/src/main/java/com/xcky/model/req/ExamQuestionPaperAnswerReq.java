package com.xcky.model.req;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题答案校验请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionPaperAnswerReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 试卷ID(如果是试卷才有此值)
     */
    private Long paperId;

    /**
     * 用户考试记录ID
     */
    private Long paperUserId;
    /**
     * 提交的问卷答案列表
     */
    private List<ExamQuestionPaperAnswerSubReq> answers;

}
