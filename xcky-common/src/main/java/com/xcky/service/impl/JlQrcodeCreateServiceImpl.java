package com.xcky.service.impl;

import com.xcky.mapper.JlQrcodeCreateMapper;
import com.xcky.model.entity.JlQrcodeCreate;
import com.xcky.service.JlQrcodeCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序二维码创建记录服务实现类
 *
 * @author lbchen
 */
@Service
public class JlQrcodeCreateServiceImpl implements JlQrcodeCreateService {
    @Autowired
    private JlQrcodeCreateMapper jlQrcodeCreateMapper;
    
    @Override
    public Integer updateJlQrcodeCreate(JlQrcodeCreate jlQrcodeCreate) {
        if (null == jlQrcodeCreate) {
            return 0;
        }
        boolean isInsert = false;
        Long id = jlQrcodeCreate.getId();
        if (null == id) {
            isInsert = true;
        } else {
            JlQrcodeCreate temp = getJlQrcodeCreateById(id);
            if (null == temp) {
                isInsert = true;
            }
        }
        if (isInsert) {
            return jlQrcodeCreateMapper.insertJlQrcodeCreate(jlQrcodeCreate);
        }
        return jlQrcodeCreateMapper.updateJlQrcodeCreate(jlQrcodeCreate);
    }
    
    @Override
    public List<JlQrcodeCreate> getJlQrcodeCreateByMap(Map<String, Object> map) {
        return jlQrcodeCreateMapper.selectJlQrcodeCreateByMap(map);
    }
    
    @Override
    public JlQrcodeCreate getJlQrcodeCreateById(Long id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        List<JlQrcodeCreate> list = getJlQrcodeCreateByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
}
