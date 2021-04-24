package com.xcky.service;

import com.xcky.model.entity.SysMenu;
import com.xcky.model.vo.SysMenuVo;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单服务接口
 *
 * @author lbchen
 */
public interface SysMenuService {
    /**
     * 批量新增菜单目录
     *
     * @param list 深度的菜单树
     * @return 更新行数
     */
    Integer updateSysMenuBatch(List<SysMenuVo> list);

    /**
     * 更新菜单
     *
     * @param sysMenuVo 系统菜单值对象
     * @return 更新行数
     */
    Integer updateSysMenu(SysMenuVo sysMenuVo);

    /**
     * 通过主键获取系统菜单
     *
     * @param id 主键ID
     * @return 系统菜单
     */
    SysMenu<SysMenuVo> getSysMenuById(Integer id);

    /**
     * 通过主键ID获取系统菜单值对象
     *
     * @param id 主键ID
     * @return 系统菜单值对象
     */
    SysMenuVo getSysMenuVoById(Integer id);

    /**
     * 根据map条件获取系统菜单树
     *
     * @param map map条件
     * @return 系统菜单树
     */
    List<SysMenuVo> getSysMenuTreeByMap(Map<String, Object> map);

    /**
     * 根据扁平化单层的菜单列表转化成深度的菜单树（列表转树）
     *
     * @param list 扁平化单层的菜单列表
     * @return 深度的菜单树
     */
    List<SysMenuVo> getSysMenuVoTreeByList(List<SysMenuVo> list);
}
