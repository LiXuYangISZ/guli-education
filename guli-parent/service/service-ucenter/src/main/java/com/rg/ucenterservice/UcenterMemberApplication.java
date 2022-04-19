package com.rg.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/8 18:20
 */
@SpringBootApplication
@ComponentScan("com.rg")
@MapperScan("com.rg.ucenterservice.mapper")
@EnableDiscoveryClient
public class UcenterMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMemberApplication.class, args);
    }
}
