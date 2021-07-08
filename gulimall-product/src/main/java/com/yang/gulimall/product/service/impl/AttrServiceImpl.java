package com.yang.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yang.common.constant.ProductConstant;
import com.yang.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.yang.gulimall.product.dao.AttrGroupDao;
import com.yang.gulimall.product.dao.CategoryDao;
import com.yang.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.yang.gulimall.product.entity.AttrGroupEntity;
import com.yang.gulimall.product.entity.CategoryEntity;
import com.yang.gulimall.product.service.AttrAttrgroupRelationService;
import com.yang.gulimall.product.service.CategoryService;
import com.yang.gulimall.product.vo.AttrGroupRelationVo;
import com.yang.gulimall.product.vo.AttrRespVo;
import com.yang.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.Query;

import com.yang.gulimall.product.dao.AttrDao;
import com.yang.gulimall.product.entity.AttrEntity;
import com.yang.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationService relationService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);

        this.save(attrEntity);

        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()&& attr.getAttrGroupId()!=null) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type","base".equalsIgnoreCase(attrType)
                        ?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        :ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if (catelogId != 0){
            queryWrapper.eq("catelog_id",catelogId);
        }

        if (!StringUtils.isEmpty(key)){
            queryWrapper.and((obj)->{
                obj.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            if ("base".equalsIgnoreCase(attrType)) {
                AttrAttrgroupRelationEntity attrId =
                        relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrEntity.getAttrId()));

                if (attrId != null && attrId.getAttrGroupId()!=null) {
                    Long attrGroupId = attrId.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo respVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, respVo);

        AttrAttrgroupRelationEntity attrgroupRelation =
                relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //1. 设置分组信息
            if (null != attrgroupRelation) {
                Long attrGroupId = attrgroupRelation.getAttrGroupId();
                respVo.setAttrGroupId(attrGroupId);
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                if (null != attrGroupEntity) {
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }
        //2. 设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        respVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (null != categoryEntity) {
            respVo.setCatelogName(categoryEntity.getName());
        }
        return respVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //修改关联分组
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attr.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            UpdateWrapper<AttrAttrgroupRelationEntity> updateWrapper = new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId());

            Integer count = relationDao.selectCount(updateWrapper);
            if (count > 0) {
                relationDao.update(relationEntity, updateWrapper);
            } else {
                relationDao.insert(relationEntity);
            }
        }

    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper
                = new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId);
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(queryWrapper);

        List<AttrEntity> entityList = relationEntities.stream().map(attr -> {
            Long attrId = attr.getAttrId();

            return this.getById(attrId);
        }).filter(attrEntity -> {
            return attrEntity != null;
        }).collect(Collectors.toList());

        return entityList;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelationEntity> entityList =
                Arrays.asList(vos).stream().map(param -> {

            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(param, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        relationDao.deleteBatchRelation(entityList);
    }

    @Override
    public PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params) {

        //1. 当前分组只能关联自己所属的分类的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();

        //2. 当前分组只能关联别的分组没有引用的属性
        //2.1)、当前分类下的其他属性分组
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
//                .ne("attr_group_id", attrgroupId));

        List<Long> collect = group.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        //2.2)、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id",collect));
        List<Long> attrIds = entities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3)、从当前分类的所有属性中移除这些属性
        QueryWrapper<AttrEntity> queryWrapper =
                new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId)
                        .eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if(null != attrIds && attrIds.size() > 0){
            queryWrapper.notIn("attr_id", attrIds);
        }

        String key = (String)params.get("key");
        if(StringUtils.isEmpty(key)){
            queryWrapper.and(w ->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils pageUtils = new PageUtils(page);


        return pageUtils;
    }

    @Override
    public List<Long> selectSearchAttrIds(List<Long> attrIds) {
        return baseMapper.selectSearchAttrIds(attrIds);
    }

}