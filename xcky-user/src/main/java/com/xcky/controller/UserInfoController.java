package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xcky.annotation.LoginAuthAnnotation;
import com.xcky.config.ToutiaoMiniUrlConstant;
import com.xcky.config.WxMiniUrlConstant;
import com.xcky.enums.AccountTypeEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.entity.UserInfo;
import com.xcky.model.req.AdminSimpleReq;
import com.xcky.model.req.AdminUserInfoListReq;
import com.xcky.model.req.UserGetWxOpenidReq;
import com.xcky.model.req.UserInfoJudgeAdminReq;
import com.xcky.model.req.UserInfoUpdateReq;
import com.xcky.model.req.UserUpdateBaseInfoReq;
import com.xcky.model.req.UserUpdateBaseSubInfoReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.UserInfoVo;
import com.xcky.model.vo.UserStatDateNumVo;
import com.xcky.model.wx.WxCode2sessionResp;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.service.UserInfoService;
import com.xcky.util.Constants;
import com.xcky.util.ResponseUtil;
import com.xcky.util.RestTemplateUtil;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户基本信息控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private ThirtTokenInfoService thirtTokenInfoService;

    /**
     * 查询用户列表
     *
     * @param adminUserInfoListReq 后台请求用户信息列表请求参数
     * @return 基本响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/getUserInfoList")
    public R getUserInfoList(@RequestBody AdminUserInfoListReq adminUserInfoListReq) {
        if (null == adminUserInfoListReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 根据请求参数查询用户列表
        PageInfo<UserInfoVo> pageInfo = userInfoService.getUserInfoListByReq(adminUserInfoListReq);
        return ResponseUtil.page(pageInfo);
    }

    /**
     * 查询用户日期统计数据列表
     *
     * @param adminSimpleReq 简单的管理员请求参数
     * @return 基本响应对象
     */
    @PostMapping("/getUserDateStat")
    public R getUserDateStat(@RequestBody AdminSimpleReq adminSimpleReq) {
        if (null == adminSimpleReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        String appid = adminSimpleReq.getAppid();
        // 查询用户日期数量统计列表
        List<UserStatDateNumVo> list = userInfoService.getUserStatByAppid(appid);
        return ResponseUtil.ok(list);
    }

    /**
     * 判断用户是否管理员
     *
     * @param userInfoJudgeAdminReq 用户是否管理员请求参数
     * @return 基本响应对象
     */
    @PostMapping("/judgeAdmin")
    public R judgeAdmin(@RequestBody UserInfoJudgeAdminReq userInfoJudgeAdminReq) {
        if (null == userInfoJudgeAdminReq) {
            log.info("入参为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        String openid = userInfoJudgeAdminReq.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            log.error("openid为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 查询用户表中的is_admin字段值
        String isAdmin = userInfoService.getUserInfoAdminFlag(openid);
        if (!StringUtils.isEmpty(isAdmin) && Constants.STR_ONE.equals(isAdmin)) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail(ResponseEnum.NO_DATA);
        }
    }

    /**
     * 用户登录
     *
     * @param userInfoUpdateReq 用户基本信息更新请求参数
     * @return 基本响应对象
     */
    @PostMapping("/userLogin")
    public R userLogin(@RequestBody UserInfoUpdateReq userInfoUpdateReq) {
        if (null == userInfoUpdateReq) {
            log.info("入参为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        String openid = userInfoUpdateReq.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            log.error("openid为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 查询用户基本信息
        UserInfo userInfo = userInfoService.getUserInfoByOpenid(openid);
        if (null == userInfo) {
            Integer updateUserResult = userInfoService.updateUserInfo(userInfoUpdateReq);
            if (null == updateUserResult || updateUserResult < 1) {
                log.error("用户登录失败");
                return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
            }
            // 查询用户基本信息
            userInfo = userInfoService.getUserInfoByOpenid(openid);
        }
        return ResponseUtil.ok(userInfo);
    }

    /**
     * 用户基本信息绑定
     *
     * @param userUpdateBaseInfoReq 用户基本信息更新请求参数
     * @return 基本响应对象
     */
    @PostMapping("/userUpdateBaseInfo")
    public R userUpdateBaseInfo(@RequestBody UserUpdateBaseInfoReq userUpdateBaseInfoReq) {
        if (null == userUpdateBaseInfoReq) {
            log.info("入参为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        log.info("用户绑定的基本信息:" + JSONObject.toJSONString(userUpdateBaseInfoReq));
        // 用户基本信息
        UserUpdateBaseSubInfoReq userUpdateBaseSubInfoReq = userUpdateBaseInfoReq.getUserInfo();
        if (null == userUpdateBaseSubInfoReq) {
            log.info("用户基本信息主键不全: userInfo为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_NOT_ENOUGH);
        }
        Long userId = userUpdateBaseSubInfoReq.getUserId();
        String openid = userUpdateBaseSubInfoReq.getOpenid();
        if (null == userId || StringUtils.isEmpty(openid)) {
            log.info("用户基本信息主键不全: 【userId=" + userId + ",openid=" + openid + "】");
            return ResponseUtil.fail(ResponseEnum.PARAM_NOT_ENOUGH);
        }
        String nickName = userUpdateBaseSubInfoReq.getNickName();
        if (StringUtils.isEmpty(nickName.trim())) {
            userUpdateBaseSubInfoReq.setNickName("昵称为空");
        }
        Date nowDate = new Date();
        userUpdateBaseSubInfoReq.setUpdateTime(nowDate);
        userInfoService.updateUserInfoBySubInfoReq(userUpdateBaseSubInfoReq);
        return ResponseUtil.ok();
    }

    /**
     * 获取用户OPENID
     *
     * @param userGetWxOpenidReq 获取微信用户OPENID请求参数
     * @return 基本响应对象
     */
    @PostMapping("/getWxMiniOpenid")
    public R getWxOpenid(@RequestBody UserGetWxOpenidReq userGetWxOpenidReq) {
        String appid = userGetWxOpenidReq.getAppid();
        ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByAppid(appid);
        if (null == thirtTokenInfo) {
            log.error("请在配置文件中配置小程序的基本信息: appid = " + appid);
            throw new BizException(ResponseEnum.CONFIG_ERROR, "请配置节点【wx.miniAccount:】");
        }
        String secret = thirtTokenInfo.getSecret();
        if (StringUtils.isEmpty(secret)) {
            log.error("请在配置文件中配置小程序的基本信息secret节点: appid = " + appid);
            throw new BizException(ResponseEnum.CONFIG_ERROR, "请配置节点【wx.miniAccount." + appid + ".secret:】");
        }
        String code = userGetWxOpenidReq.getCode();
        String appType = userGetWxOpenidReq.getAppType();
        String code2sessionUriStr;
        String code2sessionUrl;
        // 头条小程序
        if (!StringUtils.isEmpty(appType) && AccountTypeEnum.TOUTIAO.getCode().equals(appType)) {
            code2sessionUriStr = ToutiaoMiniUrlConstant.GET_AUTH_CODE2SESSION_URI;
            code2sessionUrl = String.format(code2sessionUriStr, appid, secret);
            if (!StringUtils.isEmpty(code)) {
                code2sessionUrl += "&code=" + code;
            }
            String anonymousCode = userGetWxOpenidReq.getAnonymousCode();
            if (!StringUtils.isEmpty(anonymousCode)) {
                code2sessionUrl += "&anonymous_code=" + anonymousCode;
            }
        }
        // 微信小程序
        else {
            code2sessionUriStr = WxMiniUrlConstant.GET_AUTH_CODE2SESSION_URI;
            code2sessionUrl = String.format(code2sessionUriStr, appid, secret, code);
        }
        // 发送请求openid的请求
        @SuppressWarnings("unchecked")
        ResponseEntity<String> responseEntity = restTemplateUtil.getReqByUrl(code2sessionUrl, String.class);
        String bodyStr = responseEntity.getBody();
        WxCode2sessionResp wxCode2sessionResp = JSONObject.parseObject(bodyStr, WxCode2sessionResp.class);
        if (null != wxCode2sessionResp) {
            // 增加公众号唯一标识
            wxCode2sessionResp.setAppid(appid);
            Integer updateResult = userInfoService.updateUserInfoByWxCode2sessionResp(wxCode2sessionResp);
            if (updateResult < 1) {
                log.error("新增或更新用户基本信息失败:" + JSONObject.toJSONString(wxCode2sessionResp));
            }
        }
        wxCode2sessionResp.setSessionKey(null);
        return ResponseUtil.ok(wxCode2sessionResp);
    }

}
