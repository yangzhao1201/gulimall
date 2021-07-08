package com.yang.gulimall.search.service;

import com.yang.gulimall.search.vo.SearchParam;
import com.yang.gulimall.search.vo.SearchReult;

/**
 * @author TangYuan
 * @create 2021--06--28--01:34
 * @description
 */
public interface MallSearchService {
    SearchReult search(SearchParam param);
}
