package com.fh.service;

import com.fh.model.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> selectGoodsAll(Integer isNotHotSale);

    List<Goods> selectGoodsMessageByGoodsId(Integer typeId);

    Goods selectGoodsMessageById(Integer goodsId);
}
