package com.fh.controller;

import com.fh.commons.exception.CountException;
import com.fh.model.ProductCart;
import com.fh.service.ShippingAddressService;
import com.fh.util.JsonData;
import com.fh.util.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ShippingAddressController")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @RequestMapping("selectShippingAddress")
    public JsonData selectShippingAddress(){
        String t_shippingAddress = RedisUse.get("t_shippingAddress");
        return JsonData.getJsonSuccess(t_shippingAddress);
    }

    @RequestMapping("addOrder")
    public JsonData addOrder(Integer addressId,Integer payType ,String flag) throws CountException {
        //处理接口的幂等性判断请求存在不存在
        Boolean exists = RedisUse.exists(flag);
        //如果存在说明这是第二次请求就返回一个状态说明请求正在处理中
        if (exists==true){
            return JsonData.getJsonError(300,"请求处理中");
        }else {
            //否则的话就将这次请求放到redis中
            RedisUse.set(flag,"",10);
        }
        Map map=shippingAddressService.addOrder(addressId,payType);
        return JsonData.getJsonSuccess(map);
    }









}
