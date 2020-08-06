package com.fh.service.impl;

import com.fh.TestMapper.GoodsMapper;
import com.fh.model.Goods;
import com.fh.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectGoodsAll(Integer isNotHotSale) {
        return goodsMapper.selectGoodsAll(isNotHotSale);
    }

    @Override
    public List<Goods> selectGoodsMessageByGoodsId(Integer typeId) {
        return goodsMapper.selectGoodsMessageByGoodsId(typeId);
    }

    @Override
    public Goods selectGoodsMessageById(Integer goodsId) {
        return goodsMapper.selectGoodsMessageById(goodsId);
    }

}
