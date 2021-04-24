package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 消息类型
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeTypeInfo {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 通知类型
     */
    private String typeName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态(Y-激活,N-失效)
     */
    private String status;
    /**
     * 扫描周期配置,如每隔10s【10 * * * * *】
     */
    private String cronConfig;
    /**
     * 执行的URL
     */
    private String excuteUrl;
    /**
     * 通知内容模板
     */
    private String noticeTemplate;
}
