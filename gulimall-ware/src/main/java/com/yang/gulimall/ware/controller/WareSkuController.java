package com.yang.gulimall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.yang.common.exception.BizCodeEnum;
import com.yang.common.exception.NoStockException;
import com.yang.gulimall.ware.vo.SkuHasStockVo;
import com.yang.gulimall.ware.vo.WareSkuLockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yang.gulimall.ware.entity.WareSkuEntity;
import com.yang.gulimall.ware.service.WareSkuService;
import com.yang.common.utils.PageUtils;
import com.yang.common.utils.R;



/**
 * 商品库存
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:50:45
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;



    @RequestMapping("/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo wareSkuLockVo) {
        try {
            Boolean lock = wareSkuService.orderLockStock(wareSkuLockVo);
            return R.ok();
        } catch (NoStockException e) {
            return R.error(BizCodeEnum.NO_STOCK_EXCEPTION.getCode(), BizCodeEnum.NO_STOCK_EXCEPTION.getMsg());
        }
    }
    /**
     * //查询sku是否有库存
     */
    @PostMapping("/hasStocks")
    public R getSkuHasStock(@RequestBody List<Long> SkuIds){
        List<SkuHasStockVo> vos = wareSkuService.getSkuHasStock(SkuIds);
        return R.ok().setData(vos);
    }

    @RequestMapping("/getSkuHasStocks")
    public List<SkuHasStockVo> getSkuHasStocks(@RequestBody List<Long> ids) {
        return wareSkuService.getSkuHasStocks(ids);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:waresku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:waresku:info")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:waresku:delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
