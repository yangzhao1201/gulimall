package com.yang.gulimall.auth.feign.fallback;


import com.yang.common.exception.BizCodeEnum;
import com.yang.common.utils.R;
import com.yang.gulimall.auth.feign.MemberFeignService;
import com.yang.gulimall.auth.vo.SocialUser;
import com.yang.gulimall.auth.vo.UserLoginVo;
import com.yang.gulimall.auth.vo.UserRegisterVo;
import org.springframework.stereotype.Service;

//@Service
//public class MemberFallbackService implements MemberFeignService {
//    @Override
//    public R register(UserRegisterVo registerVo) {
//        return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
//    }

//    @Override
//    public R login(UserLoginVo loginVo) {
//        return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
//    }
//
//    @Override
//    public R login(SocialUser socialUser) {
//        return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
//    }
//}
