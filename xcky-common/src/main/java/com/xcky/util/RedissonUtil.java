package com.xcky.util;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Redisson工具类
 *
 * @author lbchen
 */
@Component
public class RedissonUtil {
    /**
     * redis密码
     */
    @Value("${spring.redis.password}")
    private String password;
    /**
     * redis主机
     */
    @Value("${spring.redis.host}")
    private String host;
    /**
     * redis端口
     */
    @Value("${spring.redis.port}")
    private Integer port;

    /**
     * 配置分布式锁
     *
     * @return RedissonClient对象
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = getRedissonConfig(false);
        return Redisson.create(config);
    }

    /**
     * 获取redisson配置信息
     *
     * @param isCluster 是否集群配置
     * @return redisson配置信息
     */
    private Config getRedissonConfig(boolean isCluster) {
        Config config = new Config();
        if (isCluster) {
            //集群模式
            config.useClusterServers().setScanInterval(2000)
                    .addNodeAddress("redis://10.0.29.30:6379", "redis://10.0.29.95:6379")
                    .addNodeAddress("redis://127.0.0.1:6379");
        } else {
            //单机模式
            config.useSingleServer().setPassword(password).setAddress("redis://" + host + ":" + port);
        }
        return config;
    }

}
