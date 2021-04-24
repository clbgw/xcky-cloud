package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 问题类型实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ExamType {
    /**
     * 问题类型ID
     */
    private Long id;
    /**
     * 问题类型名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
    /**
     * 状态(1-启用,0-禁用)
     */
    private String status;
}
