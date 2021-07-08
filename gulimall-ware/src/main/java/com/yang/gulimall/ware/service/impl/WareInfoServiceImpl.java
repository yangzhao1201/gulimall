package com.yang.gulimall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.yang.common.utils.R;
import com.yang.gulimall.ware.feign.MemberFeignService;
import com.yang.gulimall.ware.vo.FareVo;
import com.yang.gulimall.ware.vo.MemberAddressVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.Query;

import com.yang.gulimall.ware.dao.WareInfoDao;
import com.yang.gulimall.ware.entity.WareInfoEntity;
import com.yang.gulimall.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        String key=(String)params.get("key");
        if(StringUtils.isNotEmpty(key)){
            queryWrapper.eq("id",key)
                    .or().like("name",key)
                    .or().like("address",key)
                    .or().like("areacode",key);
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R info = memberFeignService.info(addrId);
        if (info.getCode() == 0) {
            MemberAddressVo address = info.getData("memberReceiveAddress", new TypeReference<MemberAddressVo>() {
            });
            fareVo.setAddress(address);
            String phone = address.getPhone();
            //取电话号的最后两位作为邮费
            String fare = phone.substring(phone.length() - 2, phone.length());
            fareVo.setFare(new BigDecimal(fare));
        }
        return fareVo;
    }

}