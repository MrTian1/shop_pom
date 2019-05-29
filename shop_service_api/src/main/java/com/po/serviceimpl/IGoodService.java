package com.po.serviceimpl;

import com.po.entity.Goods;

import java.util.List;

public interface IGoodService {
    List<Goods>   queryList();

    int addGoods(Goods goods);

    Goods queryById(Integer gid);
}
