package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.model.req.MailSimpleSendReq;
import com.xcky.model.resp.R;
import com.xcky.util.Constants;
import com.xcky.util.MailUtil;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class MailController {
    @Autowired
    private MailUtil mailUtil;

    /**
     * 发送简单阿里云邮件
     *
     * @param mailSimpleSendReq 简单邮件请求参数
     * @return 响应对象
     */
    @PostMapping("/sendMail")
    @Async("emailExecutorService")
    public R sendMail(@RequestBody MailSimpleSendReq mailSimpleSendReq) {
        String toEmail = mailSimpleSendReq.getTo();
        if (StringUtils.isEmpty(toEmail) || !toEmail.contains(Constants.AT)) {
            log.error("收件人的邮箱不合法:{}", toEmail);
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 主题
        String subject = StringUtils.isEmpty(mailSimpleSendReq.getSubject()) ? "无题" : mailSimpleSendReq.getSubject();
        // 正文内容
        String text = StringUtils.isEmpty(mailSimpleSendReq.getText()) ? "" : mailSimpleSendReq.getText();
        try {
            // 给收件人发送邮箱
            mailUtil.sendAliYunMail(subject, toEmail, text);
            return ResponseUtil.ok();
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseEnum.SERVER_ERROR, e.getMessage());
        }
    }
}
