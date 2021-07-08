package com.yang.gulimall.product.dao;

import com.yang.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.gulimall.product.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);

    List<String> getSkuSaleAttrValuesAsString(@Param("skuId") Long skuId);
}
