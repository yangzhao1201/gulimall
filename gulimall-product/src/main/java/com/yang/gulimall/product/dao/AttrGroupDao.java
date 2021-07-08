package com.yang.gulimall.product.dao;

import com.yang.gulimall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.gulimall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
