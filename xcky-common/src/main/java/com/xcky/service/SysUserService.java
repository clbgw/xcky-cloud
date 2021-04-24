package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.req.AdminSysUserPageReq;
import com.xcky.model.vo.SysUserVo;

/**
 * 系统用户服务接口
 *
 * @author lbchen
 */
public interface SysUserService {
    /**
     * 根据系统用户分页请求参数获取系统用户分页列表
     *
     * @param adminSysUserPageReq 系统用户分页请求参数
     * @return 系统用户分页列表
     */
    PageInfo<SysUserVo> getSysUserVoByReq(AdminSysUserPageReq adminSysUserPageReq);

    /**
     * 根据账号密码登录
     *
     * @param username 账号
     * @param password 密码
     * @return token字符串
     */
    String loginByUsername(String username, String password);

    /**
     * 根据邮箱密码登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return token字符串
     */
    String loginByEmail(String email, String password);

    /**
     * 根据手机密码登录
     *
     * @param phone    手机
     * @param password 密码
     * @return token字符串
     */
    String loginByPhone(String phone, String password);

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param oldPwd   旧密码
     * @param password 新密码
     * @return 更新行数
     */
    Integer updateUserPwd(String username, String oldPwd, String password);

    /**
     * 通过邮箱注册
     *
     * @param email    邮箱
     * @param password 密码
     * @param deptId   部门ID
     */
    void registerByEmail(String email, String password, Long deptId);

    /**
     * 通过电话注册
     *
     * @param phone    电话
     * @param password 密码
     * @param deptId   部门ID
     */
    void registerByPhone(String phone, String password, Long deptId);

    /**
     * 通过用户名注册
     *
     * @param username 用户名
     * @param password 密码
     * @param deptId   部门ID
     */
    void registerByUsername(String username, String password, Long deptId);
}
