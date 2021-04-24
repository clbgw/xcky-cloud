package com.xcky.service.impl;

import com.xcky.mapper.SysRoleUserMapper;
import com.xcky.model.entity.SysRoleUser;
import com.xcky.service.RoleUserService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色与用户关系服务实现类
 *
 * @author lbchen
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public Integer updateSysRoleUser(SysRoleUser sysRoleUser) {
        if (null == sysRoleUser) {
            return 0;
        }
        boolean isInsert = false;
        Long id = sysRoleUser.getId();
        if (null == id) {
            isInsert = true;
        } else {
            SysRoleUser temp = getSysRoleUserById(id);
            if (null == temp) {
                isInsert = true;
            }
        }

        if (isInsert) {
            return sysRoleUserMapper.insertSysRoleUser(sysRoleUser);
        } else {
            return sysRoleUserMapper.updateSysRoleUser(sysRoleUser);
        }
    }

    @Override
    public List<SysRoleUser> getSysRoleUserByMap(Map<String, Object> map) {
        map.put("nowTime", new Date());
        return sysRoleUserMapper.selectSysRoleUserByMap(map);
    }

    @Override
    public SysRoleUser getSysRoleUserById(Long id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        List<SysRoleUser> list = sysRoleUserMapper.selectSysRoleUserByMap(map);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
