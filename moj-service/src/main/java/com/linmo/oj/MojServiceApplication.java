package com.linmo.oj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.linmo.oj.mapper")
public class MojServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MojServiceApplication.class, args);
    }

}
