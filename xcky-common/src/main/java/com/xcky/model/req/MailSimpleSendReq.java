package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简单发送邮件请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class MailSimpleSendReq {
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 收件人邮箱
     */
    private String to;
    /**
     * 邮件正文
     */
    private String text;
}
