package com.example.shop_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.po")

public class ShopSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopSearchApplication.class, args);
    }

}