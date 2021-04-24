package com.xcky.controller;

import com.github.pagehelper.PageInfo;
import com.xcky.annotation.LoginAuthAnnotation;
import com.xcky.enums.ResponseEnum;
import com.xcky.model.req.AdminSysUserPageReq;
import com.xcky.model.req.AdminSysUserRegReq;
import com.xcky.model.req.AdminUserLoginReq;
import com.xcky.model.req.AdminUserUpdatePwdReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.SysUserVo;
import com.xcky.service.SysUserService;
import com.xcky.util.Constants;
import com.xcky.util.JwtUtil;
import com.xcky.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制器
 *
 * @author lbchen
 */
@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询系统用户列表
     *
     * @param adminSysUserPageReq 后台请求系统用户列表请求参数
     * @return 基本响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/sys/getSysUserList")
    public R getUserInfoList(@RequestBody AdminSysUserPageReq adminSysUserPageReq) {
        if (null == adminSysUserPageReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 根据请求参数查询系统用户列表
        PageInfo<SysUserVo> pageInfo = sysUserService.getSysUserVoByReq(adminSysUserPageReq);
        return ResponseUtil.page(pageInfo);
    }

    /**
     * 后台系统登录
     *
     * @param adminUserLoginReq 后台系统登录请求参数
     * @return 基本响应对象
     */
    @PostMapping("/sys/user/login")
    public R userLogin(@RequestBody AdminUserLoginReq adminUserLoginReq) {
        if (null == adminUserLoginReq || StringUtils.isEmpty(adminUserLoginReq.getUsername()) || StringUtils.isEmpty(adminUserLoginReq.getPassword()) || null == adminUserLoginReq.getLoginType()) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        Integer loginType = adminUserLoginReq.getLoginType();
        String username = adminUserLoginReq.getUsername();
        String password = adminUserLoginReq.getPassword();
        String token;
        // 登录操作
        switch (loginType) {
            case 2:
                // 邮箱密码登录
                token = sysUserService.loginByEmail(username, password);
                break;
            case 3:
                // 手机密码登录
                token = sysUserService.loginByPhone(username, password);
                break;
            default:
                // 账号密码登录
                token = sysUserService.loginByUsername(username, password);
                break;
        }
        return ResponseUtil.ok(token);
    }

    /**
     * 后台系统修改登录密码
     *
     * @param adminUserUpdatePwdReq 后台系统修改登录密码请求参数
     * @return 基本响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/sys/user/updatePwd")
    public R userUpdatePwd(@RequestBody AdminUserUpdatePwdReq adminUserUpdatePwdReq) {
        if (null == adminUserUpdatePwdReq || StringUtils.isEmpty(adminUserUpdatePwdReq.getToken())) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }

        // 新旧密码必须一致
        String password = adminUserUpdatePwdReq.getPassword();
        String rePassword = adminUserUpdatePwdReq.getRepassword();
        if (StringUtils.isEmpty(password) || !password.equals(rePassword)) {
            return ResponseUtil.fail(ResponseEnum.NOT_SAME_PASSWORD);
        }

        String token = adminUserUpdatePwdReq.getToken();
        Claims claims = JwtUtil.parseJWT(token);
        String username = "" + claims.get(Constants.TOKEN_FIELD);
        // 获取旧密码
        String oldPwd = adminUserUpdatePwdReq.getOldPassword();
        sysUserService.updateUserPwd(username, oldPwd, password);

        String newToken = JwtUtil.createJWT(username);
        return ResponseUtil.ok(newToken);
    }


    /**
     * 后台系统注册
     *
     * @param adminSysUserRegReq 后台系统注册请求参数
     * @return 基本响应对象
     */
    @PostMapping("/sys/user/register")
    public R userRegister(@RequestBody AdminSysUserRegReq adminSysUserRegReq) {
        if (null == adminSysUserRegReq || StringUtils.isEmpty(adminSysUserRegReq.getUsername()) || StringUtils.isEmpty(adminSysUserRegReq.getPassword()) || null == adminSysUserRegReq.getLoginType()) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        Integer loginType = adminSysUserRegReq.getLoginType();
        String username = adminSysUserRegReq.getUsername();
        String password = adminSysUserRegReq.getPassword();
        Long deptId = adminSysUserRegReq.getDeptId();
        // 注册操作
        switch (loginType) {
            case 2:
                // 邮箱密码注册
                sysUserService.registerByEmail(username, password, deptId);
                break;
            case 3:
                // 手机密码注册
                sysUserService.registerByPhone(username, password, deptId);
                break;
            default:
                // 账号密码注册
                sysUserService.registerByUsername(username, password, deptId);
                break;
        }
        return ResponseUtil.ok();
    }


}
