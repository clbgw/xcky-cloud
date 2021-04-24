package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.CommonTimeAreaMapper;
import com.xcky.model.entity.CommonTimeArea;
import com.xcky.service.CommonTimeAreaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公共时间范围服务实现类
 *
 * @author lbchen
 */
@Service
public class CommonTimeAreaServiceImpl implements CommonTimeAreaService {
    @Autowired
    private CommonTimeAreaMapper commonTimeAreaMapper;
    @Override
    public Integer updateCommonTimeArea(CommonTimeArea commonTimeArea) {
        if(null == commonTimeArea) {
            return 0;
        }
        Long id = commonTimeArea.getId();
        boolean isInsert = false;
        if(null == id) {
            isInsert = true;
        } else {
            CommonTimeArea temp = getCommonTimeAreaById(id);
            if(null == temp) {
                isInsert = true;
            }
        }
        if(isInsert) {
            return commonTimeAreaMapper.insertCommonTimeArea(commonTimeArea);
        } else {
            return commonTimeAreaMapper.updateCommonTimeArea(commonTimeArea);
        }
    }

    @Override
    public CommonTimeArea getCommonTimeAreaById(Long id) {
        Map<String,Object> map = new HashMap<>(2);
        map.put("id",id);
        List<CommonTimeArea> list = commonTimeAreaMapper.selectTimeAreaByMap(map);
        if(null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageInfo<CommonTimeArea> getCommonTimeAreaPageInfoByMap(Map<String, Object> map, Integer page, Integer size) {
        PageInfo<CommonTimeArea> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            commonTimeAreaMapper.selectTimeAreaByMap(map);
        });
        return pageInfo;
    }

}
