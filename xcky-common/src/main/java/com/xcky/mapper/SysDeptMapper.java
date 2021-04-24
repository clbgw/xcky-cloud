package com.xcky.mapper;

import com.xcky.model.entity.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 后台部门对象映射接口
 *
 * @author lbchen
 */
public interface SysDeptMapper {
    /**
     * 新增后台部门
     *
     * @param sysDept 后台部门
     * @return 新增行数
     */
    Integer insertSysDept(SysDept sysDept);
    
    /**
     * 更新后台部门
     *
     * @param sysDept 后台部门
     * @return 更新行数
     */
    Integer updateSysDept(SysDept sysDept);
    
    /**
     * 根据map条件查询后台部门列表
     *
     * @param map map条件
     * @return 后台部门列表
     */
    List<SysDept> selectSysDeptByMap(Map<String, Object> map);
}
