package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.NoticeDistributeInfoStatusEnum;
import com.xcky.mapper.NoticeDistributeInfoMapper;
import com.xcky.mapper.SysUserMapper;
import com.xcky.model.entity.NoticeDistributeInfo;
import com.xcky.model.req.NoticeDistributeInfoPageReq;
import com.xcky.model.req.NoticeDistributeInfoUpdateStatusReq;
import com.xcky.model.resp.NoticeDistributeInfoNumResp;
import com.xcky.service.NoticeDistributeInfoService;
import com.xcky.util.EntityUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息分发内容服务实现类
 *
 * @author lbchen
 */
@Service
public class NoticeDistributeInfoServiceImpl implements NoticeDistributeInfoService {
    @Autowired
    private NoticeDistributeInfoMapper noticeDistributeInfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public Integer updateNoticeDistributeInfo(NoticeDistributeInfo noticeDistributeInfo) {
        if(null == noticeDistributeInfo) {
            return 0;
        }
        boolean isInsert = false;
        Long id = noticeDistributeInfo.getId();
        if(null == id) {
            isInsert = true;
        } else {
            NoticeDistributeInfo noticeDistributeInfoTemp = getDetailByKey(id);
            if(null == noticeDistributeInfoTemp) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        if(isInsert) {
            noticeDistributeInfo.setCreateTime(nowDate);
            noticeDistributeInfo.setUpdateTime(nowDate);
            return noticeDistributeInfoMapper.insertNoticeDistributeInfo(noticeDistributeInfo);
        } else {
            noticeDistributeInfo.setUpdateTime(nowDate);
            return noticeDistributeInfoMapper.updateNoticeDistributeInfo(noticeDistributeInfo);
        }
    }
    
    @Override
    public NoticeDistributeInfo getDetailByKey(Long id) {
        Map<String,Object> map = new HashMap<>(2);
        map.put("id",id);
        List<NoticeDistributeInfo> list = noticeDistributeInfoMapper.selectNoticeDistributeInfosByMap(map);
        if(null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public PageInfo<NoticeDistributeInfo> getPageInfo(NoticeDistributeInfoPageReq noticeDistributeInfoPageReq) {
        Integer page = noticeDistributeInfoPageReq.getPage();
        Integer size = noticeDistributeInfoPageReq.getSize();
    
        Map<String,Object> map = EntityUtil.entityToMap(noticeDistributeInfoPageReq);
        
        Integer userId = noticeDistributeInfoPageReq.getUserId();
        if(null != userId) {
            // 查询该用户所属角色
            Map<String,Object> userMap = new HashMap<>(8);
            userMap.put("userId",userId);
            List<Integer> roleIds = sysUserMapper.selectRoleIdsByMap(userMap);
            if(null != roleIds && roleIds.size() > 0) {
                map.put("roleIds",roleIds);
            }
        }
        PageInfo<NoticeDistributeInfo> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            noticeDistributeInfoMapper.selectNoticeDistributeInfosByMap(map);
        });
        return pageInfo;
    }
    
    @Override
    public Integer updateNoticeDistributeInfoStatus(NoticeDistributeInfoUpdateStatusReq noticeDistributeInfoUpdateStatusReq) {
        Date nowDate = new Date();
        noticeDistributeInfoUpdateStatusReq.setUpdateTime(nowDate);
        return noticeDistributeInfoMapper.updateNoticeDistributeInfoStatus(noticeDistributeInfoUpdateStatusReq);
    }
    
    @Override
    public NoticeDistributeInfo getDetailByMap(Map<String, Object> detailMap) {
        List<NoticeDistributeInfo> list = noticeDistributeInfoMapper.selectDetailByMap(detailMap);
        if(null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<NoticeDistributeInfoNumResp> getNumByUserId(Integer userId) {
        Map<String,Object> map = new HashMap<>(4);
        if(null != userId) {
            map.put("userId",userId);
            // 查询该用户所属角色
            Map<String,Object> userMap = new HashMap<>(4);
            userMap.put("userId",userId);
            List<Integer> roleIds = sysUserMapper.selectRoleIdsByMap(userMap);
            if(null != roleIds && roleIds.size() > 0) {
                map.put("roleIds",roleIds);
            }
        }
        // 状态不包含已经删除的信息
        map.put("notStatus", NoticeDistributeInfoStatusEnum.DELETEED.getCode());
        List<NoticeDistributeInfoNumResp> list = noticeDistributeInfoMapper.selectNoticeDistributeInfoStatNumByMap(map);
        return list;
    }
}
