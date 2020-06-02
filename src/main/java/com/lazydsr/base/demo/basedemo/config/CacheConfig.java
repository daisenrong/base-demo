package com.lazydsr.base.demo.basedemo.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {

    // @Autowired
    // private ResourceLoader resourceLoader;
    //
    // @Bean
    // public RedisCacheConfiguration cacheManager() {
    // // RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
    // RedisCacheConfiguration redisCacheConfiguration =
    // RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader());
    // // 设置缓存过期时间
    // redisCacheConfiguration.entryTtl(Duration.ofSeconds(10));
    // return redisCacheConfiguration;
    // }

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public RedisCacheManager accountCacheManager(RedisTemplate<String, Object> accountRedisTemplate) {
        RedisCacheConfiguration redisCacheConfiguration =
            RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader());
        redisCacheConfiguration.entryTtl(Duration.ofSeconds(10));
        RedisCacheManager cacheManager = new RedisCacheManager(
            RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory), redisCacheConfiguration);
        return cacheManager;
    }

}
