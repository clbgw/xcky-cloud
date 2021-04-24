package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.enums.SysUserStatusEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.SysUserMapper;
import com.xcky.model.entity.SysUser;
import com.xcky.model.req.AdminSysUserPageReq;
import com.xcky.model.vo.SysUserVo;
import com.xcky.service.SysUserService;
import com.xcky.util.Constants;
import com.xcky.util.DateUtil;
import com.xcky.util.EntityUtil;
import com.xcky.util.JwtUtil;
import com.xcky.util.MailUtil;
import com.xcky.util.StringUtil;
import com.xcky.util.encry.Md5Util;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务接口实现类
 *
 * @author lbchen
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private MailUtil mailUtil;

    @Override
    public PageInfo<SysUserVo> getSysUserVoByReq(AdminSysUserPageReq adminSysUserPageReq) {
        Integer page = adminSysUserPageReq.getPage();
        Integer size = adminSysUserPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(adminSysUserPageReq);
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            sysUserMapper.selectSysUserByMap(map);
        });
    }

    @Override
    public String loginByUsername(String username, String password) {
        // 根据账号查询用户信息
        Map<String, Object> map = new HashMap<>(4);
        map.put("username", username);
        List<SysUserVo> list = sysUserMapper.selectSysUserByMap(map);
        // 判断用户信息是否存在
        if (null == list || list.size() < 1) {
            throw new BizException(ResponseEnum.USERNAME_OR_PASSWORD_ERROR, null);
        }

        SysUserVo userVo = list.get(0);
        checkPassword(userVo, password, ResponseEnum.USERNAME_OR_PASSWORD_ERROR);

        // 根据用户名生成token
        return JwtUtil.createJWT(username);
    }

    @Override
    public String loginByEmail(String email, String password) {
        // 根据邮箱查询用户信息
        Map<String, Object> map = new HashMap<>(4);
        map.put("email", email);
        List<SysUserVo> list = sysUserMapper.selectSysUserByMap(map);
        // 判断用户信息是否存在
        if (null == list || list.size() < 1) {
            throw new BizException(ResponseEnum.EMAIL_OR_PASSWORD_ERROR, null);
        }

        SysUserVo userVo = list.get(0);
        checkPassword(userVo, password, ResponseEnum.EMAIL_OR_PASSWORD_ERROR);
        // 发送邮箱,告知邮箱登录系统
        String timeStr = DateUtil.getTimeStrByDate(new Date());
        mailUtil.sendAliYunMail("登录"+Constants.SYSTEM_NAME+"通知",email,"尊敬的"+userVo.getUsername()+"用户,您于"+ timeStr+"登录"+Constants.SYSTEM_NAME+",特此通知，如果不是本人登录，还请及时注意账号安全！");

        // 根据用户名生成token
        return JwtUtil.createJWT(userVo.getUsername());
    }

    @Override
    public String loginByPhone(String phone, String password) {
        // 根据手机查询用户信息
        Map<String, Object> map = new HashMap<>(4);
        map.put("phone", phone);
        List<SysUserVo> list = sysUserMapper.selectSysUserByMap(map);
        // 判断用户信息是否存在
        if (null == list || list.size() < 1) {
            throw new BizException(ResponseEnum.PHONE_OR_PASSWORD_ERROR, null);
        }

        SysUserVo userVo = list.get(0);
        checkPassword(userVo, password, ResponseEnum.PHONE_OR_PASSWORD_ERROR);
        // 根据用户名生成token
        return JwtUtil.createJWT(userVo.getUsername());
    }

    @Override
    public Integer updateUserPwd(String username, String oldPwd, String password) {
        // 根据账号查询用户信息
        Map<String, Object> map = new HashMap<>(4);
        map.put("username", username);
        List<SysUserVo> list = sysUserMapper.selectSysUserByMap(map);
        // 判断用户信息是否存在
        if (null == list || list.size() < 1) {
            throw new BizException(ResponseEnum.PASSWORD_ERROR, null);
        }
        SysUserVo userVo = list.get(0);
        // 检查旧密码是否匹配
        checkPassword(userVo, oldPwd, ResponseEnum.PASSWORD_ERROR);
        // 生成新密码
        String newSalt = StringUtil.generateNonceStr(20);
        String newPassword = getPwd(password, newSalt);
        // 更新密码
        return sysUserMapper.updateUserPwd(username, newPassword, newSalt);
    }

    @Override
    public void registerByEmail(String email, String password, Long deptId) {
        // 验证邮箱唯一性
        checkUserNameUnique("email",email,ResponseEnum.USERNAME_EMAIL_HAD_USED);
        String salt = StringUtil.generateNonceStr(20);
        String userPwd = getPwd(password,salt);
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(deptId);
        sysUser.setMail(email);
        sysUser.setSalt(salt);
        sysUser.setPassword(userPwd);
        sysUser.setStatus(SysUserStatusEnum.NORMAL.getCode());
        saveSysUser(sysUser);
    }

    @Override
    public void registerByPhone(String phone, String password, Long deptId) {
        // 验证手机号唯一性
        checkUserNameUnique("telephone",phone,ResponseEnum.USERNAME_PHONE_HAD_USED);
        String salt = StringUtil.generateNonceStr(20);
        String userPwd = getPwd(password,salt);
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(deptId);
        sysUser.setTelephone(phone);
        sysUser.setSalt(salt);
        sysUser.setPassword(userPwd);
        sysUser.setStatus(SysUserStatusEnum.NORMAL.getCode());
        saveSysUser(sysUser);
    }

    @Override
    public void registerByUsername(String username, String password, Long deptId) {
        // 验证用户名唯一性
        checkUserNameUnique("username",username,ResponseEnum.USERNAME_NAME_HAD_USED);
        String salt = StringUtil.generateNonceStr(20);
        String userPwd = getPwd(password,salt);
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(deptId);
        sysUser.setUsername(username);
        sysUser.setSalt(salt);
        sysUser.setPassword(userPwd);
        sysUser.setStatus(SysUserStatusEnum.NORMAL.getCode());
        saveSysUser(sysUser);
    }

    private void saveSysUser(SysUser sysUser) {
       Integer saveResult = sysUserMapper.insertSysUser(sysUser);
       if(null == saveResult || saveResult < 1) {
           throw new BizException(ResponseEnum.INSERT_ERROR, null);
       }
    }

    /**
     * 校验用户是否唯一
     *
     * @param type         校验用户类型
     * @param username     用户名称(用户名/邮箱/手机号)
     * @param responseEnum 错误时抛出的响应枚举
     */
    private void checkUserNameUnique(String type, String username, ResponseEnum responseEnum) {
        // 查询用户名的注册个数
        Integer countName = sysUserMapper.selectCountUserName(type,username);
        if (null != countName && countName > 0) {
            throw new BizException(responseEnum, null);
        }
    }

    /**
     * 密码算法
     *
     * @param sourcePwd 密码原文
     * @param salt      密码盐
     * @return 加密后的密码
     */
    private String getPwd(String sourcePwd, String salt) {
        return Md5Util.encode(salt + sourcePwd);
    }

    /**
     * 检查用户输入的密码是否一致
     *
     * @param userVo       用户对象
     * @param password     用户输入的密码
     * @param responseEnum 返回响应枚举值
     */
    private void checkPassword(SysUserVo userVo, String password, ResponseEnum responseEnum) {
        String userPassword = userVo.getPassword();
        // 根据密码规则生成密码
        String newPassword = Md5Util.encode(userVo.getSalt() + password);
        if (!userPassword.equals(newPassword)) {
            throw new BizException(responseEnum, null);
        }
    }
}
