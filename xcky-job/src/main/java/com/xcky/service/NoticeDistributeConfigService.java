package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.NoticeDistributeConfig;
import com.xcky.model.req.NoticeDistributeConfigPageReq;
import com.xcky.model.req.NoticeDistributeConfigRespPageReq;
import com.xcky.model.resp.NoticeDistributeConfigResp;

import java.util.Map;

/**
 * 消息分发配置服务接口
 *
 * @author lbchen
 */
public interface NoticeDistributeConfigService {
    /**
     * 更新消息分发配置
     *
     * @param noticeDistributeConfig 消息分发配置
     * @return 更新行数
     */
    Integer updateNoticeDistributeConfig(NoticeDistributeConfig noticeDistributeConfig);
    
    /**
     * 根据map条件删除消息分发配置
     *
     * @param map map条件
     * @return 更新行数
     */
    Integer deleteNoticeDistributeConfigByMap(Map<String, Object> map);
    
    /**
     * 查询消息分发配置详情
     *
     * @param id 主键ID
     * @return 消息分发配置
     */
    NoticeDistributeConfig getNoticeDistributeConfigDetailByKey(Long id);
    
    /**
     * 根据请求参数获取消息分发配置分页列表
     *
     * @param noticeDistributeConfigPageReq 消息分发配置分页请求参数
     * @return 消息分发配置分页列表
     */
    PageInfo<NoticeDistributeConfig> getPageInfo(NoticeDistributeConfigPageReq noticeDistributeConfigPageReq);
    
    /**
     * 根据请求参数获取消息分发配置响应对象分页列表
     *
     * @param noticeDistributeConfigRespPageReq 消息分发配置响应对象分页请求参数
     * @return 消息分发配置响应对象分页列表
     */
    PageInfo<NoticeDistributeConfigResp> getRespPageInfo(NoticeDistributeConfigRespPageReq noticeDistributeConfigRespPageReq);
}
