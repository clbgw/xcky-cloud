package com.xcky.mapper;

import com.xcky.model.entity.UserInfo;
import com.xcky.model.req.UserUpdateBaseSubInfoReq;
import com.xcky.model.vo.UserInfoVo;
import com.xcky.model.vo.UserStatDateNumVo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息数据访问接口
 *
 * @author lbchen
 */
public interface UserInfoMapper {
    /**
     * 根据用户主键ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息ID
     */
    List<Long> selectUserIdByKey(@Param("id") Long id);

    /**
     * 查询用户剩余积分数
     *
     * @param userId             用户ID
     * @param userIntegralAmount 预扣的积分数
     * @return 剩余积分数
     */
    BigDecimal selectUserIntegeral(@Param("userId") Long userId, @Param("integral") BigDecimal userIntegralAmount);

    /**
     * 更新用户积分数
     *
     * @param userId             用户ID
     * @param userIntegralAmount 扣减的积分数
     * @return 剩余积分数
     */
    Integer updateUserIntegral(@Param("userId") Long userId, @Param("integral") BigDecimal userIntegralAmount);

    /**
     * 新增用户基本信息
     *
     * @param userInfo 用户基本信息
     * @return 新增行数
     */
    Integer insertUserInfo(UserInfo userInfo);

    /**
     * 更新用户基本信息
     *
     * @param userInfo 用户基本信息
     * @return 更新行数
     */
    Integer updateUserInfo(UserInfo userInfo);

    /**
     * 根据用户主键统计用户数
     *
     * @param userId 用户主键ID
     * @return 用户数量
     */
    Integer selectCountUserByKey(@Param("userId") Long userId);

    /**
     * 根据微信用户openid查询用户基本信息
     *
     * @param openid 微信用户openid
     * @return 用户基本信息
     */
    List<UserInfo> selectUserInfoByOpenid(@Param("openid") String openid);

    /**
     * 更新用户微信基本信息
     *
     * @param userUpdateBaseSubInfoReq 用户微信基本信息请求对象
     * @return 更新行数
     */
    Integer updateUserInfoBySubInfoReq(UserUpdateBaseSubInfoReq userUpdateBaseSubInfoReq);

    /**
     * 查询OPENID是否管理员
     *
     * @param openid 公众号OPENID
     * @return 否管理员字段的值
     */
    String selectIsAdminByOpenid(@Param("openid") String openid);

    /**
     * 查询用户日期数量统计
     *
     * @param appid 公众号ID
     * @return 用户日期数量统计列表
     */
    List<UserStatDateNumVo> selectUserStatByAppid(@Param("appid") String appid);

    /**
     * 根据map条件查询用户信息列表
     *
     * @param map map条件
     * @return 用户信息列表
     */
    List<UserInfoVo> selectUserInfoByMap(Map<String, Object> map);
}
