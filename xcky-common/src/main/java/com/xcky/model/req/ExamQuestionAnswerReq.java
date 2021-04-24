package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取问题答案请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionAnswerReq {
    /**
     * 问题ID
     */
    private Long questionId;
}
