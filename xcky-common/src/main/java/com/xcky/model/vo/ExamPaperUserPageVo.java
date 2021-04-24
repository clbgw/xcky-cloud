package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户答卷结果
 */
@Getter
@Setter
@ToString
public class ExamPaperUserPageVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 最终得分
     */
    private Integer score;
    /**
     * 答卷开始时间
     */
    private Date createTime;
    /**
     * 是否通过(1-通过,0-未通过)
     */
    private String isPass;
    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 试卷名称(冗余试卷信息)
     */
    private String paperName;
}
