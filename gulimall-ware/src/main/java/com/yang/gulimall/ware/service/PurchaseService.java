package com.yang.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.ware.entity.PurchaseEntity;
import com.yang.gulimall.ware.vo.MergeVo;
import com.yang.gulimall.ware.vo.PurchaseFinishVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author cosmoswong
 * @email cosmoswong@sina.com
 * @date 2020-04-23 23:42:51
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceived(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseFinishVo finishVo);
}

