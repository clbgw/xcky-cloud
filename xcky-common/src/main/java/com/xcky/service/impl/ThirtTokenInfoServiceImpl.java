package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.ThirtTokenInfoMapper;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.req.ThirtTokenInfoPageReq;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.util.EntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方会话信息服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class ThirtTokenInfoServiceImpl implements ThirtTokenInfoService {
    @Autowired
    private ThirtTokenInfoMapper thirtTokenInfoMapper;

    @Override
    public Integer updateThirtTokenInfo(ThirtTokenInfo thirtTokenInfo) {
        if (null == thirtTokenInfo) {
            return 0;
        }
        boolean isInsert = false;
        Long id = thirtTokenInfo.getId();
        if (null == id) {
            isInsert = true;
        } else {
            ThirtTokenInfo thirtTokenInfoTemp = getThirtTokenInfoById(id);
            if (null == thirtTokenInfoTemp) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        if (isInsert) {
            Map<String, Object> map = new HashMap<>(4);
            map.put("appid", thirtTokenInfo.getAppid());
            ThirtTokenInfo thirtTokenInfoTemp = getThirtTokenInfoByMap(map);
            if (null == thirtTokenInfoTemp) {
                thirtTokenInfo.setCreateTime(nowDate);
                thirtTokenInfo.setUpdateTime(nowDate);
                return thirtTokenInfoMapper.insertThirtTokenInfo(thirtTokenInfo);
            } else {
                isInsert = false;
            }
        }
        if (!isInsert) {
            thirtTokenInfo.setUpdateTime(nowDate);
            return thirtTokenInfoMapper.updateThirtTokenInfo(thirtTokenInfo);
        } else {
            log.error("逻辑错误:" + JSONObject.toJSONString(thirtTokenInfo));
            return -1;
        }
    }

    @Override
    public ThirtTokenInfo getThirtTokenInfoById(Long id) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", id);
        return getThirtTokenInfoByMap(map);
    }

    @Override
    public ThirtTokenInfo getThirtTokenInfoByMap(Map<String, Object> map) {
        List<ThirtTokenInfo> list = thirtTokenInfoMapper.selectThirtTokenInfosByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageInfo<ThirtTokenInfo> getThirtTokenInfoByPageReq(ThirtTokenInfoPageReq thirtTokenInfoPageReq) {
        Integer page = thirtTokenInfoPageReq.getPage();
        Integer size = thirtTokenInfoPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(thirtTokenInfoPageReq);
        PageInfo<ThirtTokenInfo> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            thirtTokenInfoMapper.selectThirtTokenInfosByMap(map);
        });
        return pageInfo;
    }

    @Override
    public ThirtTokenInfo getThirtTokenInfoByAppid(String appid) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("appid", appid);
        return getThirtTokenInfoByMap(map);
    }

}
