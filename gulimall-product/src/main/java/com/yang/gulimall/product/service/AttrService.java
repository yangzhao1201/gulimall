package com.yang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.product.entity.AttrEntity;
import com.yang.gulimall.product.vo.AttrGroupRelationVo;
import com.yang.gulimall.product.vo.AttrRespVo;
import com.yang.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);


    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params);

    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

