package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 全局参数信息实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ParamInfo {
    /**
     * 参数类别名称
     */
    private String className;
    /**
     * 参数类别编码
     */
    private String classCode;
    /**
     * 参数值
     */
    private String codeValue;
    /**
     * 参数值描述
     */
    private String codeDesc;
    /**
     * 参数是否有效,Y-有效,N-无效
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
    /**
     * 排序编码(越小越靠前)
     */
    private Long sortNum;
}
