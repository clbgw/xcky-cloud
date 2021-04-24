package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 答案值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamAnswerVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 选项或者答案
     */
    private String answer;
    /**
     * 是否正确(0-错误,1-正确)
     */
    private String isRight = "0";
}
