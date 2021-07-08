package com.yang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.yang.gulimall.product.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrRelations(List<AttrGroupRelationVo> vos);

}

