package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 微信小程序二维码创建记录
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlQrcodeCreate {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 微信APPID
     */
    private String appid;
    /**
     * 场景值
     */
    private String scene;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
    /**
     * 存储路径
     */
    private String destPath;
    /**
     * 图片存储路径
     */
    private String picPath;
    /**
     * 文件名称
     */
    private String fileName;
}
