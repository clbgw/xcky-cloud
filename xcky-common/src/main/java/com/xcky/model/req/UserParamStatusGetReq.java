package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取参数状态信息请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserParamStatusGetReq {
    /**
     * 参数类型
     */
    private String type;
    /**
     * 用户ID
     */
    private Long userId;
}
