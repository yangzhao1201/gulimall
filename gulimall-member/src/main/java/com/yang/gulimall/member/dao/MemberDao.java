package com.yang.gulimall.member.dao;

import com.yang.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:30:41
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
