package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.NoticeDistributeInfo;
import com.xcky.model.req.NoticeDistributeInfoPageReq;
import com.xcky.model.req.NoticeDistributeInfoUpdateStatusReq;
import com.xcky.model.resp.NoticeDistributeInfoNumResp;

import java.util.List;
import java.util.Map;

/**
 * 消息分发内容服务接口
 *
 * @author lbchen
 */
public interface NoticeDistributeInfoService {
    /**
     * 更新消息分发内容
     *
     * @param noticeDistributeInfo 消息分发内容
     * @return 更新行数
     */
    Integer updateNoticeDistributeInfo(NoticeDistributeInfo noticeDistributeInfo);
    
    /**
     * 获取消息分发内容详情
     *
     * @param id 主键ID
     * @return 消息分发内容
     */
    NoticeDistributeInfo getDetailByKey(Long id);
    
    /**
     * 根据消息分发内容分页请求参数获取分页列表
     *
     * @param noticeDistributeInfoPageReq 消息分发内容分页请求参数
     * @return 消息分发内容分页列表
     */
    PageInfo<NoticeDistributeInfo> getPageInfo(NoticeDistributeInfoPageReq noticeDistributeInfoPageReq);
    
    /**
     * 根据更新状态请求参数更新消息分发内容状态
     *
     * @param noticeDistributeInfoUpdateStatusReq 消息分发内容更新状态请求参数
     * @return 更新行数
     */
    Integer updateNoticeDistributeInfoStatus(NoticeDistributeInfoUpdateStatusReq noticeDistributeInfoUpdateStatusReq);
    
    /**
     * 根据map条件获取消息分发内容详情
     *
     * @param detailMap 查询详情map条件
     * @return 消息分发内容
     */
    NoticeDistributeInfo getDetailByMap(Map<String, Object> detailMap);
    
    /**
     * 统计该用户消息分发内容的各状态的数量
     *
     * @param userId 用户ID
     * @return 各消息分发内容状态的数量
     */
    List<NoticeDistributeInfoNumResp> getNumByUserId(Integer userId);
}
