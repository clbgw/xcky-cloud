package com.xcky.model.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷题目分页值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaperQuestionPageVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 试卷名称
     */
    private String name;
    /**
     * 考试分钟数
     */
    private Integer durationMinus;
    /**
     * 总分
     */
    private Integer totalScore;
    /**
     * 及格分
     */
    private Integer passScore;
    /**
     * 问题ID列表
     */
    private List<Long> questionIds;

}
