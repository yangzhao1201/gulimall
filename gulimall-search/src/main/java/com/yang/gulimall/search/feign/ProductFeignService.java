package com.yang.gulimall.search.feign;

import com.yang.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TangYuan
 * @create 2021--06--28--05:00
 * @description
 */

@FeignClient("gulimall-product")
public interface ProductFeignService {

    @RequestMapping("product/attr/info/{attrId}")
    R info(@PathVariable("attrId") Long attrId);
}
