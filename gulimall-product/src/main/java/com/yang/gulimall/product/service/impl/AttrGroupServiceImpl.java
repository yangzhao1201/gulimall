package com.yang.gulimall.product.service.impl;

import com.yang.gulimall.product.entity.AttrEntity;
import com.yang.gulimall.product.service.AttrService;
import com.yang.gulimall.product.vo.AttrGroupWithAttrVo;
import com.yang.gulimall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.Query;

import com.yang.gulimall.product.dao.AttrGroupDao;
import com.yang.gulimall.product.entity.AttrGroupEntity;
import com.yang.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if (!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }

        if (catelogId == 0){
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }else {
            wrapper.eq("catelog_id",catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //?????????????????????????????????????????????
        List<AttrGroupEntity> attrGroupEntities = baseMapper.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));

        //????????????????????????????????????????????????
        List<AttrGroupWithAttrVo> attrGroupWithAttrVos = attrGroupEntities.stream().map(item -> {
            AttrGroupWithAttrVo attrGroupWithAttrVo = new AttrGroupWithAttrVo();
            BeanUtils.copyProperties(item, attrGroupWithAttrVo);
            List<AttrEntity> relationAtr = attrService.getRelationAttr(attrGroupWithAttrVo.getAttrGroupId());
            attrGroupWithAttrVo.setAttrs(relationAtr);
            return attrGroupWithAttrVo;
        }).collect(Collectors.toList());


        return attrGroupWithAttrVos;
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
        //1.???????????????SPU???????????????????????????????????????????????????????????????????????????????????????
        return this.baseMapper.getAttrGroupWithAttrsBySpuId(spuId,catalogId);
    }

}