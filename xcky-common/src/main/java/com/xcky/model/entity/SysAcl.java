package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台权限实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysAcl extends BaseBackEntity {
    /**
     * 权限ID
     */
    private Long id;
    /**
     * 权限码
     */
    private String code;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限所在的权限模块ID
     */
    private Long aclModuleId;
    /**
     * 请求的url, 可以填正则表达式
     */
    private String url;
    /**
     * 类型<br>
     * {@link com.xcky.enums.SysAclTypeEnum}
     */
    private String type;
    /**
     * 状态<br>
     * {@link com.xcky.enums.SysAclStatusEnum}
     */
    private String status;
    /**
     * 权限在当前模块下的顺序，由小到大
     */
    private Long seq;
    /**
     * 备注
     */
    private String remark;
}
