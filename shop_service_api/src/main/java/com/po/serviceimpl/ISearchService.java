package com.po.serviceimpl;

import com.po.entity.Goods;

import java.util.List;

public interface ISearchService {
    List<Goods> queryByKeyWord(String keyword);
    int addGoods(Goods goods);
}
