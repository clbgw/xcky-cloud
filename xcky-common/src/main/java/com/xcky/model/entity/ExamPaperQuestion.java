package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷与问题关联关系
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaperQuestion {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 问题ID
     */
    private Long questionId;
}
