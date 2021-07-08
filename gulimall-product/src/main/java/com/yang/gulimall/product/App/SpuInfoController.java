package com.yang.gulimall.product.App;

import java.util.Arrays;
import java.util.Map;


import com.yang.gulimall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yang.gulimall.product.entity.SpuInfoEntity;
import com.yang.gulimall.product.service.SpuInfoService;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.R;



/**
 * spu信息
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    @RequestMapping("/skuId/{skuId}")
    public R getSpuBySkuId(@PathVariable("skuId") Long skuId) {
        SpuInfoEntity spuInfoEntity = spuInfoService.getSpuBySkuId(skuId);
        return R.ok().setData(spuInfoEntity);
    }
    /**
     * 商品上架
     */
    @PostMapping("/{spuId}/up")
    //@RequiresPermissions("product:spuinfo:list")
    public R spuUp(@PathVariable("spuId") Long spuId){
        spuInfoService.up(spuId);

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuSaveVo spuSaveVo){

        spuInfoService.saveSpuInfo(spuSaveVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
