package com.xcky.schedule;

import com.alibaba.fastjson.JSONObject;
import com.xcky.config.ToutiaoMiniUrlConstant;
import com.xcky.config.WxConfig;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.pojo.MiniTtAccount;
import com.xcky.model.wx.WxGetAccessTokenResp;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.util.RestTemplateUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 头条小程序普通调度类
 * 
 * @author lbchen
 */
@Component
@Slf4j
public class TtCommonJob {
	@Autowired
	private RestTemplateUtil restTemplateUtil;
	@Autowired
	private ThirtTokenInfoService thirtTokenInfoService;
	@Autowired
	private WxConfig wxConfig;

	/**
	 * 同步头条小程序账号
	 */
	@Scheduled(cron = "53 3 2 * * *")
	public void syncTtMiniAccount() {
		if (null == wxConfig) {
			return;
		}
		Map<String, MiniTtAccount> ttMiniAccountMap = wxConfig.getMiniTtAccount();
		if (null == ttMiniAccountMap) {
			return;
		}
		for (String appid : ttMiniAccountMap.keySet()) {
			MiniTtAccount ttMiniAccount = ttMiniAccountMap.get(appid);
			ThirtTokenInfo thirtTokenInfo = new ThirtTokenInfo();
			thirtTokenInfo.setAppid(appid);
			thirtTokenInfo.setRemark(ttMiniAccount.getRemark());
			thirtTokenInfo.setStatus(ttMiniAccount.getStatus());
			thirtTokenInfo.setType(ttMiniAccount.getType());
			Integer update = thirtTokenInfoService.updateThirtTokenInfo(thirtTokenInfo);
			if (null == update || update < 1) {
				log.error("保存头条账号信息失败: {}", JSONObject.toJSONString(thirtTokenInfo));
			}
		}
	}

	/**
	 * 获取头条小程序的token
	 */
	@Scheduled(cron = "30 0/30 * * * *")
	public void getWxToken() {
		log.info("开始获取头条token");
		String secret;
		String remark;
		String type;
		String status;
		// 小程序
		Map<String, MiniTtAccount> miniTtAccountMap = wxConfig.getMiniTtAccount();
		for (String key : miniTtAccountMap.keySet()) {
			MiniTtAccount miniTtAccount = miniTtAccountMap.get(key);
			secret = miniTtAccount.getSecret();
			remark = miniTtAccount.getRemark();
			type = miniTtAccount.getType();
			status = miniTtAccount.getStatus();
			getToken(key, secret, remark, type, status);
		}
	}

	/**
	 * 向头条服务器请求token数据
	 *
	 * @param appid  公众号APPID
	 * @param secret 公众号密钥
	 * @param remark 公众号备注名称
	 * @param type   公众号类型
	 * @param status 公众号状态
	 */
	@SuppressWarnings("unchecked")
	private void getToken(String appid, String secret, String remark, String type, String status) {
		String url = String.format(ToutiaoMiniUrlConstant.GET_ACCESS_TOKEN_URL_TEMPLATE, appid, secret);
		ResponseEntity<String> responseEntity = restTemplateUtil.getReqByUrl(url, String.class);
		String result = responseEntity.getBody();
		WxGetAccessTokenResp wxGetAccessTokenResp = JSONObject.parseObject(result, WxGetAccessTokenResp.class);
		log.info("远程请求头条token = {}", JSONObject.toJSONString(wxGetAccessTokenResp));
		// 封装第三方token信息对象
		ThirtTokenInfo thirtTokenInfo = new ThirtTokenInfo();
		thirtTokenInfo.setAppid(appid);
		thirtTokenInfo.setSecret(secret);
		thirtTokenInfo.setStatus(status);
		thirtTokenInfo.setRemark(remark);
		thirtTokenInfo.setToken(wxGetAccessTokenResp.getAccessToken());
		thirtTokenInfo.setType(type);
		// 新增或更新第三方token信息
		Integer update = thirtTokenInfoService.updateThirtTokenInfo(thirtTokenInfo);
		if (null == update || update < 1) {
			log.error("保存第三方会话信息失败: {}", JSONObject.toJSONString(thirtTokenInfo));
		}
	}
}
