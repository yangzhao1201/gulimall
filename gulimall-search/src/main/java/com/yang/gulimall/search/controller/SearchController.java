package com.yang.gulimall.search.controller;

import com.yang.gulimall.search.service.MallSearchService;
import com.yang.gulimall.search.vo.SearchParam;
import com.yang.gulimall.search.vo.SearchReult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TangYuan
 * @create 2021--06--27--10:01
 * @description
 */

@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request){

        param.set_queryString(request.getQueryString());
        SearchReult search = mallSearchService.search(param);
        model.addAttribute("result",search);

        return "list";
    }
}
