package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息类型分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeTypePageReq extends PageReq {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 状态(Y-激活,N-失效)
     */
    private String status;
    /**
     * 通知类型
     */
    private String typeName;
}
