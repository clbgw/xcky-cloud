package com.xcky.util;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.EmailSendInfo;
import com.xcky.service.EmailSendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
/**
 * 邮件工具类
 *
 * @author lbchen
 */
@Component
@Slf4j
public class MailUtil {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private EmailSendInfoService emailSendInfoService;

    /**
     * 发送阿里云邮件
     *
     * @param subject 邮件主题
     * @param to      收件人
     * @param text    邮件内容
     */
    public void sendAliYunMail(String subject, String to, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String fromEmail = Constants.DEFAULT_FORM_EMAIL;
        simpleMailMessage.setFrom(fromEmail);
        //设置邮件主题
        simpleMailMessage.setSubject(subject);
        //设定收件人
        simpleMailMessage.setTo(to);
        //设置邮件内容
        simpleMailMessage.setText(text);
        //发送邮件
        try {
            mailSender.send(simpleMailMessage);
            // 保存邮件发送信息
            EmailSendInfo emailSendInfo = new EmailSendInfo();
            emailSendInfo.setEmailContent(text);
            emailSendInfo.setFromEmail(fromEmail);
            emailSendInfo.setSubject(subject);
            emailSendInfo.setToEmail(to);
            Integer updateEmail = emailSendInfoService.updateEmailSendInfo(emailSendInfo);
            if(null == updateEmail || updateEmail < 1) {
                log.info("新增邮件发送信息失败: " + JSONObject.toJSONString(emailSendInfo));
            }
        } catch (Exception e) {
            throw new BizException(ResponseEnum.SERVER_ERROR, null);
        }

    }
}
