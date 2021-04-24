package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题标记分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamQuestionMarkPageReq extends PageReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 是否记录错题本
     */
    private String isError;
    /**
     * 是否收藏
     */
    private String isCollect;
    /**
     * 是否打星
     */
    private String starMark;
}
