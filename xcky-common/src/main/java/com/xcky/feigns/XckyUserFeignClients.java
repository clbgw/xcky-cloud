package com.xcky.feigns;

import com.xcky.model.entity.SourceLog;
import com.xcky.model.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务FeignClients
 *
 * @author lbchen
 */
@FeignClient(name = "xcky-user")
public interface XckyUserFeignClients {
    /**
     * 保存日志
     *
     * @param sourceLog 源日志实体类
     * @return 保存结果
     */
    @PostMapping("/l/s")
    R logSave(@RequestBody SourceLog sourceLog);
}
