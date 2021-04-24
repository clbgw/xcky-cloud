package com.xcky.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 源日志实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SourceLog {
    /**
     * 公众号APPID
     */
    @JsonProperty(value = "a")
    private String appid = "";
    /**
     * 页面ID
     */
    @JsonProperty(value = "p")
    private Long pageId = 0L;
    /**
     * openid
     */
    @JsonProperty(value = "o")
    private String openid = "";
    /**
     * 微信unionId
     */
    @JsonProperty(value = "u")
    private String unionId = "";
    /**
     * 用户ID
     */
    @JsonProperty(value = "ui")
    private Long userId = 0L;


    /**
     * 日志级别,info,warn,error
     */
    @JsonProperty(value = "l")
    private String level = "INFO";
    /**
     * 日志主要内容
     */
    @JsonProperty(value = "m")
    private String msg = "";
    /**
     * 日志事件ID
     */
    @JsonProperty(value = "lt")
    private Long logType = 0L;
    /*********** 附加属性 ***********/
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 记录日期
     */
    private Date recordDate;

    /**
     * 记录日志用字符串
     *
     * @return 对象对应的数据
     */
    public String toLogString() {
        return "appid=" + appid + ",pageId=" + pageId + ",openid=" + openid
                + ",userId=" + userId + ",log_type=" + logType + ",level=" + level + ",msg=" + msg;
    }
}
