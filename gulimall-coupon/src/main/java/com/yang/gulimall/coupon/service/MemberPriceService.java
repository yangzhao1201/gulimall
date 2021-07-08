package com.yang.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 22:55:39
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

