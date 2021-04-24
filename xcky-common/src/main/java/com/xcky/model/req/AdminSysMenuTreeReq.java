package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取菜单树请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class AdminSysMenuTreeReq {
    /**
     * 程序APPID
     */
    private String appid;
}
