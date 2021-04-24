package com.xcky.model.vo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页试卷值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaperPageVo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 试卷名称
     */
    private String name;
    /**
     * 生效开始时间
     */
    private Date beginTime;
    /**
     * 生效结束时间
     */
    private Date endTime;
    /**
     * 状态(1-启用，0-禁用)
     */
    private String status;
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
     * 试卷描述
     */
    private String paperDesc;
}
