package com.yang.gulimall.order.dao;

import com.yang.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:41:29
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
