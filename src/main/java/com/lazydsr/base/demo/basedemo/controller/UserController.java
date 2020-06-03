package com.lazydsr.base.demo.basedemo.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lazydsr.base.demo.basedemo.common.Response;
import com.lazydsr.base.demo.basedemo.common.ResponseUtils;
import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;
import com.lazydsr.base.demo.basedemo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("get")
    public Response get(Integer id) {

        return ResponseUtils.success(userService.get(id).orElse(null));
    }

    @Autowired
    private UserRepository userRepository;

    public static final ThreadPoolExecutor executor =
        new ThreadPoolExecutor(20, 20, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @GetMapping("data")
    public Response insertUser() {
        Random random = new Random();
        for (int i = 0; i < 2000; i++) {

            CompletableFuture.runAsync(() -> {
                ArrayList<User> users = new ArrayList<>(1000);
                for (int j = 0; j < 1000; j++) {

                    User user = new User();
                    user.setAge(random.nextInt(120));
                    user.setName("name-" + System.currentTimeMillis() + "-" + random.nextInt(10000));
                    users.add(user);
                }
                userRepository.saveAll(users);
            }, executor);

        }
        return ResponseUtils.success(userRepository.count());
    }

}
