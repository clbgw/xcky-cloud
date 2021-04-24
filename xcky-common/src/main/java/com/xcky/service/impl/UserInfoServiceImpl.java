package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.convert.UserInfoConvert;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.UserInfoMapper;
import com.xcky.model.entity.UserInfo;
import com.xcky.model.req.AdminUserInfoListReq;
import com.xcky.model.req.UserInfoUpdateReq;
import com.xcky.model.req.UserUpdateBaseSubInfoReq;
import com.xcky.model.vo.UserInfoVo;
import com.xcky.model.vo.UserStatDateNumVo;
import com.xcky.model.wx.WxCode2sessionResp;
import com.xcky.service.UserInfoService;
import com.xcky.util.Constants;
import com.xcky.util.EntityUtil;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.StringUtil;
import com.xcky.util.WxMiniConstants;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户信息服务接口实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @Override
    public boolean isExistUser(Long userId) {
        boolean isExist = false;
        if (null == userId) {
            return isExist;
        }
        List<Long> userIds = userInfoMapper.selectUserIdByKey(userId);
        if (null != userIds && userIds.size() > 0) {
            isExist = true;
        }
        return isExist;
    }
    
    @Override
    public void judgeUserExist(Long userId) {
        boolean isExist = false;
        if (null != userId) {
            isExist = isExistUser(userId);
        }
        if (!isExist) {
            log.info("用户不存在,用户ID = 【" + userId + "】");
            throw new BizException(ResponseEnum.USER_NOT_EXIST, null);
        }
    }
    
    @Override
    public UserInfo getUserInfoByOpenid(String openid) {
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoByOpenid(openid);
        if (null == userInfos || userInfos.size() < 1) {
            return null;
        }
        return userInfos.get(0);
    }
    
    @Override
    public Integer updateUserInfo(UserInfoUpdateReq userInfoUpdateReq) {
        // 将更新请求参数转化为实体类
        UserInfo userInfo = UserInfoConvert.getUserInfoByUpdateReq(userInfoUpdateReq);
        if (null == userInfo) {
            return 0;
        }
        Long userId = userInfoUpdateReq.getId();
        boolean isInsert = false;
        if (null == userId || userId < 1) {
            isInsert = true;
        } else {
            // 统计同一个主键的用户信息数量
            Integer countResult = userInfoMapper.selectCountUserByKey(userId);
            if (null == countResult || countResult < 1) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        Integer updateResult = 0;
        if (isInsert) {
            // 新增用户基本信息
            userInfo.setCreateTime(nowDate);
            userInfo.setUpdateTime(nowDate);
            updateResult = userInfoMapper.insertUserInfo(userInfo);
        } else {
            // 更新用户基本信息
            userInfo.setUpdateTime(nowDate);
            updateResult = userInfoMapper.updateUserInfo(userInfo);
        }
        return updateResult;
    }
    
    @Override
    public void updateUserInfoBySubInfoReq(UserUpdateBaseSubInfoReq userUpdateBaseSubInfoReq) {
        Integer updateResult = userInfoMapper.updateUserInfoBySubInfoReq(userUpdateBaseSubInfoReq);
        if (null == updateResult || updateResult < 1) {
            log.info("更新用户的微信基本信息失败,基本信息 = 【" + JSONObject.toJSONString(userUpdateBaseSubInfoReq) + "】");
            throw new BizException(ResponseEnum.UPDATE_ERROR, null);
        }
    }
    
    @Override
    public Integer updateUserInfoByWxCode2sessionResp(WxCode2sessionResp wxCode2sessionResp) {
        String openid = wxCode2sessionResp.getOpenid();
        if(StringUtils.isEmpty(openid)) {
            return 0;
        }
        boolean isInsert = false;
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoByOpenid(openid);
        if (null == userInfos || userInfos.size() < 1) {
            isInsert = true;
        }
        Date nowDate = new Date();
        UserInfo userInfo;
        if (isInsert) {
            userInfo = new UserInfo();
            userInfo.setAppId(wxCode2sessionResp.getAppid());
            userInfo.setOpenid(openid);
            userInfo.setUnionId(wxCode2sessionResp.getUnionid());
            userInfo.setSessionKey(wxCode2sessionResp.getSessionKey());
            userInfo.setCreateTime(nowDate);
            userInfo.setUpdateTime(nowDate);
            return userInfoMapper.insertUserInfo(userInfo);
        } else {
            userInfo = userInfos.get(0);
            userInfo.setUpdateTime(nowDate);
            userInfo.setSessionKey(wxCode2sessionResp.getSessionKey());
            userInfo.setUnionId(wxCode2sessionResp.getUnionid());
            userInfo.setAppId(wxCode2sessionResp.getAppid());
            return userInfoMapper.updateUserInfo(userInfo);
        }
    }

    @Override
    public String getUserInfoAdminFlag(String openid) {
        return userInfoMapper.selectIsAdminByOpenid(openid);
    }

    @Override
    public List<UserStatDateNumVo> getUserStatByAppid(String appid) {
        return userInfoMapper.selectUserStatByAppid(appid);
    }

    @Override
    public PageInfo<UserInfoVo> getUserInfoListByReq(AdminUserInfoListReq adminUserInfoListReq) {
        Integer page = adminUserInfoListReq.getPage();
        Integer size = adminUserInfoListReq.getSize();

        Map<String,Object> map = EntityUtil.entityToMap(adminUserInfoListReq);
        PageInfo<UserInfoVo> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            userInfoMapper.selectUserInfoByMap(map);
        });
        return pageInfo;
    }
}
