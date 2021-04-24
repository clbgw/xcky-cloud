package com.xcky.enums;

import lombok.Getter;

/**
 * 签到状态枚举接口<br>
 *  <ul>
 *      <li>0-未签到</li>
 *      <li>1-已签到</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum CheckinStatusEnum {
    
    /**
     * 未签到
     */
    NO("0", "未签到"),
    /**
     * 已签到
     */
    YES("1", "已签到"),
    ;
    
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
    
    CheckinStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
