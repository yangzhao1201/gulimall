package com.yang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.product.entity.SpuInfoEntity;
import com.yang.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);

    SpuInfoEntity getSpuBySkuId(Long skuId);
}

