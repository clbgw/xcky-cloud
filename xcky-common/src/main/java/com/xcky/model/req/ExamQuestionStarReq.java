package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题打星请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionStarReq {
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 是否打星
     */
    private String starMark;
}
