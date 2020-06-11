package com.lazydsr.base.demo.basedemo.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.BitSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;
import com.lazydsr.base.demo.basedemo.service.UserService;
import com.lazydsr.base.demo.basedemo.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

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
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void getTest() {
        int count = 0;
        for (int i = 10001; i < 100000; i++) {
            Optional<User> user = userService.get(i);
            if (user.isPresent()) {
                count++;
            }
        }
        System.out.println("==========" + count);
    }

    @Test
    public void hashTest() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Page<User> page = userRepository.findAll(PageRequest.of(i, 1000));
            for (User user : page.getContent()) {
                redisTemplate.opsForHash().putAll("user:" + user.getId(), JsonUtil.toMap(JsonUtil.toJson(user)));
                redisTemplate.expire("user:" + user.getId(), Duration.ofSeconds(3000 + random.nextInt(600)));
            }
        }
    }

    @Test
    public void bitTest() {
        redisTemplate.opsForValue().setBit("bit:user", 2, true);
        redisTemplate.opsForValue().setBit("bit:user", 2000000, true);
        log.info("bit:user = {}", redisTemplate.opsForValue().getBit("bit:user", 2));
        log.info("bit:user = {}", redisTemplate.opsForValue().getBit("bit:user", 20000000));
        log.info("bit:user = {}", redisTemplate.opsForValue().getBit("bit:user", 1000000000));
    }

    @Test
    public void delKey() {
        redisTemplate.delete("user:bloom");

    }

    @Test
    public void initBloom() {
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForValue().setBit("user:bloom", i, true);
        }
    }

    @Test
    public void userLoginInfo() {
        Random random = new Random();
        String key = "user:bit:login:" + LocalDate.now().plusDays(-2);
        for (int i = 1; i <= 1000; i++) {
            redisTemplate.opsForValue().setBit(key, random.nextInt(1000000), true);
        }
        RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        Long aLong = connection.bitCount(key.getBytes());
        System.out.println(aLong);
    }

    @Test
    public void uniqueCount() {
        String key = "user:bit:login:" + LocalDate.now();
        RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        BitSet bitSet = BitSet.valueOf(Objects.requireNonNull(connection.get(key.getBytes())));

        System.out.println("当日活跃用户数量：" + bitSet.cardinality());
        // 活跃的用户id
        for (int i = 0; i < bitSet.length(); i++) {
            boolean b = bitSet.get(i);
            if (b) {
                System.out.println("index   =" + i);
            }
        }
    }

    @Test
    public void uniqueCount0() {
        String[] keys =new String[4];
        RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        BitSet all = new BitSet();
        for (int i = 0; i < 4; i++) {
            keys[i] = "user:bit:login:" + LocalDate.now().plusDays(-i);
            BitSet bitSet = BitSet.valueOf(Objects.requireNonNull(connection.get(keys[i].getBytes())));
            all.or(bitSet);
        }
        System.out.println("近四天活跃用户数量：" + all.cardinality());
        // 活跃的用户id
        for (int i = 0; i < all.length(); i++) {
            boolean b = all.get(i);
            if (b) {
                System.out.println("index   =" + i);
            }
        }
    }

    @Test
    public void insertUser() {
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            User user = new User();

            user.setAge(random.nextInt(120));
            user.setName("name-"+System.currentTimeMillis()+"-"+random.nextInt(1000000));

            userRepository.save(user);
        }
    }

}