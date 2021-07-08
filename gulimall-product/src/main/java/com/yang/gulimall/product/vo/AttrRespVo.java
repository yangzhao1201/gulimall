package com.yang.gulimall.product.vo;

import lombok.Data;

/**
 * @author TangYuan
 * @create 2021--06--19--23:42
 * @description
 */
@Data
public class AttrRespVo extends AttrVo{

    //所属分类和所属分组
    private String catelogName;

    private String groupName;

    private Long[] catelogPath;

}
