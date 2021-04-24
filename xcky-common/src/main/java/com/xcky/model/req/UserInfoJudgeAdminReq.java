package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 判断用户是否管理员请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserInfoJudgeAdminReq {
    /**
     * 公众号OPENID
     */
    private String openid;
}
