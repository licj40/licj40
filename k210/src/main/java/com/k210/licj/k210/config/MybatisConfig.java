package com.k210.licj.k210.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan("com.k210.licj.k210.mapper")
public class MybatisConfig {
}
