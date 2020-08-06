package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.Type;
import com.fh.service.TypeService;
import com.fh.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("TypeController")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("selectType")
    public JsonData selectType(){
        Jedis jedis = RedisUtils.getJedis();
        String t_type = jedis.get("t_type");
        if (StringUtils.isEmpty(t_type)==true){
            List<Type> typeList= typeService.selectType();
            t_type= JSONObject.toJSONString(typeList);
            jedis.set("t_type",t_type);
        }
        RedisUtils.returnJedis(jedis);
        return JsonData.getJsonSuccess(t_type);
    }
}
