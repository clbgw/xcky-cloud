package com.xcky.mapper;

import com.xcky.model.entity.SysRoleAcl;

import java.util.List;
import java.util.Map;

/**
 * 后台角色权限关系对象映射接口
 *
 * @author lbchen
 */
public interface SysRoleAclMapper {
    /**
     * 新增角色权限关系
     *
     * @param sysRoleAcl 角色权限关系
     * @return 新增行数
     */
    Integer insertSysRoleAcl(SysRoleAcl sysRoleAcl);
    
    /**
     * 更新角色权限关系
     *
     * @param sysRoleAcl 角色权限关系
     * @return 更新行数
     */
    Integer updateSysRoleAcl(SysRoleAcl sysRoleAcl);
    
    /**
     * 根据map条件查询角色权限列表
     *
     * @param map map条件
     * @return 角色权限列表
     */
    List<SysRoleAcl> selectSysRoleAclByMap(Map<String, Object> map);
}
