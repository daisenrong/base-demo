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

}
