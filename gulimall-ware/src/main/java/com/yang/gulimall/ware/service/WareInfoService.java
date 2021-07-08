package com.yang.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.common.utils.PageUtils;
import com.yang.gulimall.ware.entity.WareInfoEntity;
import com.yang.gulimall.ware.vo.FareVo;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author tangyuan
 * @email tangyuan@gmail.com
 * @date 2021-06-16 23:50:45
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    FareVo getFare(Long addrId);
}

