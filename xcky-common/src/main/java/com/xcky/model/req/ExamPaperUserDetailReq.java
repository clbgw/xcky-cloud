package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户答卷详情请求参数
 * @author clbgw
 */
@Getter
@Setter
@ToString
public class ExamPaperUserDetailReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户答卷ID
     */
    private Long paperUserId;
}
