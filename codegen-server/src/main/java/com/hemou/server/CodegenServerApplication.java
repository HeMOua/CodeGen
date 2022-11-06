package com.hemou.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.hemou.server.mapper")
@SpringBootApplication
public class CodegenServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodegenServerApplication.class, args);
    }

}
