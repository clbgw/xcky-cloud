package com.xcky.model.req;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷问题答案校验子请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionPaperAnswerSubReq {
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 提交答案
     */
    private Set<Long> answerIds;

    /**
     * 该问题的分数【数据库的结果】
     */
    private Integer score;

}
