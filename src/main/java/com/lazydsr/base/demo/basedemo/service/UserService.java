package com.lazydsr.base.demo.basedemo.service;

import com.lazydsr.base.demo.basedemo.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> get(Integer id);
}
