package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 文件信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class FileInfo {
    /**
     * 文件ID
     */
    private Integer id;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 用户unionid
     */
    private String unionid;
    /**
     * 简要描述
     */
    private String simpleDesc;
    /**
     * 是否公开
     */
    private String isPublic;
    /**
     * 是否分享
     */
    private String isShare;
    /**
     * 记录日期
     */
    private Date recordDate;
    /**
     * 标签
     */
    private String tags;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 文件真实可访问url
     */
    private String fileUrl;
    /**
     * 文件状态
     */
    private String fileStatus;

}
