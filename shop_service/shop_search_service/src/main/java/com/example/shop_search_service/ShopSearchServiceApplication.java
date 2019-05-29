package com.example.shop_search_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
@DubboComponentScan("com.po.serviceimpl")
public class ShopSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchServiceApplication.class, args);
    }

}
