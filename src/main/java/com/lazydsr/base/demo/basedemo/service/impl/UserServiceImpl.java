package com.lazydsr.base.demo.basedemo.service.impl;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;
import com.lazydsr.base.demo.basedemo.service.UserService;
import com.lazydsr.base.demo.basedemo.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final String REDIS_PRXFIX_USER_BLOOM = "user:bloom";
    public static final String REDIS_PRXFIX_USER = "user:id:";

    @Override
    public Optional<User> get(Integer id) {
        if (Objects.isNull(id) || id < 0) {
            return Optional.empty();
        }
        String userJson = (String)redisTemplate.opsForValue().get(REDIS_PRXFIX_USER + id);
        if (StringUtils.isNotBlank(userJson)) {
            return Optional.ofNullable(JsonUtil.toBean(userJson, User.class));
        }
        // 如果不存在，那么使用布隆过滤器进行过滤，决定是否查询数据库
        Boolean bit = redisTemplate.opsForValue().getBit(REDIS_PRXFIX_USER_BLOOM, id);
        if (Objects.isNull(bit) || !bit) {
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(user -> redisTemplate.opsForValue().set(REDIS_PRXFIX_USER + id, JsonUtil.toJson(user),
            Duration.ofSeconds(600)));
        return userOptional;
    }

}
