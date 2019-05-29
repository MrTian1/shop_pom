package com.po.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.po.dao.GoodsMapper;
import com.po.entity.Goods;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class GoodsServiceImpl implements IGoodService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Reference
    private  ISearchService searchService;
    @Override
    public List<Goods> queryList() {
        List<Goods> goods = goodsMapper.selectList(null);
        return goods;
    }

    @Override
    public int addGoods(Goods goods) {
        //保存商品到数据库中
        goodsMapper.insert(goods);
        //同步商品到索引库中
      //   searchService.addGoods(goods);
        //通过http通知详情工程生成静态页面
       // HttpUtil.sendGet("http://localhost:8083/item/createHtml?gid=" + goods.getId());

        //将商品信息放入队列中，进行异步化处理
        rabbitTemplate.convertAndSend("goods_exchange", "", goods);
        return 1;
    }

    @Override
    public Goods queryById(Integer gid) {
        return  goodsMapper.selectById(gid);
    }
}
