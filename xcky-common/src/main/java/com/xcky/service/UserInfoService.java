package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.UserInfo;
import com.xcky.model.req.AdminUserInfoListReq;
import com.xcky.model.req.UserInfoUpdateReq;
import com.xcky.model.req.UserUpdateBaseSubInfoReq;
import com.xcky.model.vo.UserInfoVo;
import com.xcky.model.vo.UserStatDateNumVo;
import com.xcky.model.wx.WxCode2sessionResp;
import java.util.List;

/**
 * 用户信息服务接口
 *
 * @author lbchen
 */
public interface UserInfoService {
    /**
     * 根据用户ID判断用户信息是否存在
     *
     * @param userId 用户ID
     * @return true-存在,false-不存在
     */
    boolean isExistUser(Long userId);

    /**
     * 判断用户ID是否在用户表中存在
     *
     * @param userId 用户ID
     */
    void judgeUserExist(Long userId);

    /**
     * 查询唯一索引用户基本信息
     *
     * @param openid 微信用户openid
     * @return 用户基本信息
     */
    UserInfo getUserInfoByOpenid(String openid);

    /**
     * 更新用户基本信息
     *
     * @param userInfoUpdateReq 用户基本信息更新请求参数
     * @return 影响行数
     */
    Integer updateUserInfo(UserInfoUpdateReq userInfoUpdateReq);

    /**
     * 更新用户微信基本信息
     *
     * @param userUpdateBaseSubInfoReq 用户微信基本信息请求参数
     */
    void updateUserInfoBySubInfoReq(UserUpdateBaseSubInfoReq userUpdateBaseSubInfoReq);

    /**
     * 新增或更新用户基本信息
     *
     * @param wxCode2sessionResp
     * @return
     */
    Integer updateUserInfoByWxCode2sessionResp(WxCode2sessionResp wxCode2sessionResp);

    /**
     * 查询用户是否管理员字段
     *
     * @param openid 公众号OPENID
     * @return 是否管理员字段
     */
    String getUserInfoAdminFlag(String openid);

    /**
     * 查询用户日期数量统计
     *
     * @param appid 公众号APPID
     * @return 用户日期数量统计列表
     */
    List<UserStatDateNumVo> getUserStatByAppid(String appid);

    /**
     * 查询用户信息列表
     *
     * @param adminUserInfoListReq 后台请求用户信息列表请求参数
     * @return 用户信息列表
     */
    PageInfo<UserInfoVo> getUserInfoListByReq(AdminUserInfoListReq adminUserInfoListReq);
}
