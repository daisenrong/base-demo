package com.lazydsr.base.demo.basedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class BaseDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseDemoApplication.class, args);
    }

}
