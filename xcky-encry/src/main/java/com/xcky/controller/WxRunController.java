package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.UserInfo;
import com.xcky.model.req.WxRunDecryReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.WxRunStepInfoVo;
import com.xcky.model.vo.WxRunStepVo;
import com.xcky.model.vo.WxWaterMarkVo;
import com.xcky.service.UserInfoService;
import com.xcky.service.UserStepInfoService;
import com.xcky.util.ResponseUtil;
import com.xcky.util.encry.AesUtil;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信运动控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class WxRunController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserStepInfoService userStepInfoService;
    
    /**
     * 解密微信运动数据
     *
     * @param wxRunDecryReq 微信运动解密请求参数
     * @return 微信运动数据
     */
    @PostMapping("/wxRun/decry")
    public R getWxRunDecry(@RequestBody WxRunDecryReq wxRunDecryReq) {
        String openid = wxRunDecryReq.getOpenid();
        UserInfo userInfo = userInfoService.getUserInfoByOpenid(openid);
        if(null == userInfo) {
            throw new BizException(ResponseEnum.USER_NOT_EXIST,null);
        }
        String sessionKey = userInfo.getSessionKey();
        if(StringUtils.isEmpty(sessionKey)) {
            throw new BizException(ResponseEnum.USER_SESSION_KEY_IS_NOT_EXIST,null);
        }

        String encryData = wxRunDecryReq.getEncryData();
        String iv = wxRunDecryReq.getIv();
        // 数据解密
        String result = AesUtil.decode(encryData, iv, sessionKey);
        WxRunStepVo wxRunStepVo = JSONObject.parseObject(result,WxRunStepVo.class);
        WxWaterMarkVo waterMarkVo = wxRunStepVo.getWatermark();
        String userAppid = userInfo.getAppId();
        String waterMarkAppid = waterMarkVo.getAppid();
        if (("" + userAppid).equals(waterMarkAppid)) {
            List<WxRunStepInfoVo> wxRunStepInfoVos = wxRunStepVo.getStepInfoList();
            if(null == wxRunStepInfoVos || wxRunStepInfoVos.size() < 1) {
                return ResponseUtil.fail(ResponseEnum.NO_DATA);
            }
            new Thread(()->{
                // 记录用户的微信步数
                Date nowDate = new Date();
                userStepInfoService.batchSaveStepInfos(wxRunStepInfoVos,openid,nowDate);
            }).start();
            return ResponseUtil.ok(wxRunStepVo);
        } else {
            return ResponseUtil.fail(ResponseEnum.WATER_MARK_ERROR);
        }
    }
}
