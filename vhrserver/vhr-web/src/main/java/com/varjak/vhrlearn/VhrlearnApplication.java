package com.varjak.vhrlearn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.varjak.vhrlearn.mapper")
public class VhrlearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(VhrlearnApplication.class, args);
    }

}
