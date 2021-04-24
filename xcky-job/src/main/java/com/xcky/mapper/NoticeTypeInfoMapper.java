package com.xcky.mapper;

import com.xcky.model.entity.NoticeTypeInfo;

import java.util.List;
import java.util.Map;

/**
 * 消息类型对象映射接口
 *
 * @author lbchen
 */
public interface NoticeTypeInfoMapper {
    /**
     * 新增消息类型
     *
     * @param noticeTypeInfo 消息类型
     * @return 新增行数
     */
    Integer insertNoticeTypeInfo(NoticeTypeInfo noticeTypeInfo);

    /**
     * 更新消息类型
     *
     * @param noticeTypeInfo 消息类型
     * @return 更新行数
     */
    Integer updateNoticeTypeInfo(NoticeTypeInfo noticeTypeInfo);

    /**
     * 根据map条件查询消息类型列表
     *
     * @param map map条件
     * @return 消息类型列表
     */
    List<NoticeTypeInfo> seleteNoticeTypeByMap(Map<String, Object> map);

    /**
     * 根据主键列表删除消息类型
     *
     * @param list 主键列表
     * @return 删除记录数
     */
    Integer deleteNoticeTypeByIds(List<Long> list);
}
