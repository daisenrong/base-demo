package com.lazydsr.base.demo.basedemo.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.lazydsr.base.demo.basedemo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;
import reactor.core.publisher.Flux;

/**
 * UserServiceImplTest Description : TODO
 *
 * @author : daisenrong
 * @date : 2020/06/03 12:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void hashTest(){
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Page<User> page = userRepository.findAll(PageRequest.of(i, 1000));
            for (User user:page.getContent()){
                redisTemplate.opsForHash().putAll("user:"+user.getId(), JsonUtil.toMap(JsonUtil.toJson(user)));
                redisTemplate.expire("user:"+user.getId(), Duration.ofSeconds(3000+random.nextInt(600)));
            }

        }
    }

    @Test
    public void bitTest(){
       redisTemplate.opsForValue().setBit("bit:user",2,true);
       redisTemplate.opsForValue().setBit("bit:user",20000000,true);
       log.info("bit:user = {}",redisTemplate.opsForValue().getBit("bit:user",2));
       log.info("bit:user = {}",redisTemplate.opsForValue().getBit("bit:user",20000000));
       log.info("bit:user = {}",redisTemplate.opsForValue().getBit("bit:user",1000000000));
    }

    @Test
    public void delKey(){
        redisTemplate.delete("bit:user");

    }


}