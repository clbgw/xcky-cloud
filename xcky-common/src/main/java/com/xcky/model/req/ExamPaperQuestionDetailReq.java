package com.xcky.model.req;

import com.xcky.enums.ExamModeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题详情请求参数
 */
@Getter
@Setter
@ToString
public class ExamPaperQuestionDetailReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 是否是考试模式,默认使用干净模式
     */
    private Integer examMode = ExamModeEnum.CLEAN.getCode();
}
