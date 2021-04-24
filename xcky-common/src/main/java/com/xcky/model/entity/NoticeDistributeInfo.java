package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 消息分发内容
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeInfo {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 消息ID
     */
    private Long noticeId;
    /**
     * 完整消息内容
     */
    private String msgContent;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 状态<br>
     * {@link com.xcky.enums.NoticeDistributeInfoStatusEnum}
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
