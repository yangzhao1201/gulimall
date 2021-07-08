package com.yang.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author TangYuan
 * @create 2021--06--16--23:37
 * @description
 */
//open-feign 编写接口，调用远程服务
@EnableFeignClients(basePackages="com.yang.gulimall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class,args);
    }
}
