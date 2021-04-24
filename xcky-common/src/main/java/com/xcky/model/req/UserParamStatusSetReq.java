package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 设置参数状态信息请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserParamStatusSetReq {
    /**
     * 参数类型
     */
    private String type;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 当前状态
     */
    private String status;
}
