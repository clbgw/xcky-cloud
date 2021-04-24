package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.NoticeDistributeConfigMapper;
import com.xcky.model.entity.NoticeDistributeConfig;
import com.xcky.model.req.NoticeDistributeConfigPageReq;
import com.xcky.model.req.NoticeDistributeConfigRespPageReq;
import com.xcky.model.resp.NoticeDistributeConfigResp;
import com.xcky.service.NoticeDistributeConfigService;
import com.xcky.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息分发配置服务接口实现类
 *
 * @author lbchen
 */
@Service
public class NoticeDistributeConfigServiceImpl implements NoticeDistributeConfigService {
    @Autowired
    private NoticeDistributeConfigMapper noticeDistributeConfigMapper;
    
    @Override
    public Integer updateNoticeDistributeConfig(NoticeDistributeConfig noticeDistributeConfig) {
        if (null == noticeDistributeConfig) {
            return 0;
        }
        boolean isInsert = false;
        Long id = noticeDistributeConfig.getId();
        if (null == id) {
            isInsert = true;
        } else {
            NoticeDistributeConfig noticeDistributeConfigTemp = getNoticeDistributeConfigDetailByKey(id);
            if (null == noticeDistributeConfigTemp) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        if (isInsert) {
            noticeDistributeConfig.setCreateTime(nowDate);
            noticeDistributeConfig.setUpdateTime(nowDate);
            return noticeDistributeConfigMapper.insertNoticeDistributeConfig(noticeDistributeConfig);
        } else {
            noticeDistributeConfig.setUpdateTime(nowDate);
            return noticeDistributeConfigMapper.updateNoticeDistributeConfig(noticeDistributeConfig);
        }
    }
    
    @Override
    public Integer deleteNoticeDistributeConfigByMap(Map<String, Object> map) {
        return noticeDistributeConfigMapper.deleteNoticeDistributeConfigByMap(map);
    }
    
    @Override
    public NoticeDistributeConfig getNoticeDistributeConfigDetailByKey(Long id) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", id);
        List<NoticeDistributeConfig> list = noticeDistributeConfigMapper.selectNoticeDistributeConfigByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public PageInfo<NoticeDistributeConfig> getPageInfo(NoticeDistributeConfigPageReq noticeDistributeConfigPageReq) {
        Integer page = noticeDistributeConfigPageReq.getPage();
        Integer size = noticeDistributeConfigPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(noticeDistributeConfigPageReq);
        PageInfo<NoticeDistributeConfig> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            noticeDistributeConfigMapper.selectNoticeDistributeConfigByMap(map);
        });
        return pageInfo;
    }
    
    @Override
    public PageInfo<NoticeDistributeConfigResp> getRespPageInfo(NoticeDistributeConfigRespPageReq noticeDistributeConfigRespPageReq) {
        Integer page = noticeDistributeConfigRespPageReq.getPage();
        Integer size = noticeDistributeConfigRespPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(noticeDistributeConfigRespPageReq);
        PageInfo<NoticeDistributeConfigResp> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            noticeDistributeConfigMapper.selectNoticeDistributeConfigRespByMap(map);
        });
        return pageInfo;
    }
}
