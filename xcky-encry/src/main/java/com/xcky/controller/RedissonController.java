package com.xcky.controller;

import com.xcky.model.resp.R;
import com.xcky.util.RedissonUtil;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redisson控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class RedissonController {
    @Autowired
    private RedissonUtil redissonUtil;

    /**
     * 设置锁
     *
     * @param lockStr 锁的钥匙
     * @return 返回上锁成功
     */
    @GetMapping("/lock/{lockStr}")
    public R setLock(@PathVariable("lockStr") String lockStr) {
        RLock rLock = redissonUtil.redissonClient().getLock(lockStr);
        rLock.lock();
        log.info(lockStr + "上锁成功!");
        if (rLock.isLocked()) {
            rLock.unlock();
            return ResponseUtil.ok(lockStr + "解锁成功!");
        } else {
            return ResponseUtil.ok(lockStr + "未上锁!");
        }
    }
}
