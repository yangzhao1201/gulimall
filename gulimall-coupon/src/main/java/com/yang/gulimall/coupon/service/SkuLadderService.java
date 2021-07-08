package com.yang.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 22:55:39
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

