package com.xcky.model.entity;


import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户与问题关联关系
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionUser {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 星标(1-已打星，0-未打星)
     */
    private String starMark;
    /**
     * 收藏(1-已收藏，0-未收藏)
     */
    private String isCollect;
    /**
     * 错题(1-记录错题本,0-不在错题本)
     */
    private String isError;
    /**
     * 最近连续答对次数
     */
    private Integer recentRightCount;

}
