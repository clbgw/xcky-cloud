package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 简历基本信息实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlBasicInfo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 宽度
     */
    private BigDecimal width;
    /**
     * 高度
     */
    private BigDecimal height;
    /**
     * 首页照片URL
     */
    private String photo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 岗位名称
     */
    private String job;
    /**
     * 工作年限
     */
    private String exp;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 期望工作地
     */
    private String address;
    /**
     * 简介
     */
    private String introduction;
}
