package com.lazydsr.base.demo.basedemo.controller;

import com.lazydsr.base.demo.basedemo.entity.User;
import com.lazydsr.base.demo.basedemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lazydsr.base.demo.basedemo.common.Response;
import com.lazydsr.base.demo.basedemo.common.ResponseUtil;
import com.lazydsr.base.demo.basedemo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("get")
    public Response get(Integer id) {

        return ResponseUtil.success(userService.get(id));
    }

    @GetMapping("init")
    public Response init() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {

            List<User> users = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                User user = new User();
                user.setAge(random.nextInt(120));
                user.setName("name-"+System.currentTimeMillis()+"-"+random.nextInt(1000000));
                users.add(user);
            }
            CompletableFuture.runAsync(()->{
                userRepository.saveAll(users);
            });
        }
        return ResponseUtil.success();
    }

}
