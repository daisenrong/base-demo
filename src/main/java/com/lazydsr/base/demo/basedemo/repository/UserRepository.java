package com.lazydsr.base.demo.basedemo.repository;

import com.lazydsr.base.demo.basedemo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
