package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.SourceLog;
import com.xcky.model.resp.R;
import com.xcky.service.SourceLogService;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 源日志控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class SourceLogController {
    @Autowired
    private SourceLogService sourceLogService;

    /**
     * 保存源日志
     *
     * @param sourceLog 保存源日志
     * @return 基本响应对象
     */
    @PostMapping("/l/s")
    public R logSave(@RequestBody SourceLog sourceLog) {
        if (null == sourceLog) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Thread thread = new Thread(() -> {
            Integer saveResult = sourceLogService.saveSourceLog(sourceLog);
            if (null == saveResult || saveResult < 1) {
                log.error("保存日志失败:" + sourceLog.toLogString());
            }
        });
        thread.start();
        return ResponseUtil.ok();
    }
}
