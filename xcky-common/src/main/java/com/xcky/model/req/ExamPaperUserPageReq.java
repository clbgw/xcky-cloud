package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户答卷分页请求参数
 *
 * @author clbgw
 */
@Getter
@Setter
@ToString
public class ExamPaperUserPageReq extends PageReq {

    private Long userId;

}
