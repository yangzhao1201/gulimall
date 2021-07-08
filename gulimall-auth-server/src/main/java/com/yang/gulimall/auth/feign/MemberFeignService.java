package com.yang.gulimall.auth.feign;


import com.yang.common.utils.R;
import com.yang.gulimall.auth.vo.SocialUser;
import com.yang.gulimall.auth.vo.UserLoginVo;
import com.yang.gulimall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//,fallback = MemberFallbackService.class
@FeignClient(value = "gulimall-member")
public interface MemberFeignService {

    @RequestMapping("member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    @RequestMapping("member/member/login")
     R login(@RequestBody UserLoginVo loginVo);

//    @RequestMapping("member/member/oauth2/login")
//    R login(@RequestBody SocialUser socialUser);
}
