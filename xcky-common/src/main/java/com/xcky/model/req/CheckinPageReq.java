package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 未签到列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CheckinPageReq extends PageReq {
    /**
     * 微信应用ID
     */
    private String appid;
    /************ 非必填字段 ****************/
    /**
     * 微信OPENID
     */
    private String openid;
    /**
     * 签到日期
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
}
