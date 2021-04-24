package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.Checkin;
import com.xcky.model.req.CheckinPageReq;

import java.util.Date;

/**
 * 签到服务接口
 *
 * @author lbchen
 */
public interface CheckinService {
    /**
     * 签到
     *
     * @param appid       公众号APPID
     * @param openid      公众号OPENID
     * @param checkinDate 签到日期(格式:yyyyMMdd)
     * @param checkinTime 签到时间
     */
    void saveCheckinRecord(String appid, String openid, String checkinDate, Date checkinTime);

    /**
     * 订阅明天的签到
     *
     * @param appid   公众号APPID
     * @param openid  微信OPENID
     * @param subTime 订阅时间
     */
    void saveCheckinForTomorrow(String appid, String openid, Date subTime);

    /**
     * 更新发送状态
     *
     * @param id          主键ID
     * @param sendTime    发送订阅时间
     * @param sendStatus  发送状态
     * @param sendErrCode 发送订阅消息失败的状态码
     */
    void updateCheckinSendStatus(Long id, Date sendTime, String sendStatus, String sendErrCode);

    /**
     * 根据主键获取签到记录
     *
     * @param appid       微信APPID
     * @param openid      微信OPENID
     * @param checkinDate 签到日期
     * @return 签到记录
     */
    Checkin getCheckinByKey(String appid, String openid, String checkinDate);

    /**
     * 根据主键ID获取签到记录
     *
     * @param id 主键ID
     * @return 签到记录
     */
    Checkin getCheckinById(Long id);

    /**
     * 根据分页请求参数获取签到记录
     *
     * @param checkinPageReq 签到分页请求参数
     * @return 签到分页请求参数
     */
    PageInfo<Checkin> getCheckinByReq(CheckinPageReq checkinPageReq);

    /**
     * 统计用户签到天数
     *
     * @param appid  微信APPID
     * @param openid 微信OPENID
     * @return 签到记录数
     */
    Integer getCheckinNum(String appid, String openid);
}
