package com.xcky.mapper;

import com.xcky.model.entity.SysMenu;
import com.xcky.model.vo.SysMenuVo;
import java.util.List;
import java.util.Map;

/**
 * 菜单数据访问接口
 *
 * @author lbchen
 */
public interface SysMenuMapper {
    /**
     * 新增菜单
     *
     * @param sysMenu 系统菜单
     * @return 新增行数
     */
    Integer insertSysMenu(SysMenu<SysMenuVo> sysMenu);

    /**
     * 更新菜单
     *
     * @param sysMenu 系统菜单
     * @return 更新行数
     */
    Integer updateSysMenu(SysMenu<SysMenuVo> sysMenu);

    /**
     * 根据map条件查询菜单列表
     *
     * @param map map条件
     * @return 菜单列表
     */
    List<SysMenu<SysMenuVo>> selectSysMenuByMap(Map<String, Object> map);

    /**
     * 根据map条件查询菜单值对象列表
     *
     * @param map map条件
     * @return 菜单值对象列表
     */
    List<SysMenuVo> selectSysMenuVoByMap(Map<String, Object> map);
}
