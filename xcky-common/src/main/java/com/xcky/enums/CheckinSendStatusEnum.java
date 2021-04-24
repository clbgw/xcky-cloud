package com.xcky.enums;

import lombok.Getter;

/**
 * 签到消息订阅发送状态枚举接口<br>
 *  <ul>
 *      <li>0-已订阅未发送</li>
 *      <li>1-已订阅已成功发送</li>
 *      <li>2-已订阅发送失败</li>
 *      <li>3-未订阅</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum CheckinSendStatusEnum {
    
    /**
     * 已订阅未发送
     */
    NO_SEND("0", "已订阅未发送"),
    /**
     * 已订阅已成功发送
     */
    SEND_SUCCESS("1", "已订阅已成功发送"),
    /**
     * 已订阅发送失败
     */
    SEND_FAIL("2", "已订阅发送失败"),
    /**
     * 未订阅
     */
    NO_SUB("3", "未订阅"),
    ;
    
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
    
    CheckinSendStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
