package com.yang.gulimall.product.App;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.gulimall.product.entity.BrandEntity;
import com.yang.gulimall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yang.gulimall.product.entity.CategoryBrandRelationEntity;
import com.yang.gulimall.product.service.CategoryBrandRelationService;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId",required = true) Long catId){
        List<BrandEntity> vos=categoryBrandRelationService.getBrandsById(catId);
        List<BrandVo> brandVos = vos.stream().map(item -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(item.getBrandId());
            brandVo.setBrandName(item.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data",brandVos);
    }

    /**
     * 获取当前分类关联列表
     */
    @GetMapping("catelog/list")
    public R cateloglist(@RequestParam Long brandId){
        QueryWrapper<CategoryBrandRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brandId);
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(queryWrapper);
        return R.ok().put("data", data);
    }
    /**
     * 保存当前分类关联信息
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
//		categoryBrandRelationService.save(categoryBrandRelation);
        categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("product:categorybrandrelation:save")
//    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
//		categoryBrandRelationService.save(categoryBrandRelation);
//
//        return R.ok();
//    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
