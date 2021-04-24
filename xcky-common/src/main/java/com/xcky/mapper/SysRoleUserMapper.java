package com.xcky.mapper;

import com.xcky.model.entity.SysRoleUser;

import java.util.List;
import java.util.Map;

/**
 * 系统角色用户关系对象映射接口
 *
 * @author lbchen
 */
public interface SysRoleUserMapper {
    /**
     * 新增系统角色用户关系
     *
     * @param sysRoleUser 系统角色用户关系
     * @return 新增行数
     */
    Integer insertSysRoleUser(SysRoleUser sysRoleUser);
    
    /**
     * 更新系统角色用户关系
     *
     * @param sysRoleUser 系统角色用户关系
     * @return 更新行数
     */
    Integer updateSysRoleUser(SysRoleUser sysRoleUser);
    
    /**
     * 根据map条件查询系统角色用户关系列表
     *
     * @param map map条件
     * @return 系统角色用户关系列表
     */
    List<SysRoleUser> selectSysRoleUserByMap(Map<String, Object> map);
    
}
