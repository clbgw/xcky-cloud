package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 消息分发配置
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeConfig {
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
