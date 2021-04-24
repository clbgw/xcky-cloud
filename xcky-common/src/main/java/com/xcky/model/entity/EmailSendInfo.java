package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 邮件发送信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class EmailSendInfo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 发件人邮件地址
     */
    private String fromEmail;
    /**
     * 接收人邮件地址
     */
    private String toEmail;
    /**
     * 标题
     */
    private String subject;
    /**
     * 邮件正文
     */
    private String emailContent;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 记录日期
     */
    private Date recordDate;
}
