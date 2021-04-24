package com.xcky.mapper;

import com.xcky.model.entity.NoticeDistributeConfig;
import com.xcky.model.resp.NoticeDistributeConfigResp;

import java.util.List;
import java.util.Map;

/**
 * 消息分发配置对象映射接口
 *
 * @author lbchen
 */
public interface NoticeDistributeConfigMapper {
    /**
     * 新增消息分发配置
     *
     * @param noticeDistributeConfig 消息分发配置
     * @return 新增行数
     */
    Integer insertNoticeDistributeConfig(NoticeDistributeConfig noticeDistributeConfig);
    
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
     * 根据map条件查询消息分发配置列表
     *
     * @param map map条件
     * @return 消息分发配置对象列表
     */
    List<NoticeDistributeConfig> selectNoticeDistributeConfigByMap(Map<String, Object> map);
    
    /**
     * 根据map条件查询消息分发配置响应对象列表
     *
     * @param map map条件
     * @return 消息分发配置响应对象列表
     */
    List<NoticeDistributeConfigResp> selectNoticeDistributeConfigRespByMap(Map<String, Object> map);
}
