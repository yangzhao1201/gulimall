package com.yang.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.gulimall.product.dao.AttrGroupDao;
import com.yang.gulimall.product.dao.SkuSaleAttrValueDao;
import com.yang.gulimall.product.entity.BrandEntity;
import com.yang.gulimall.product.service.BrandService;

import com.yang.gulimall.product.vo.SkuItemSaleAttrVo;
import com.yang.gulimall.product.vo.SpuItemAttrGroupVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

//    @Autowired
//    OSSClient ossClient;
//
//    @Test
//    public void testUpload() throws FileNotFoundException {
//
//        // 上传文件流。
//        InputStream inputStream = new FileInputStream("/Users/yangzhao/Desktop/Tromso.jpeg");
//        ossClient.putObject("gulimallbucket-yz", "Tromso.jpeg", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//
//        System.out.println("上传完成...");
//    }


//    @Test
//    public void testUpload() throws FileNotFoundException {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-eu-central-1.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI5tCVMFRLbcKNv3UczB46";
//        String accessKeySecret = "5Jv311kVFEfLuS4GCje9G7SARyIuNE";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        // 上传文件流。
//        InputStream inputStream = new FileInputStream("/Users/yangzhao/Desktop/tangyuan.jpeg");
//        ossClient.putObject("gulimallbucket-yz", "tangyuan.jpeg", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//
//        System.out.println("上传完成...");
//    }

    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("huawei");
//        brandService.save(brandEntity);
//        System.out.println("测试保存成功");

//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("honor");
//        brandService.updateById(brandEntity);
//        System.out.println("测试修改成功");


        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>());
        list.forEach(System.out::println);
        System.out.println("测试查询成功");
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        //保存
        ops.set("hello","world"+ UUID.randomUUID().toString());

        //查询
        System.out.println(ops.get("hello"));
    }

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    public void testAttr() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(7L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);

        List<SkuItemSaleAttrVo> saleAttrsBySpuId = skuSaleAttrValueDao.getSaleAttrsBySpuId(7L);
        System.out.println(saleAttrsBySpuId);
    }

}
