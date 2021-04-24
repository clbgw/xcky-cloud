package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.NoticeInfo;
import com.xcky.model.req.NoticeInfoDeleteReq;
import com.xcky.model.req.NoticeInfoPageReq;

/**
 * 消息信息服务接口
 *
 * @author lbchen
 */
public interface NoticeInfoService {
    /**
     * 更新消息信息
     *
     * @param noticeInfo 消息信息
     * @return 更新行数
     */
    Integer updateNoticeInfo(NoticeInfo noticeInfo);
    
    /**
     * 删除消息信息
     *
     * @param noticeInfoDeleteReq 消息信息删除请求对象
     * @return 删除行数
     */
    Integer deleteNoticeInfoByMap(NoticeInfoDeleteReq noticeInfoDeleteReq);
    
    /**
     * 获取消息信息详情
     *
     * @param id 主键ID
     * @return 消息信息
     */
    NoticeInfo getDetail(Long id);
    
    /**
     * 根据消息信息分页请求参数获取消息信息分页列表
     *
     * @param noticeInfoPageReq 消息信息分页请求参数
     * @return 消息信息分页列表
     */
    PageInfo<NoticeInfo> getPageInfo(NoticeInfoPageReq noticeInfoPageReq);
}
