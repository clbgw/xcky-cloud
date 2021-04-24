package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台用户实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysUser extends BaseBackEntity {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 加密后的密码
     */
    private String password;
    /**
     * 用户所在部门的ID
     */
    private Long deptId;
    /**
     * 用户状态<br>
     * {@link com.xcky.enums.SysUserStatusEnum}
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 密码盐
     */
    private String salt;
}
