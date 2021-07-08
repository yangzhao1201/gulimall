package com.yang.gulimall.ware.vo;

import com.yang.gulimall.ware.vo.PurchaseFinishItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseFinishVo {
    @NotNull
    private Long id;

    private List<PurchaseFinishItem> items;
}
