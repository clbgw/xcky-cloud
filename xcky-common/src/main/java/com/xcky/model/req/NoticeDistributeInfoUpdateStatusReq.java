package com.xcky.model.req;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息分发内容更新状态请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeInfoUpdateStatusReq {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 状态(N-未读,Y-已读,E-忽略,S-星标)
     */
    private String status;
    /**
     * 用户ID
     */
    private Integer userId;
    /************ 前端不传的参数 ***************/
    /**
     * 更新时间
     */
    private Date updateTime;
}
