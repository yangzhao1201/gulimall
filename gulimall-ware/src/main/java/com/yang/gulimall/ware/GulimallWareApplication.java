package com.yang.gulimall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author TangYuan
 * @create 2021--06--16--23:52
 * @description
 */

@EnableRabbit
@EnableFeignClients
@EnableTransactionManagement
@MapperScan("com.yang.gulimall.ware.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallWareApplication.class,args);
    }
}
