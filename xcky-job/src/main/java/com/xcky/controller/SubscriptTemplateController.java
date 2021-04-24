package com.xcky.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xcky.config.SubscriptTemplateConfig;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.entity.UserInfo;
import com.xcky.model.resp.R;
import com.xcky.model.wx.WxBaseResp;
import com.xcky.service.CheckinService;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.service.UserInfoService;
import com.xcky.util.Constants;
import com.xcky.util.DateUtil;
import com.xcky.util.ResponseUtil;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.SubTempUtil;
import com.xcky.util.WxMiniConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 订阅模板控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class SubscriptTemplateController {
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private ThirtTokenInfoService thirtTokenInfoService;
    @Autowired
    private SubscriptTemplateConfig subscriptTemplateConfig;
    @Autowired
    private SubTempUtil subTempUtil;
    @Autowired
    private CheckinService checkinService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 发送签到提醒
     *
     * @param openid 用户OPENID
     * @return
     */
    @SuppressWarnings("unchecked")
	@GetMapping("/sendSubTempForCheckin")
    public R sendSubTempForCheckin(String openid) {
        Map<String, Object> config = subscriptTemplateConfig.getCheckinSub();
        String appid = "" + config.get(Constants.FIELD_APPID);
        ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByAppid(appid);
        if (null == thirtTokenInfo) {
            throw new BizException(ResponseEnum.TOKEN_IS_NOT_EXIST, null);
        }
        // 统计该用户累计签到数量
        Integer num = checkinService.getCheckinNum(appid, openid);
        String token = thirtTokenInfo.getToken();
        String url = String.format(WxMiniConstants.POST_SEND_SUBSCRIPT_TEMPLATE_URL_TEMPLATE, token);
        String nowDateStr = DateUtil.getTimeStrByDate(new Date(), DateUtil.DATE_CHAR_PATTERN);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        paramMap.put("nowDate", nowDateStr);
        Map<String, Object> parameterMap = subTempUtil.getSubData(config, openid, paramMap);
        ResponseEntity<Object> responseEntity = restTemplateUtil.postReqByJson(url, parameterMap, Object.class);
        String result = getRespResult(responseEntity);
        return ResponseUtil.ok(result);
    }

    /**
     * 发送签到成功提醒
     *
     * @param openid 用户OPENID
     * @return
     */
    @SuppressWarnings("unchecked")
	@GetMapping("/sendSubTempForCheckinOk")
    public R sendSubTempForCheckinOk(String openid) {
        UserInfo userInfo = userInfoService.getUserInfoByOpenid(openid);
        if (null == userInfo) {
            throw new BizException(ResponseEnum.USER_NOT_EXIST, null);
        }
        Map<String, Object> config = subscriptTemplateConfig.getCheckinOk();
        String appid = "" + config.get(Constants.FIELD_APPID);
        ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByAppid(appid);
        if (null == thirtTokenInfo) {
            throw new BizException(ResponseEnum.TOKEN_IS_NOT_EXIST, null);
        }
        String nickName = userInfo.getNickName();
        String token = thirtTokenInfo.getToken();
        String url = String.format(WxMiniConstants.POST_SEND_SUBSCRIPT_TEMPLATE_URL_TEMPLATE, token);
        String nowTimeStr = DateUtil.getTimeStrByDate(new Date(), DateUtil.DATE_TIME_PATTERN);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("nickName", nickName);
        paramMap.put("nowTime", nowTimeStr);
        Map<String, Object> parameterMap = subTempUtil.getSubData(config, openid, paramMap);
        ResponseEntity<Object> responseEntity = restTemplateUtil.postReqByJson(url, parameterMap, Object.class);
        String result = getRespResult(responseEntity);
        return ResponseUtil.ok(result);
    }

    /**
     * 处理发送结果
     *
     * @param responseEntity
     * @return
     */
    private String getRespResult(ResponseEntity<Object> responseEntity) {
        if (Constants.TWO_HUNDRED == responseEntity.getStatusCodeValue()) {
            // 处理发送结果
            WxBaseResp wxBaseResp = JSONObject.parseObject(JSONObject.toJSONString(responseEntity.getBody()), WxBaseResp.class);
            if (0 == wxBaseResp.getErrcode()) {
                // 发送成功
                return "0@@ok";
            } else {
                // 发送错误,并记录错误码
                return wxBaseResp.getErrcode() + "@@" + wxBaseResp.getErrmsg();
            }
        } else {
            // 记录发送结果
            log.error(JSONObject.toJSONString(responseEntity));
            return "-1@@" + responseEntity.getStatusCodeValue();
        }
    }

}
