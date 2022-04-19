package com.rg.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/16 15:15
 */
@SpringBootApplication
@ComponentScan("com.rg")  //扫描外部配置的注解==>此处主要是为了增加对swagger的配置
@EnableDiscoveryClient //服务注册和发现
@EnableFeignClients //表明自己是一个服务调用端
@MapperScan("com.rg.orderservice.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
