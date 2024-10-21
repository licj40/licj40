package com.k210.licj.k210;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.k210.licj.k210.mapper")
@EnableScheduling
public class K210Application {

    public static void main(String[] args) {
        SpringApplication.run(K210Application.class, args);
    }

}
