package com.xpool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: config threadPool through database
 */
@SpringBootApplication
@MapperScan(basePackages={"com.xpool.mapper"})
public class XPoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(XPoolApplication.class, args);
    }
}
