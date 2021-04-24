package com.xcky.model.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 题目类别(依赖类别表)
     */
    private String type;
    /**
     * 题目
     */
    private String title;
    /**
     * 题型(1-单选,2-多选,3-解答题)
     */
    private Long answerType;
    /**
     * 难度等级(1-5,数字越大越难)
     */
    private Integer difficultyLevel;
    /**
     * 问题得分数
     */
    private Integer score;
    /**
     * 答案列表
     */
    private List<ExamAnswerVo> answers;
}
