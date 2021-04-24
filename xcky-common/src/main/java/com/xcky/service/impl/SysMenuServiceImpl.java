package com.xcky.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xcky.convert.SysMenuConvert;
import com.xcky.mapper.SysMenuMapper;
import com.xcky.model.entity.SysMenu;
import com.xcky.model.vo.SysMenuVo;
import com.xcky.service.SysMenuService;
import com.xcky.util.TreeUtil;

/**
 * 系统菜单服务接口实现类
 *
 * @author lbchen
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Integer updateSysMenuBatch(List<SysMenuVo> list) {
        Integer result = 0;
        Integer saveResult;
        for (SysMenuVo sysMenuVo : list) {
            if (null == sysMenuVo) {
                continue;
            }
            if (null == sysMenuVo.getPid()) {
                sysMenuVo.setPid(0);
            }
            saveResult = updateSysMenu(sysMenuVo);
            if (null != saveResult && saveResult > 0) {
                result += saveResult;
                List<SysMenuVo> subList = sysMenuVo.getList();
                if (null != subList && subList.size() > 0) {
                    Integer pid = sysMenuVo.getId();
                    subList.forEach(item -> {
                        item.setPid(pid);
                    });
                    result += updateSysMenuBatch(subList);
                }
            }
        }
        return result;
    }

    @Override
    public Integer updateSysMenu(SysMenuVo sysMenuVo) {
        if (null == sysMenuVo) {
            return 0;
        }
        boolean isInsert = false;
        Integer id = sysMenuVo.getId();
        if (null == id) {
            isInsert = true;
        } else {
            SysMenu<SysMenuVo> temp = getSysMenuById(id);
            if (null == temp) {
                isInsert = true;
            }
        }
        SysMenu<SysMenuVo> sysMenu = SysMenuConvert.getSysMenuByVo(sysMenuVo);
        if (isInsert) {
            Integer result = sysMenuMapper.insertSysMenu(sysMenu);
            if (null != result && result > 0) {
                sysMenuVo.setId(sysMenu.getId());
            }
            return result;
        } else {
            return sysMenuMapper.updateSysMenu(sysMenu);
        }
    }

    @Override
    public SysMenu<SysMenuVo> getSysMenuById(Integer id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        List<SysMenu<SysMenuVo>> list = sysMenuMapper.selectSysMenuByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public SysMenuVo getSysMenuVoById(Integer id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        List<SysMenuVo> list = sysMenuMapper.selectSysMenuVoByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SysMenuVo> getSysMenuTreeByMap(Map<String, Object> map) {
        return sysMenuMapper.selectSysMenuVoByMap(map);
    }


    @Override
    public List<SysMenuVo> getSysMenuVoTreeByList(List<SysMenuVo> list) {
        TreeUtil<SysMenuVo> treeUtil = new TreeUtil<>();
        return treeUtil.getTreeByList(list);
    }
}
