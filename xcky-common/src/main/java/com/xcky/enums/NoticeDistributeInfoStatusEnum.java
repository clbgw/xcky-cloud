package com.xcky.enums;

import lombok.Getter;

/**
 * 消息分发内容状态枚举接口<br>
 * <ul>
 *     <li>1-星标</li>
 *     <li>3-未读</li>
 *     <li>8-已读</li>
 *     <li>9-忽略</li>
 *     <li>99-删除</li>
 *     <li>999-已删除</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum NoticeDistributeInfoStatusEnum {
    
    /**
     * 星标
     */
    STAR("1", "星标"),
    /**
     * 未读
     */
    NO_READ("3", "未读"),
    /**
     * 已读
     */
    HAD_READ("8", "已读"),
    /**
     * 忽略
     */
    IGNORE("9", "忽略"),
    /**
     * 删除
     */
    DELETE("99", "删除"),
    /**
     * 已删除
     */
    DELETEED("999", "已删除");
    /**
     * 消息分发内容状态编码
     */
    private final String code;
    /**
     * 消息分发内容状态描述
     */
    private final String desc;
    
    NoticeDistributeInfoStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
