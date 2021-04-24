package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeInfoPageReq extends PageReq {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 状态<br>
     * {@link com.xcky.enums.NoticeDistributeInfoStatusEnum}
     */
    private String status;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 模板内容关键字
     */
    private String msgContent;
}
