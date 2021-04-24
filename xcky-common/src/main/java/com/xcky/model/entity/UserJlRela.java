package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户简历关系表
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserJlRela {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 用户OPENID
     */
    private String openid;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 简历ID
     */
    private Long jlId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
    /**
     * 状态(0-删除或失效,1-生效)<br>
     * {@link com.xcky.enums.UserJlRelaStatusEnum}
     */
    private String status;

}
