package com.yang.gulimall.ware.feign;


import com.yang.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-order")
public interface OrderFeignService {
    @RequestMapping("order/order/infoByOrderSn/{OrderSn}")
    R infoByOrderSn(@PathVariable("OrderSn") String OrderSn);
}
