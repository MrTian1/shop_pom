package com.po.shop_item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {
    @Autowired
    private Configuration configuration;
    @Test
    public void contextLoads() throws IOException, TemplateException {
        //准备模板
        Template template=configuration.getTemplate("hello.ftl");

        //准备数据
        Map map=new HashMap();
        map.put("name","xiaoming");
        //准备静态页面输出的路径
        Writer out=new FileWriter("C:\\Users\\tian\\Desktop\\hello.html");
        //生成静态页面
        template.process(map,out);
        out.close();
    }

}
