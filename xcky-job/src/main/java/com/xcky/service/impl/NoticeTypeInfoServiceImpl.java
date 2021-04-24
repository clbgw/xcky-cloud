package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.NoticeTypeInfoMapper;
import com.xcky.model.entity.NoticeTypeInfo;
import com.xcky.model.req.NoticeTypePageReq;
import com.xcky.service.NoticeTypeInfoService;
import com.xcky.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息类型服务接口实现类
 *
 * @author lbchen
 */
@Service
public class NoticeTypeInfoServiceImpl implements NoticeTypeInfoService {
    @Autowired
    private NoticeTypeInfoMapper noticeTypeInfoMapper;
    @Override
    public Integer updateNoticeTypeInfo(NoticeTypeInfo noticeTypeInfo) {
        if(null == noticeTypeInfo) {
            return 0;
        }
        boolean isInsert = false;
        Long id = noticeTypeInfo.getId();
        
        if(null == id) {
            isInsert = true;
        } else {
            NoticeTypeInfo noticeTypeInfoTemp = getNoticeTypeInfoByKey(id);
            if(null == noticeTypeInfoTemp) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        if(isInsert) {
            noticeTypeInfo.setCreateTime(nowDate);
            noticeTypeInfo.setUpdateTime(nowDate);
            return noticeTypeInfoMapper.insertNoticeTypeInfo(noticeTypeInfo);
        } else {
            noticeTypeInfo.setUpdateTime(nowDate);
            return noticeTypeInfoMapper.updateNoticeTypeInfo(noticeTypeInfo);
        }
    }
    
    @Override
    public NoticeTypeInfo getNoticeTypeInfoByKey(Long id) {
        Map<String,Object> map = new HashMap<>(4);
        map.put("id",id);
        List<NoticeTypeInfo> list = noticeTypeInfoMapper.seleteNoticeTypeByMap(map);
        if(null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public PageInfo<NoticeTypeInfo> getPageInfoByReq(NoticeTypePageReq noticeTypePageReq) {
        Integer page = noticeTypePageReq.getPage();
        Integer size = noticeTypePageReq.getSize();
        Map<String,Object> map = EntityUtil.entityToMap(noticeTypePageReq);
        PageInfo<NoticeTypeInfo> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            noticeTypeInfoMapper.seleteNoticeTypeByMap(map);
        });
        return pageInfo;
    }

    @Override
    public Integer deleteNoticeTypeByIds(List<Long> ids) {
        if(null != ids && ids.size() > 0) {
            return noticeTypeInfoMapper.deleteNoticeTypeByIds(ids);
        }
        return 0;
    }
}
