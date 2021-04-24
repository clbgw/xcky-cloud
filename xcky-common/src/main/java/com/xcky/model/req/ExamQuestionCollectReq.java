package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题收藏请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionCollectReq {
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 是否收藏
     */
    private String isCollect;
}
