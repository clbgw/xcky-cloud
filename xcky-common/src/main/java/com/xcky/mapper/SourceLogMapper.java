package com.xcky.mapper;

import com.xcky.model.entity.SourceLog;

/**
 * 源日志对象映射接口
 * @author lbchen
 */
public interface SourceLogMapper {
    /**
     * 新增源日志
     *
     * @param sourceLog 源日志实体类
     * @return
     */
    Integer insertSourceLog(SourceLog sourceLog);
}
