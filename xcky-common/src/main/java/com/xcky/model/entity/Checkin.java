package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 签到记录实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class Checkin {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 程序appid
     */
    private String appid;
    /**
     * 签到用户openid
     */
    private String openid;
    /**
     * 订阅时间
     */
    private Date subTime;
    /**
     * 签到日期字符串(格式yyyyMMdd)
     */
    private String checkinDate;
    /**
     * 签到状态<br>
     * {@link com.xcky.enums.CheckinStatusEnum}
     */
    private String checkinStatus;
    /**
     * 发送订阅消息状态<br>
     * {@link com.xcky.enums.CheckinSendStatusEnum}
     */
    private String sendStatus;
    /**
     * 发送订阅消息时间
     */
    private Date sendTime;
    /**
     * 签到时间
     */
    private Date checkinTime;
    /**
     * 发送订阅消息失败的状态码
     */
    private String sendErrCode;
    /**
     * 最后更新时间
     */
    private Date updateTime;
}
