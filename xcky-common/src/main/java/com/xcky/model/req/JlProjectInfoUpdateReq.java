package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新简历教育信息请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlProjectInfoUpdateReq {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目描述
     */
    private String descStr;
    /**
     * 关联简历ID
     */
    private Long jlId;
    /**
     * 排序
     */
    private Long sortNum = 1L;
}
