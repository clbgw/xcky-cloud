package com.xcky.mapper;

import com.xcky.model.entity.SysUser;
import com.xcky.model.vo.SysUserVo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户对象映射接口
 *
 * @author lbchen
 */
public interface SysUserMapper {
    /**
     * 通过map条件查询角色ID列表
     *
     * @param map map条件
     * @return 角色ID列表
     */
    List<Integer> selectRoleIdsByMap(Map<String, Object> map);

    /**
     * 通过map条件查询系统用户值对象列表
     *
     * @param map map条件
     * @return 系统用户值对象列表
     */
    List<SysUserVo> selectSysUserByMap(Map<String, Object> map);

    /**
     * 更新用户密码
     *
     * @param username    用户名
     * @param newPassword 密码
     * @param salt        密码盐
     * @return 影响行数
     */
    Integer updateUserPwd(@Param("username") String username, @Param("password") String newPassword, @Param("salt") String salt);

    /**
     * 查询用户名称在库中的个数
     *
     * @param type     类型
     * @param username 用户名
     * @return 记录数量
     */
    Integer selectCountUserName(@Param("type") String type, @Param("username") String username);

    /**
     * 新增系统用户
     *
     * @param sysUser 系统用户
     * @return 新增记录数
     */
    Integer insertSysUser(SysUser sysUser);
}
