package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题解析请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionDescReq {
    /**
     * 问题ID
     */
    private Long questionId;
}
