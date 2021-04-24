package com.xcky.service.impl;

import com.xcky.mapper.EmailSendInfoMapper;
import com.xcky.model.entity.EmailSendInfo;
import com.xcky.service.EmailSendInfoService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮件发送信息服务实现类
 *
 * @author lbchen
 */
@Service
public class EmailSendInfoServiceImpl implements EmailSendInfoService {
    @Autowired
    private EmailSendInfoMapper emailSendInfoMapper;
    @Override
    public Integer updateEmailSendInfo(EmailSendInfo emailSendInfo) {
        Date nowDate = new Date();
        emailSendInfo.setCreateTime(nowDate);
        return emailSendInfoMapper.insertEmailSendInfo(emailSendInfo);
    }
}
