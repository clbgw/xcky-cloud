package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 基本后台实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class BaseBackEntity {
    /**
     * 操作人名称
     */
    private String operator;
    /**
     * 最后一次更新时间
     */
    private Date operateTime;
    /**
     * 最后一次更新者的ip地址
     */
    private String operateIp;
}
