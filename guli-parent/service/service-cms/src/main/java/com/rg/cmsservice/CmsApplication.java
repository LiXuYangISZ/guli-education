package com.rg.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/1 21:36
 */
@SpringBootApplication
@ComponentScan("com.rg")
@MapperScan("com.rg.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

}
