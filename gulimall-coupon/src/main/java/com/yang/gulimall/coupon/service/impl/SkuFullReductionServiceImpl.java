package com.yang.gulimall.coupon.service.impl;

import com.yang.common.to.MemberPrice;
import com.yang.common.to.SkuReductionTo;
import com.yang.gulimall.coupon.entity.MemberPriceEntity;
import com.yang.gulimall.coupon.entity.SkuLadderEntity;
import com.yang.gulimall.coupon.service.MemberPriceService;
import com.yang.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.Query;

import com.yang.gulimall.coupon.dao.SkuFullReductionDao;
import com.yang.gulimall.coupon.entity.SkuFullReductionEntity;
import com.yang.gulimall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReductionTo(SkuReductionTo skuReductionTo) {

        //SKU的优惠，满减等信息；sms_sku_ladder；sms_sku_full_reduction；sms_member_price
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        //skuLadderEntity.setPrice();
        skuLadderEntity.setAddOther(skuReductionTo.getFullCount());

        if(skuLadderEntity.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }

        //2.sms_sku_full_reduction
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        if(skuFullReductionEntity.getReducePrice().compareTo(new BigDecimal("0")) == 1){
            this.save(skuFullReductionEntity);
        }

        //3.sms_member_price
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = memberPrice.stream().map(item -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuLadderEntity.getSkuId());
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(item -> {
            return (item.getMemberPrice().compareTo(new BigDecimal("0")) == 1);
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(memberPriceEntities);

    }

}