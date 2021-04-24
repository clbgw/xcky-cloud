package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class PageReq {
    /**
     * 页码(从1开始的正整数)
     */
    private Integer page;
    /**
     * 每页显示的数据数量(从1开始的正整数)
     */
    private Integer size;
}
