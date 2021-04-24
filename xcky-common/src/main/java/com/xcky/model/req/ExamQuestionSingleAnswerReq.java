package com.xcky.model.req;

import java.util.Set;
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
public class ExamQuestionSingleAnswerReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 试卷ID(如果是试卷才有此值)
     */
    private Long paperId;
    /**
     * 提交答案
     */
    private Set<Long> answerIds;

}
