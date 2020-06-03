package com.lazydsr.base.demo.basedemo.service.impl;

import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;
import com.lazydsr.base.demo.basedemo.service.UserService;
import com.lazydsr.base.demo.basedemo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Cacheable(value = "user",unless="#result == null")
    public Optional<User> get(Integer id) {
        // String userJson = redisTemplate.opsForValue().get(id.toString());
        // if (StringUtils.isNotBlank(userJson)){
        //     return Optional.ofNullable(JsonUtil.toBean(userJson, User.class));
        // }
        Optional<User> userOptional = userRepository.findById(id);
        // if (userOptional.isPresent()){
        //     redisTemplate.opsForValue().set(JsonUtil.toJson(userOptional.get()),);
        // }
        return userOptional;
    }

}
