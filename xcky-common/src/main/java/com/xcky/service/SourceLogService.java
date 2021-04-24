package com.xcky.service;

import com.xcky.model.entity.SourceLog;

/**
 * 源日志服务接口
 *
 * @author lbchen
 */
public interface SourceLogService {
    /**
     * 新增源日志
     *
     * @param sourceLog 源日志实体类
     * @return 新增行数
     */
    Integer saveSourceLog(SourceLog sourceLog);
}
