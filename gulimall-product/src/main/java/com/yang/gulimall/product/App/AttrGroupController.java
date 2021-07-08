package com.yang.gulimall.product.App;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.yang.gulimall.product.entity.AttrEntity;
import com.yang.gulimall.product.service.AttrAttrgroupRelationService;
import com.yang.gulimall.product.service.AttrService;
import com.yang.gulimall.product.service.CategoryService;
import com.yang.gulimall.product.vo.AttrGroupRelationVo;
import com.yang.gulimall.product.vo.AttrGroupWithAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yang.gulimall.product.entity.AttrGroupEntity;
import com.yang.gulimall.product.service.AttrGroupService;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.R;



/**
 * 属性分组
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    /**
     * 功能描述：获取分类下所有分组&关联属性
     * API：https://easydoc.xyz/doc/75716633/ZUqEdvA4/6JM6txHf
     */
    @GetMapping("{catelogId}/withattr")
    public R getAttrGroupWithAttr(@PathVariable("catelogId") Long catelogId){
        List<AttrGroupWithAttrVo> vos=attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data",vos);
    }


    @PostMapping("/attr/relation")
    public R addAttrRelation(@RequestBody List<AttrGroupRelationVo> vos){

        relationService.saveAttrRelations(vos);

        return  R.ok();
    }

    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(attrgroupId,params);

        return R.ok().put("page",page);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    @GetMapping("{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entityList = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){
        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);

        attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
