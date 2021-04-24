package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class AdminSysUserPageReq extends PageReq {
    /**
     * 主键ID
     */
    private Long id;

}
