package com.xcky.service.impl;


import com.xcky.mapper.SourceLogMapper;
import com.xcky.model.entity.SourceLog;
import com.xcky.service.SourceLogService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 源日志服务实现类
 *
 * @author lbchen
 */
@Service
public class SourceLogServiceImpl implements SourceLogService {
    @Autowired
    private SourceLogMapper sourceLogMapper;
    @Override
    public Integer saveSourceLog(SourceLog sourceLog) {
        if(null == sourceLog) {
            return 0;
        }
        Date nowDate = new Date();
        sourceLog.setCreateTime(nowDate);
        sourceLog.setRecordDate(nowDate);
        return sourceLogMapper.insertSourceLog(sourceLog);
    }
}
