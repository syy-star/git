package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.Goods;
import com.fh.service.GoodsService;
import com.fh.util.JsonData;
import com.fh.util.RedisUse;
import com.fh.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("GoodsController")
@Api(value= "商品类")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("selectGoodsAll")
    /*@GetMapping(value = "selectGoodsAll")
    @ApiOperation(value = "查询全部商品",notes = "根据热销商品查询信息")
    @ApiImplicitParam(paramType = "query",name = "查询热销商品",value = "isNotHotSale",required = true,dataType = "Integer")
    @ApiResponse(code = 404,message = "路径未找到",response = String.class)*/
    public JsonData selectGoodsAll(Integer isNotHotSale){
        Jedis jedis = RedisUtils.getJedis();
        String t_goods = jedis.get("t_goods");
        if (StringUtils.isEmpty(t_goods)==true){
            List<Goods> goodsList= goodsService.selectGoodsAll(isNotHotSale);
            t_goods = JSONObject.toJSONString(goodsList);
            RedisUse.set("t_goods",t_goods);
        }
        RedisUtils.returnJedis(jedis);
        return JsonData.getJsonSuccess(t_goods);
    }

    @RequestMapping("selectGoodsMessageByGoodsId")
    public JsonData selectGoodsMessageByGoodsId(Integer typeId){
        List<Goods> apiList=goodsService.selectGoodsMessageByGoodsId(typeId);
        return JsonData.getJsonSuccess(apiList);
    }

    @RequestMapping("selectGoodsMessageById")
    public JsonData selectGoodsMessageById(Integer goodsId){
        Goods goods=goodsService.selectGoodsMessageById(goodsId);
        return JsonData.getJsonSuccess(goods);
    }
}
