package com.yang.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.order.entity.OrderEntity;
import com.yang.gulimall.order.vo.OrderConfirmVo;
import com.yang.gulimall.order.vo.OrderSubmitVo;
import com.yang.gulimall.order.vo.PayVo;
import com.yang.gulimall.order.vo.SubmitOrderResponseVo;

import java.util.Map;

/**
 * 订单
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:41:29
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder();

    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity orderEntity);

    PayVo getOrderPay(String orderSn);

    PageUtils getMemberOrderPage(Map<String, Object> params);
}

