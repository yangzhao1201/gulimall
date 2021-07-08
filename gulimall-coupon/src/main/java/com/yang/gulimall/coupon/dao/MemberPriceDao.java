package com.yang.gulimall.coupon.dao;

import com.yang.gulimall.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 22:55:39
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
