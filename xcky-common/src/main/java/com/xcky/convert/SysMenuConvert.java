package com.xcky.convert;

import com.xcky.model.entity.SysMenu;
import com.xcky.model.vo.SysMenuVo;
import org.springframework.beans.BeanUtils;

/**
 * 系统菜单转换器
 *
 * @author lbchen
 */
public class SysMenuConvert {
    /**
     * 通过系统菜单值对象获取系统菜单实体类
     *
     * @param sysMenuVo 系统菜单值对象
     * @return 系统菜单实体类
     */
    public static SysMenu<SysMenuVo> getSysMenuByVo(SysMenuVo sysMenuVo) {
        if (null == sysMenuVo) {
            return null;
        }
        SysMenu<SysMenuVo> sysMenu = new SysMenu<>();
        BeanUtils.copyProperties(sysMenuVo, sysMenu);
        return sysMenu;
    }

    /**
     * 通过系统菜单实体类获取值对象
     *
     * @param sysMenu 系统菜单实体类
     * @return 系统菜单值对象
     */
    public static SysMenuVo getSysMenuVoByEntity(SysMenu<SysMenuVo> sysMenu) {
        if (null == sysMenu) {
            return null;
        }
        SysMenuVo sysMenuVo = new SysMenuVo();
        BeanUtils.copyProperties(sysMenu, sysMenuVo);
        return sysMenuVo;
    }
}
