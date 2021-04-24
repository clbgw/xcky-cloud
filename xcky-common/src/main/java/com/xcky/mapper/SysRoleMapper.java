package com.xcky.mapper;

import com.xcky.model.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 系统角色对象映射接口
 *
 * @author lbchen
 */
public interface SysRoleMapper {
    /**
     * 新增系统角色
     *
     * @param sysRole 系统角色
     * @return 新增行数
     */
    Integer insertSysRole(SysRole sysRole);
    
    /**
     * 更新系统角色
     *
     * @param sysRole 系统角色
     * @return 更新行数
     */
    Integer updateSysRole(SysRole sysRole);
    
    /**
     * 根据map条件查询系统角色列表
     *
     * @param map map条件
     * @return 系统角色列表
     */
    List<SysRole> selectSysRoleByMap(Map<String, Object> map);
}
