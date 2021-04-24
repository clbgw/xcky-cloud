package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 第三方会话信息分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ThirtTokenInfoPageReq extends PageReq {
    /**
     * 账号类型:wxmini-微信小程序
     */
    private String type;
    /**
     * 账号ID
     */
    private String appid;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态<br>
     * {@link com.xcky.enums.ThirtTokenStatusEnum}
     */
    private String status;
}
