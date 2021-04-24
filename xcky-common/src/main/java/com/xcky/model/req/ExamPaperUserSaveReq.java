package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷与用户关联关系保存请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaperUserSaveReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 试卷ID
     */
    private Long paperId;
}
