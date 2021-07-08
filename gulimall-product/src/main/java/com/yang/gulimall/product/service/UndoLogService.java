package com.yang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.product.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 17:37:36
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

