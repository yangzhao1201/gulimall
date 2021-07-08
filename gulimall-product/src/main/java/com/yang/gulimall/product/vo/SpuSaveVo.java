/**
  * Copyright 2020 bejson.com 
  */
package com.yang.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2020-05-08 14:3:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private Integer publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<com.yang.gulimall.product.vo.BaseAttrs> baseAttrs;
    private List<com.yang.gulimall.product.vo.Skus> skus;


}