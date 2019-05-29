package com.example.shop_bark;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.po")
@Import(FdfsClientConfig.class)
public class ShopBarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBarkApplication.class, args);
    }

}
