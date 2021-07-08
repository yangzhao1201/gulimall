package com.yang.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.member.entity.MemberEntity;
import com.yang.gulimall.member.vo.MemberLoginVo;
import com.yang.gulimall.member.vo.MemberRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:30:41
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo registerVo);

    MemberEntity login(MemberLoginVo loginVo);
}

