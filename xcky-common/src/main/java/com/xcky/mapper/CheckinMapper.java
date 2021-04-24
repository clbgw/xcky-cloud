package com.xcky.mapper;

import com.xcky.model.entity.Checkin;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 签到对象映射接口
 *
 * @author lbchen
 */
public interface CheckinMapper {
    /**
     * 新增签到记录
     *
     * @param checkin 签到记录
     * @return 新增行数
     */
    Integer insertCheckin(Checkin checkin);

    /**
     * 更新签到记录
     *
     * @param checkin 签到记录
     * @return 更新行数
     */
    Integer updateCheckin(Checkin checkin);

    /**
     * 根据map条件查询签到记录列表
     *
     * @param map map条件
     * @return 签到记录表
     */
    List<Checkin> selectCheckinByMap(Map<String, Object> map);

    /**
     * 统计用户签到记录数
     *
     * @param appid  微信APPID
     * @param openid 微信OPENID
     * @return 签到记录数
     */
    Integer selectCheckinNum(@Param("appid") String appid,@Param("openid") String openid);
}
