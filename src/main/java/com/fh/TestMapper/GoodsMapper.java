package com.fh.TestMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Goods;
import com.fh.model.ProductCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> selectGoodsAll(Integer isNotHotSale);

    List<Goods> selectGoodsMessageByGoodsId(Integer typeId);

    Goods selectGoodsMessageById(Integer goodsId);

    ProductCart selectProCartById(@RequestParam("goodsId") Integer id);

    Integer updateOrderDetail(@Param("goodsId") Integer goodsId, @Param("count") Integer count);
}
