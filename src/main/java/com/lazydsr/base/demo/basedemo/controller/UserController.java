package com.lazydsr.base.demo.basedemo.controller;

import com.lazydsr.base.demo.basedemo.common.Response;
import com.lazydsr.base.demo.basedemo.common.ResponseUtils;
import com.lazydsr.base.demo.basedemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("get")
    public Response get(Integer id){

        return ResponseUtils.success(userService.get(id).orElse(null));
    }
}
