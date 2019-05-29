package com.po.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.po.entity.Goods;
import com.po.serviceimpl.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;
    /**
     * 根据关键词进行搜索
     */
    @RequestMapping("/searchByKeyWord")
    public  String searchByKey(String keyword, Model model){

        //通过关键词进行搜索
        System.out.println("进行商品的搜索是 "+keyword);
        List<Goods> goods = searchService.queryByKeyWord(keyword);
        model.addAttribute("goods",goods);
        return "searchlist";
    }
}
