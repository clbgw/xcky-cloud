package com.xcky.model.req;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 试卷分页列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamPaperPageReq extends PageReq {

    private String status;

    private Date endTime;


}
