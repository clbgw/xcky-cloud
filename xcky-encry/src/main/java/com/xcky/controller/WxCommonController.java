package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.req.CheckMsgSecReq;
import com.xcky.model.resp.R;
import com.xcky.model.wx.WxBaseResp;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.util.ResponseUtil;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.WxMiniConstants;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信通用控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class WxCommonController {
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private ThirtTokenInfoService thirtTokenInfoService;

    /**
     * 检测内容是否敏感
     *
     * @param checkMsgSecReq 检测内容是否敏感请求参数
     * @return 基本响应对象
     */
    @PostMapping("/checkMsgSec")
    public R checkMsgSec(@RequestBody CheckMsgSecReq checkMsgSecReq) {
        String appid = checkMsgSecReq.getAppid();
        String msg = checkMsgSecReq.getMsg();

        ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByAppid(appid);
        if (null == thirtTokenInfo) {
            log.error("请在配置文件中配置小程序的基本信息: appid = " + appid);
            throw new BizException(ResponseEnum.CONFIG_ERROR, "请配置节点【wx.miniAccount:】");
        }
        String url = String.format(WxMiniConstants.POST_MSG_SEC_CHECK_TEMPLATE, thirtTokenInfo.getToken());
        Map<String, Object> map = new HashMap<>(2);
        map.put("content", msg);
        ResponseEntity<String> responseEntity = restTemplateUtil.postReqByJson(url, map, String.class);
        String resultBody = responseEntity.getBody();
        WxBaseResp wxBaseResp = JSONObject.parseObject(resultBody, WxBaseResp.class);
        if (wxBaseResp.getErrcode() != 0) {
            log.error("检测结果:" + resultBody);
            throw new BizException(ResponseEnum.MSG_DANGER, null);
        }
        return ResponseUtil.ok();
    }
}
