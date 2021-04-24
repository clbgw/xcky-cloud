package com.xcky.model.req;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 签到请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CheckinSaveReq {
    /**
     * 公众号APPID
     */
    private String appid;
    /**
     * 公众号OPENID
     */
    private String openid;
    /**
     * 签到时间
     */
    private Date checkinTime;
}
