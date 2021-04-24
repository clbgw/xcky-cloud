package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户答卷记录实体类
 */
@Getter
@Setter
@ToString
public class ExamPaperUser {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 最终得分
     */
    private Integer score;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
    /**
     * 是否通过(1-通过,0-未通过)
     */
    private String isPass;
    /**
     * 总分
     */
    private Integer totalScore;
    /**
     * 评卷时间
     */
    private Date revisionTime;
    /**
     * 评卷人
     */
    private Long revisionUserId;
}
