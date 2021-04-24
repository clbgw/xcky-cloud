package com.xcky.mapper;

import com.xcky.model.entity.NoticeInfo;
import com.xcky.model.req.NoticeInfoDeleteReq;

import java.util.List;
import java.util.Map;

/**
 * 消息内容对象映射接口
 *
 * @author lbchen
 */
public interface NoticeInfoMapper {
    /**
     * 新增消息内容
     *
     * @param noticeInfo 消息内容
     * @return 新增行数
     */
    Integer insertNoticeInfo(NoticeInfo noticeInfo);
    
    /**
     * 更新消息内容
     *
     * @param noticeInfo 消息内容
     * @return 更新行数
     */
    Integer updateNoticeInfo(NoticeInfo noticeInfo);
    
    /**
     * 删除消息内容
     *
     * @param noticeInfoDeleteReq 消息内容删除请求参数
     * @return 更新行数
     */
    Integer deleteNoticeInfo(NoticeInfoDeleteReq noticeInfoDeleteReq);
    
    /**
     * 根据map条件查询消息信息列表
     *
     * @param map map条件
     * @return 消息信息列表
     */
    List<NoticeInfo> selectNoticeInfosByMap(Map<String, Object> map);
}
