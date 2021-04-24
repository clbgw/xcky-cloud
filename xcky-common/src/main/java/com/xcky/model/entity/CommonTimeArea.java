package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共时间范围实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CommonTimeArea {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 类型下的主键ID
     */
    private Long typeId;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 该时间范围内是否停用(0-停用，1-生效)
     */
    private Integer valid;
    /**
     * 记录是否有效(1-生效,0-失效)
     */
    private String status;
}
