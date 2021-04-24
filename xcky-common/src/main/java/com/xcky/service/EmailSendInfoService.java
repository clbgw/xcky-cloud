package com.xcky.service;

import com.xcky.model.entity.EmailSendInfo;

/**
 * 邮件发送信息服务接口
 *
 * @author lbchen
 */
public interface EmailSendInfoService {
    /**
     * 更新邮件发送信息
     *
     * @param emailSendInfo 邮件发送信息
     * @return 更新行数
     */
    Integer updateEmailSendInfo(EmailSendInfo emailSendInfo);
}
