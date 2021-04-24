package com.xcky.schedule;

import com.alibaba.fastjson.JSONObject;
import com.xcky.config.WxConfig;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.pojo.MchAccount;
import com.xcky.model.pojo.MiniAccount;
import com.xcky.model.pojo.SerAccount;
import com.xcky.model.pojo.SubAccount;
import com.xcky.model.wx.WxGetAccessTokenResp;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.WxMiniConstants;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 微信公众号普通调度类
 *
 * @author lbchen
 */
@Component
@Slf4j
public class WxCommonJob {
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private ThirtTokenInfoService thirtTokenInfoService;
    @Autowired
    private WxConfig wxConfig;

    /**
     * 同步微信商户账号
     */
    @Scheduled(cron = "50 3 2 * * *")
    public void syncMchAccount() {
        if (null == wxConfig) {
            return;
        }
        Map<String, MchAccount> mchAccountMap = wxConfig.getMchAccount();
        if (null == mchAccountMap) {
            return;
        }
        for (String mchId : mchAccountMap.keySet()) {
            MchAccount mchAccount = mchAccountMap.get(mchId);
            ThirtTokenInfo thirtTokenInfo = new ThirtTokenInfo();
            thirtTokenInfo.setAppid(mchId);
            thirtTokenInfo.setRemark(mchAccount.getRemark());
            thirtTokenInfo.setStatus(mchAccount.getStatus());
            thirtTokenInfo.setType(mchAccount.getType());
            thirtTokenInfo.setApiSecret(mchAccount.getApiSecret());
            thirtTokenInfo.setApi3Secret(mchAccount.getApi3Secret());
            thirtTokenInfo.setNotifyUrl(mchAccount.getNotifyUrl());
            thirtTokenInfo.setRefundCertPath(mchAccount.getRefundCertPath());
            Integer update = thirtTokenInfoService.updateThirtTokenInfo(thirtTokenInfo);
            if (null == update || update < 1) {
                log.error("保存商户信息失败: {}", JSONObject.toJSONString(thirtTokenInfo));
            }
        }
    }

    /**
     * 获取微信公众号的token
     */
    @Scheduled(cron = "20 0/30 * * * *")
    public void getWxToken() {
        log.info("开始获取token");
        String secret;
        String remark;
        String type;
        String status;
        String mail;
        String loginPwd;
        String orginId;
        // 小程序
        Map<String, MiniAccount> miniAccountMap = wxConfig.getMiniAccount();
        for (String appid : miniAccountMap.keySet()) {
            MiniAccount miniAccount = miniAccountMap.get(appid);
            secret = miniAccount.getSecret();
            remark = miniAccount.getRemark();
            type = miniAccount.getType();
            status = miniAccount.getStatus();
            mail = miniAccount.getMail();
            loginPwd = miniAccount.getLoginPwd();
            orginId = miniAccount.getOrginId();
            getToken(appid, secret, remark, type, status, mail, loginPwd, orginId);
        }
        // 服务号
        Map<String, SerAccount> serAccountMap = wxConfig.getSerAccount();
        for (String appid : serAccountMap.keySet()) {
            SerAccount serAccount = serAccountMap.get(appid);
            secret = serAccount.getSecret();
            remark = serAccount.getRemark();
            type = serAccount.getType();
            status = serAccount.getStatus();
            mail = serAccount.getMail();
            loginPwd = serAccount.getLoginPwd();
            orginId = serAccount.getOrginId();
            getToken(appid, secret, remark, type, status, mail, loginPwd, orginId);
        }
        // 订阅号
        Map<String, SubAccount> subAccountMap = wxConfig.getSubAccount();
        for (String appid : subAccountMap.keySet()) {
            SubAccount subAccount = subAccountMap.get(appid);
            secret = subAccount.getSecret();
            remark = subAccount.getRemark();
            type = subAccount.getType();
            status = subAccount.getStatus();
            mail = subAccount.getMail();
            loginPwd = subAccount.getLoginPwd();
            orginId = subAccount.getOrginId();
            getToken(appid, secret, remark, type, status, mail, loginPwd, orginId);
        }
    }

    /**
     * 向微信服务器请求token数据
     *
     * @param appid    公众号APPID
     * @param secret   公众号密钥
     * @param remark   公众号备注名称
     * @param type     公众号类型
     * @param status   公众号状态
     * @param mail     公众号登录邮箱
     * @param loginPwd 公众号登录密码
     * @param orginId  公众号原始ID
     */
	private void getToken(String appid, String secret, String remark, String type,
                          String status, String mail, String loginPwd, String orginId) {
        String url = String.format(WxMiniConstants.GET_ACCESS_TOKEN_URL_TEMPLATE, appid, secret);
        ResponseEntity<String> responseEntity = restTemplateUtil.getReqByUrl(url, String.class);
        String result = responseEntity.getBody();
        WxGetAccessTokenResp wxGetAccessTokenResp = JSONObject.parseObject(result, WxGetAccessTokenResp.class);
        log.info("远程请求token = {}", JSONObject.toJSONString(wxGetAccessTokenResp));
        // 封装第三方token信息对象
        ThirtTokenInfo thirtTokenInfo = new ThirtTokenInfo();
        thirtTokenInfo.setAppid(appid);
        thirtTokenInfo.setSecret(secret);
        thirtTokenInfo.setStatus(status);
        thirtTokenInfo.setRemark(remark);
        thirtTokenInfo.setToken(wxGetAccessTokenResp.getAccessToken());
        thirtTokenInfo.setType(type);
        thirtTokenInfo.setMail(mail);
        thirtTokenInfo.setLoginPwd(loginPwd);
        thirtTokenInfo.setOrginId(orginId);
        // 新增或更新第三方token信息
        Integer update = thirtTokenInfoService.updateThirtTokenInfo(thirtTokenInfo);
        if (null == update || update < 1) {
            log.error("保存第三方会话信息失败: {}", JSONObject.toJSONString(thirtTokenInfo));
        }
    }
}
