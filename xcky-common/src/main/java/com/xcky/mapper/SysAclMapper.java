package com.xcky.mapper;

import com.xcky.model.entity.SysAcl;

import java.util.List;
import java.util.Map;

/**
 * 后台权限对象映射接口
 *
 * @author lbchen
 */
public interface SysAclMapper {
    /**
     * 新增后台权限
     *
     * @param sysAcl
     * @return
     */
    Integer insertSysAcl(SysAcl sysAcl);
    
    /**
     * 更新后台权限
     *
     * @param sysAcl
     * @return
     */
    Integer updateSysAcl(SysAcl sysAcl);
    
    /**
     * 根据map条件查询系统权限列表
     *
     * @param map map条件
     * @return 权限列表
     */
    List<SysAcl> selectSysAclByMap(Map<String, Object> map);
}
