package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.TestMapper.CartMapper;
import com.fh.TestMapper.GoodsMapper;
import com.fh.model.Goods;
import com.fh.model.ProductCart;
import com.fh.service.CartService;
import com.fh.util.RedisPool;
import com.fh.util.RedisUse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private HttpServletRequest request;


    @Override
    public Integer addCart(Integer id, Integer count) {
        //如果库存大于0说明有库存
        if(count>0){
            //判断库存够不够
            Goods goods = goodsMapper.selectById(id);
            if (count>goods.getStockCount()){
                //如果库存不够就返回库存数量减去个数
                return goods.getStockCount()-count;
            }
        }
        //将数据存入redis中
        Map user = (Map) request.getAttribute("user");
        String iPhone = (String) user.get("iPhone");
        //获取购物车中指定的商品信息
        String proCart = RedisUse.hget("cart_" + iPhone + "_syy", id + "");
        //判断商品是否存在购物车
        if (StringUtils.isEmpty(proCart)){
            ProductCart productCart=goodsMapper.selectProCartById(id);
            productCart.setCheck(true);
            productCart.setCount(count);
            //小计
            BigDecimal money=productCart.getGoodsPrice().multiply(new BigDecimal(count));
            productCart.setMoney(money);
            //将商品信息 转成json字符串
            String productCartString = JSONObject.toJSONString(productCart);
            //将数据放入redis
            RedisUse.hset("cart_"+iPhone+"_syy",id+"",productCartString);
        }else {
            //存在购物车中修改商品个数和小计
            //将字符串类型转为Javabean
            ProductCart productCart = JSONObject.parseObject(proCart, ProductCart.class);
            //修改个数
            productCart.setCount(productCart.getCount()+count);
            //判断库存是否够
            //获取数据库的数量
            Goods  product = goodsMapper.selectById(id);
            //判断库存中的数据是否够
            if (productCart.getCount()>product.getStockCount()){
                //如果库存不够就返回库存数量减去个数
                // return product.getStockCount()-productCart.getCount();
                productCart.setCount(product.getStockCount());
            }else{
                if (productCart.getCount()<1){
                    return 1;
                }
            }
            //修改小计
            BigDecimal multiply = productCart.getGoodsPrice().multiply(new BigDecimal(productCart.getCount()));
            productCart.setMoney(multiply);
            String pro = JSONObject.toJSONString(productCart);
            //将数据放到redis中
            RedisUse.hset("cart_"+iPhone+"_syy",id+"",pro);
        }
        //怎么获取商品种类的个数
        long hlen = RedisUse.hlen("cart_" + iPhone + "_syy");
        return (int)hlen;
    }

    @Override
    public List selectCartAll() {
        Map user = (Map) request.getAttribute("user");
        String iPhone = (String) user.get("iPhone");
        //获取购物车中指定的商品信息
        List<String> proCart = RedisUse.hvals("cart_" + iPhone + "_syy");
        return proCart;
    }

    @Override
    public Integer deleteCart(Integer goodsId) {
        Map user = (Map) request.getAttribute("user");
        String iPhone = (String) user.get("iPhone");
        List<String> proCart = RedisUse.hvals("cart_" + iPhone + "_syy");
        Jedis resource = RedisPool.getResource();
        //从redis中取出key根据key删除
        resource.hdel("cart_" + iPhone + "_syy",goodsId+"");
        //获取购物车中的数据
        long hlen = RedisUse.hlen("cart_" + iPhone + "_syy");
        //最后返回删除的数量
        return (int)hlen;
    }

    @Override
    public List<ProductCart> sumCart() {
        Map user = (Map) request.getAttribute("user");
        String iPhone = (String) user.get("iPhone");
        List<String> proCart = RedisUse.hvals("cart_" + iPhone + "_syy");
        List<ProductCart> productCartList=new ArrayList<>();
        for (int i = 0; i <proCart.size() ; i++) {
            String s = proCart.get(i);
            ProductCart productCart = JSONObject.parseObject(s, ProductCart.class);
            //获取选中的数据
            if(productCart.isCheck()==true){
                productCartList.add(productCart);
            }
        }
        return productCartList;
    }

    @Override
    public void updateProductCartByIsCheck(String ids) {
        Map user = (Map) request.getAttribute("user");
        String iPhone = (String) user.get("iPhone");
        //获取购物车中指定的商品信息
        List<String> product = RedisUse.hvals("cart_" + iPhone + "_syy");

        for (int i = 0; i <product.size() ; i++) {
            //商品的json字符串
            String s = product.get(i);
            //将商品信息转为对象
            ProductCart productCart = JSONObject.parseObject(s, ProductCart.class);
            //判断此商品是否为要修改的字段
            Integer goodsId = productCart.getGoodsId();
            //判断此商品是否在被选中的状态中
            if((","+ids).contains(","+goodsId+",")==true){
                productCart.setCheck(true);
                RedisUse.hset("cart_" + iPhone + "_syy",productCart.getGoodsId()+"",JSONObject.toJSONString(productCart));
            }else {
                productCart.setCheck(false);
                RedisUse.hset("cart_" + iPhone + "_syy",productCart.getGoodsId()+"",JSONObject.toJSONString(productCart));
            }
        }
    }


}
