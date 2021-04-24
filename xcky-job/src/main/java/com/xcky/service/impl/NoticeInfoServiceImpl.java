package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.NoticeInfoMapper;
import com.xcky.model.entity.NoticeInfo;
import com.xcky.model.req.NoticeInfoDeleteReq;
import com.xcky.model.req.NoticeInfoPageReq;
import com.xcky.service.NoticeInfoService;
import com.xcky.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息信息服务接口实现类
 *
 * @author lbchen
 */

@Service
public class NoticeInfoServiceImpl implements NoticeInfoService {
    @Autowired
    private NoticeInfoMapper noticeInfoMapper;
    
    @Override
    public Integer updateNoticeInfo(NoticeInfo noticeInfo) {
        if (null == noticeInfo) {
            return 0;
        }
        boolean isInsert = false;
        Long id = noticeInfo.getId();
        if (null == id) {
            isInsert = true;
        } else {
            NoticeInfo noticeInfoTemp = getDetail(id);
            if (null == noticeInfoTemp) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        if (isInsert) {
            noticeInfo.setCreateTime(nowDate);
            noticeInfo.setUpdateTime(nowDate);
            noticeInfo.setRecordDate(nowDate);
            return noticeInfoMapper.insertNoticeInfo(noticeInfo);
        } else {
            noticeInfo.setUpdateTime(nowDate);
            return noticeInfoMapper.updateNoticeInfo(noticeInfo);
        }
    }
    
    @Override
    public Integer deleteNoticeInfoByMap(NoticeInfoDeleteReq noticeInfoDeleteReq) {
        if (null == noticeInfoDeleteReq) {
            return 0;
        }
        Date nowDate = new Date();
        noticeInfoDeleteReq.setDeleteTime(nowDate);
        return noticeInfoMapper.deleteNoticeInfo(noticeInfoDeleteReq);
    }
    
    @Override
    public NoticeInfo getDetail(Long id) {
        if (null == id) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", id);
        List<NoticeInfo> list = noticeInfoMapper.selectNoticeInfosByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public PageInfo<NoticeInfo> getPageInfo(NoticeInfoPageReq noticeInfoPageReq) {
        Integer page = noticeInfoPageReq.getPage();
        Integer size = noticeInfoPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(noticeInfoPageReq);
        PageInfo<NoticeInfo> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            noticeInfoMapper.selectNoticeInfosByMap(map);
        });
        return pageInfo;
    }
}
