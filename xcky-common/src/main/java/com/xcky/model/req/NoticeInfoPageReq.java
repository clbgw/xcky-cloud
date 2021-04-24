package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息信息分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeInfoPageReq extends PageReq {
    /**
     * 主键ID
     */
    private Long id;
}
