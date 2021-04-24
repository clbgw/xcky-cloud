package com.xcky.model.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息分发内容数量响应对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeInfoNumResp {
    /**
     * 状态值
     */
    private String status;
    /**
     * 数量
     */
    private Integer num;
}
