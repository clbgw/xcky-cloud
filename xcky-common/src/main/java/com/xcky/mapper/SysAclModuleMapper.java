package com.xcky.mapper;

import com.xcky.model.entity.SysAclModule;

import java.util.List;
import java.util.Map;

/**
 * 后台权限模块对象映射接口
 *
 * @author lbchen
 */
public interface SysAclModuleMapper {
    /**
     * 新增后台权限模块
     *
     * @param sysAclModule 后台权限模块
     * @return 新增行数
     */
    Integer insertSysAclModule(SysAclModule sysAclModule);
    
    /**
     * 更新后台权限模块
     * @param sysAclModule 后台权限模块
     * @return 更新行数
     */
    Integer updateSysAclModule(SysAclModule sysAclModule);
    
    /**
     * 根据map条件查询后台权限模块列表
     * @param map map条件
     * @return 后台权限模块列表
     */
    List<SysAclModule> selectSysAclModuleByMap(Map<String, Object> map);
}
