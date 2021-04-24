package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 简单时间实体扩展字段<br>
 * <ul>
 *     <li>createTime-创建时间</li>
 *     <li>updateTime-更新时间</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class BaseSimpleTimeEntity {
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
