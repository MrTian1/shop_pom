package com.po.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back")
public class BackController {

    @RequestMapping("/{topage}")
    public  String toPage(@PathVariable("topage") String  topage){
        return  topage ;
    }
}
