package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题记录错题请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionErrorReq {
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 是否记录错题本
     */
    private String isError;
}
