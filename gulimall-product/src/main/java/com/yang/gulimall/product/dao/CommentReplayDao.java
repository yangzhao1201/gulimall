package com.yang.gulimall.product.dao;

import com.yang.gulimall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
