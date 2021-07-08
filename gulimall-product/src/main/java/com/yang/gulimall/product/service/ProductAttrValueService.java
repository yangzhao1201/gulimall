package com.yang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttrValueEntities(List<ProductAttrValueEntity> productAttrValueEntities);

    List<ProductAttrValueEntity> baseAttrListforspu(long spuId);

    void updateSpuAttr(long spuId, List<ProductAttrValueEntity> entities);
}

