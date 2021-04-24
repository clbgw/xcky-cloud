package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息分发配置更新请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeConfigUpdateReq {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 通知信息ID
     */
    private Long noticeId;
    /**
     * 接收角色ID
     */
    private Integer noticeRoleId;
    /**
     * 接收用户ID
     */
    private Integer noticeUserId;
}
