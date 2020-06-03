package com.lazydsr.base.demo.basedemo.service.impl;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;

/**
 * UserServiceImplTest Description : TODO
 *
 * @author : daisenrong
 * @date : 2020/06/03 12:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    public static final ThreadPoolExecutor executor =
        new ThreadPoolExecutor(10, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @Test
    void insertUser() {
        Random random = new Random();
        for (int i = 0; i < 500000; i++) {

            ArrayList<User> users = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                User user = new User();
                user.setAge(random.nextInt(120));
                user.setName("name-" + System.currentTimeMillis() + "-" + random.nextInt(10000));
                users.add(user);
            }
            userRepository.saveAll(users);

        }
    }
}