package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.TestMapper.GoodsMapper;
import com.fh.TestMapper.OrderCartMapper;
import com.fh.TestMapper.OrderFromMapper;
import com.fh.TestMapper.ShippingAddressMapper;
import com.fh.commons.enums.PayStatusEnum;
import com.fh.commons.exception.CountException;
import com.fh.model.Goods;
import com.fh.model.OrderDetail;
import com.fh.model.OrderFrom;
import com.fh.model.ProductCart;
import com.fh.service.ShippingAddressService;
import com.fh.util.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderFromMapper orderFromMapper;

    @Autowired
    private OrderCartMapper orderCartMapper;

    @Override
    public Map addOrder(Integer addressId, Integer payType) throws CountException {
        //添加订单
        Map map=new HashMap();
        List<OrderDetail> orderFromList=new ArrayList<>();
        OrderFrom order=new OrderFrom();
        order.setAddressId(addressId);
        order.setCreateDate(new Date());
        order.setPayStatus(PayStatusEnum.PAY_STATUS_INIT.getStatus());
        order.setPayType(payType);
        Integer typeCount=0;
        BigDecimal totalMany=new BigDecimal(0);
        //获取用户信息
        Map user = (Map) request.getAttribute("user");
        //从用户信息中获取获取用户的手机号
        String iPhone = (String) user.get("iPhone");
        //从Redis中取出数据
        List<String> cartStr = RedisUse.hvals("cart_" + iPhone + "_syy");
        //循环遍历数据
        for (int i = 0; i < cartStr.size();i++) {
            //将从redis中取出的数据转成对象
            ProductCart productCart = JSONObject.parseObject(cartStr.get(i), ProductCart.class);
            //判断是否被选中
            if (productCart.isCheck()==true){
                //通过id查询商品
                Goods goods = goodsMapper.selectById(productCart.getGoodsId());
                //判断商品库存大于商品加入购物车的数量
                if (goods.getStockCount()>productCart.getCount()){
                    typeCount++;
                    totalMany=totalMany.add(productCart.getMoney());
                    OrderDetail orderDetail=new OrderDetail();
                    orderDetail.setCount(orderDetail.getCount());
                    orderDetail.setProductId(orderDetail.getProductId());
                    orderFromList.add(orderDetail);
                    //修改一下商品的详情信息
                    Integer orderDetail1=goodsMapper.updateOrderDetail(goods.getGoodsId(),productCart.getCount());
                    //解决商品库存不足问题如果商品等于0说明商品下单时库存不足抛出异常在则让他进行事务回滚
                    if (orderDetail1==0){
                        throw new CountException("商品库存不足");
                    }
                }else{
                    throw  new CountException("商品库存不足");
                }
            }
            //再重新给商品价钱赋值
            order.setTotalMoney(totalMany);
            order.setProTypeCount(typeCount);
            orderFromMapper.insert(order);
            //保存订单详情表
            orderCartMapper.batchAdd(orderFromList,order.getId());
            for (int j = 0; j <cartStr.size() ; j++) {
                ProductCart productCart1 = JSONObject.parseObject(cartStr.get(i), ProductCart.class);
                if (productCart1.isCheck()==true){
                    RedisUse.hdel("cart_"+iPhone+"_syy",productCart1.getGoodsId()+"");
                }
            }
        }
        //最后返回状态
        map.put("code",200);
        map.put("orderId",order.getId());
        map.put("totalMoney",totalMany);
        return map;
    }

}
