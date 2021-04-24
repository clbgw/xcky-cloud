package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息分发内容统计数量请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeInfoNumReq {
    /**
     * 用户ID
     */
    private Integer userId;
}
