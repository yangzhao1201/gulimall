package com.yang.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.yang.gulimall.thirdparty.component.SmsComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

//    @Autowired
//    OSSClient ossClient;
//
//    @Test
//    public void testUpload() throws FileNotFoundException {
//
//        // 上传文件流。
//        InputStream inputStream = new FileInputStream("/Users/yangzhao/Desktop/niuniu.jpeg");
//        ossClient.putObject("gulimallbucket-yz", "niuniu.jpeg", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//
//        System.out.println("上传完成...");
//    }



//    @Autowired
//    SmsComponent smsComponent;
//    @Test
//    public void testSms(){
//        smsComponent.sendCode("18829693233","6666");
//    }

}
