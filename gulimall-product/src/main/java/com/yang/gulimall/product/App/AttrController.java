package com.yang.gulimall.product.App;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.yang.gulimall.product.entity.ProductAttrValueEntity;
import com.yang.gulimall.product.service.ProductAttrValueService;
import com.yang.gulimall.product.vo.AttrRespVo;
import com.yang.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yang.gulimall.product.service.AttrService;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.R;



/**
 * 商品属性
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    @RequestMapping("/base/listforspu/{spuId}")
    public R baseAttrListForspu(@PathVariable("spuId") long spuId) {

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListforspu(spuId);

        return R.ok().put("data", entities);
    }

    /**
     * base_attr
     */
    @RequestMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                         @PathVariable("catelogId") long catelogId,
                         @PathVariable("attrType") String attrType) {

        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,attrType);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		//AttrEntity attr = attrService.getById(attrId);
        AttrRespVo respVo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }
    //product/attr/update
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities){
        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
