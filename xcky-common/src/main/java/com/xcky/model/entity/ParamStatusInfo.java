package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 参数状态信息实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ParamStatusInfo {
    /**
     * 参数类型
     */
    private String type;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 当前状态
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
