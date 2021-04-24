package com.xcky.mapper;

import com.xcky.model.entity.EmailSendInfo;

/**
 * 邮件发送信息数据访问接口
 *
 * @author lbchen
 */
public interface EmailSendInfoMapper {
    /**
     * 新增邮件发送信息
     *
     * @param emailSendInfo 邮件发送信息
     * @return 影响行数
     */
    Integer insertEmailSendInfo(EmailSendInfo emailSendInfo);
}
