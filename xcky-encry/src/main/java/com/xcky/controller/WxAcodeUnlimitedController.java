package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.config.WxMiniQrcodeConfig;
import com.xcky.enums.ResponseEnum;
import com.xcky.model.entity.JlQrcodeCreate;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.req.JlQrcodeCreateReq;
import com.xcky.model.resp.R;
import com.xcky.service.JlQrcodeCreateService;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.util.ResponseUtil;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.WxMiniConstants;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序码控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class WxAcodeUnlimitedController {
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private ThirtTokenInfoService thirtTokenInfoService;
    @Autowired
    private JlQrcodeCreateService jlQrcodeCreateService;
    @Autowired
    private WxMiniQrcodeConfig wxMiniQrcodeConfig;

    @PostMapping("/mini/qrcode/create")
    public R qrcodeCreate(@RequestBody JlQrcodeCreateReq jlQrcodeCreateReq) {
        String appid = jlQrcodeCreateReq.getAppid();
        Long jlId = jlQrcodeCreateReq.getJlId();
        Integer isQuery = jlQrcodeCreateReq.getIsQuery();
        String scene = "" + jlId;
        JlQrcodeCreate jlQrcodeCreate = new JlQrcodeCreate();
        Date nowDate = new Date();
        Map<String, Object> insertMap = new HashMap<>(4);
        insertMap.put("appid", appid);
        insertMap.put("scene", scene);
        List<JlQrcodeCreate> list = jlQrcodeCreateService.getJlQrcodeCreateByMap(insertMap);
        if (null != list && list.size() > 0) {
            if (isQuery == 1) {
                return ResponseUtil.ok(list.get(0).getPicPath());
            }
            jlQrcodeCreate.setId(list.get(0).getId());
        } else {
            jlQrcodeCreate.setCreateTime(nowDate);
        }
        // 查询是否已经生成过
        Map<String, Object> tokenMap = new HashMap<>(4);
        tokenMap.put("appid", appid);
        ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByMap(tokenMap);
        if (null == thirtTokenInfo) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        String accessToken = thirtTokenInfo.getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return ResponseUtil.fail(ResponseEnum.SERVER_ERROR);
        }
        String fileName = "qrcodea_" + appid + "_" + jlId + ".png";
        //String path = wxMiniQrcodeConfig.getDestPath() + fileName ;
        String path = wxMiniQrcodeConfig.getDestPath() + fileName;
        String picPath = wxMiniQrcodeConfig.getUrlPrefix() + fileName;
        String url = String.format(WxMiniConstants.POST_WXQRCODE_UNLIMITED_URL_TEMPLATE, accessToken);
        Map<String, Object> params = new HashMap<>(4);
        params.put("scene", scene);
        params.put("page", wxMiniQrcodeConfig.getPage());
        String result = restTemplateUtil.getWxMiniQrcode(url, JSONObject.toJSONString(params), path);
        // 有生成图片
        if (result.equals(path)) {
            // 记录下生成图片的记录
            jlQrcodeCreate.setAppid(appid);
            jlQrcodeCreate.setUpdateTime(nowDate);
            jlQrcodeCreate.setDestPath(path);
            jlQrcodeCreate.setFileName(fileName);
            jlQrcodeCreate.setPicPath(picPath);
            jlQrcodeCreate.setScene(scene);
            Integer updateResult = jlQrcodeCreateService.updateJlQrcodeCreate(jlQrcodeCreate);
            if (null == updateResult || updateResult < 1) {
                log.error("更新失败:" + JSONObject.toJSONString(jlQrcodeCreate));
            }
            return ResponseUtil.ok(picPath);
        }
        return ResponseUtil.ok(result);
    }
}
