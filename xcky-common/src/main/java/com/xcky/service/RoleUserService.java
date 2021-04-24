package com.xcky.service;

import com.xcky.model.entity.SysRoleUser;
import java.util.List;
import java.util.Map;

/**
 * 角色与用户关系服务接口
 *
 * @author lbchen
 */
public interface RoleUserService {
    /**
     * 更新系统角色用户关系
     *
     * @param sysRoleUser 系统角色用户关系
     * @return 更新行数
     */
    Integer updateSysRoleUser(SysRoleUser sysRoleUser);

    /**
     * 根据map条件获取系统角色用户关系列表
     *
     * @param map map条件
     * @return 系统角色用户关系列表
     */
    List<SysRoleUser> getSysRoleUserByMap(Map<String, Object> map);

    /**
     * 根据主键ID获取系统角色用户关系
     *
     * @param id 主键ID
     * @return 系统角色用户关系
     */
    SysRoleUser getSysRoleUserById(Long id);

}
