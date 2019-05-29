package com.po.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.po.entity.Goods;
import com.po.serviceimpl.IGoodService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("goods")
public class GoosController {

    @Reference
    private IGoodService goodService;
    @Value("${img.server}")
    private  String imgPath;
    /**
     * 后台商品管理
     * @param model
     * @return
     */
    @RequestMapping("list")
    public  String goootList(Model model){
        //调用商品服务，查询商品列表
        List<Goods> goods = goodService.queryList();
            model.addAttribute("goods",goods);
            model.addAttribute("imgPath",imgPath);
        return  "goodslist";
    }
    /**
     * 添加商品
     */
    @RequestMapping("/add")
    public  String goodsAdd(Goods goods,String[] gimageList){
        //处理商品照片
        String gimages= "";
        for (String s:gimageList) {
            if (gimages.length()>0){
                 gimages += "|";
        }
            gimages += s;
        }
        goods.setGimages(gimages);

        //调用服务层，添加商品
        goodService.addGoods(goods);

        return  "redirect:/goods/list";

    }
}
