package com.yang.gulimall.product.dao;

import com.yang.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
