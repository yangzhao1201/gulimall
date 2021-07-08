package com.yang.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author TangYuan
 * @create 2021--06--16--19:16
 * @description
 */

@EnableRedisHttpSession
@EnableCaching   //开启缓存
@EnableFeignClients(basePackages = "com.yang.gulimall.product.feign")
@MapperScan("com.yang.gulimall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class,args);
    }
}
