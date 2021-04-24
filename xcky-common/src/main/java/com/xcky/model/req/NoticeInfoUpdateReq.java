package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 消息信息更新请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeInfoUpdateReq {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 记录日期
     */
    private Date recordDate;
    /**
     * 类型ID
     */
    private Long noticeTypeId;
    /**
     * 提示消息模板
     */
    private String messageTemplate;
    /**
     * 消息等级
     */
    private String msgLevel;
    /**
     * 消息状态(Y-删除,N-未删除)
     */
    private String isDelete;
    /**
     * 删除时间
     */
    private Date deleteTime;
    
}
