package com.yang.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.to.mq.OrderTo;
import com.yang.common.to.mq.StockLockedTo;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.ware.entity.WareSkuEntity;
import com.yang.gulimall.ware.vo.SkuHasStockVo;
import com.yang.gulimall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:50:45
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    List<SkuHasStockVo> getSkuHasStocks(List<Long> ids);

    Boolean orderLockStock(WareSkuLockVo wareSkuLockVo);

    void unlock(StockLockedTo stockLockedTo);

    void unlocks(OrderTo orderTo);
}

