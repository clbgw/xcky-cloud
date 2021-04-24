package com.xcky.mapper;

import com.xcky.model.entity.NoticeDistributeInfo;
import com.xcky.model.req.NoticeDistributeInfoUpdateStatusReq;
import com.xcky.model.resp.NoticeDistributeInfoNumResp;

import java.util.List;
import java.util.Map;

/**
 * 消息分发内容对象映射接口
 *
 * @author lbchen
 */
public interface NoticeDistributeInfoMapper {
    /**
     * 新增消息分发内容
     *
     * @param noticeDistributeInfo 消息分发内容
     * @return 新增行数
     */
    Integer insertNoticeDistributeInfo(NoticeDistributeInfo noticeDistributeInfo);
    
    /**
     * 更新消息分发内容
     *
     * @param noticeDistributeInfo 消息分发内容
     * @return 更新行数
     */
    Integer updateNoticeDistributeInfo(NoticeDistributeInfo noticeDistributeInfo);
    
    /**
     * 根据消息分发内容更新状态请求参数更新消息分发内容状态
     *
     * @param noticeDistributeInfoUpdateStatusReq 消息分发内容更新状态请求参数
     * @return 更新行数
     */
    Integer updateNoticeDistributeInfoStatus(NoticeDistributeInfoUpdateStatusReq noticeDistributeInfoUpdateStatusReq);
    
    /**
     * 根据map条件获取消息分发内容列表
     *
     * @param map map条件
     * @return 消息分发内容列表
     */
    List<NoticeDistributeInfo> selectNoticeDistributeInfosByMap(Map<String, Object> map);
    
    /**
     * 根据map条件获取消息分发内容详情
     *
     * @param detailMap map条件
     * @return 消息分发内容详情
     */
    List<NoticeDistributeInfo> selectDetailByMap(Map<String, Object> detailMap);
    
    /**
     * 根据map条件统计消息分发内容各状态的数量
     *
     * @param map map条件
     * @return 消息分发内容各状态的数量
     */
    List<NoticeDistributeInfoNumResp> selectNoticeDistributeInfoStatNumByMap(Map<String, Object> map);
}
