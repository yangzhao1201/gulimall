package com.yang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.yang.gulimall.product.vo.SkuItemSaleAttrVo;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId);

    List<String> getSkuSaleAttrValuesAsString(Long skuId);
}

