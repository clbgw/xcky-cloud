package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简历信息查询请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlInfoReq {
    /**
     * 简历信息主键ID
     */
    private Long jlId;
}
