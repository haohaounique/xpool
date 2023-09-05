package com.xpool;

import com.xpool.config.DBThreadPoolInit;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * description: config threadPool through database
 */
@SpringBootApplication
@MapperScan(basePackages={"com.xpool.mapper"})
@Import(DBThreadPoolInit.class)
public class XPoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(XPoolApplication.class, args);
    }
}
