package com.yang.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yang.gulimall.product.dao.BrandDao;
import com.yang.gulimall.product.dao.CategoryDao;
import com.yang.gulimall.product.entity.BrandEntity;
import com.yang.gulimall.product.entity.CategoryEntity;
import com.yang.gulimall.product.service.BrandService;
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

import com.yang.gulimall.product.dao.CategoryBrandRelationDao;
import com.yang.gulimall.product.entity.CategoryBrandRelationEntity;
import com.yang.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    BrandDao brandDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationDao relationDao;

    @Autowired
    BrandService brandService;

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    //由于从表单中只能获取分类、品牌的id，因此需要从数据库将其name字段查出，并保存至关联表
    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();

        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        relationEntity.setBrandId(brandId);
        relationEntity.setBrandName(name);
        this.update(relationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public List<BrandEntity> getBrandsById(Long catId) {
        List<CategoryBrandRelationEntity> entityList = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        //下面的封装是为了以后的重用
        List<BrandEntity> collect = entityList.stream().map(relationEntity -> {
            Long brandId = relationEntity.getBrandId();
            return brandService.getById(brandId);
        }).collect(Collectors.toList());

        return collect;
    }

}