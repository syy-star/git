package com.fh.controller;

import com.fh.TestMapper.TestMapper;
import com.fh.model.Area;
import com.fh.service.TestService;
import com.fh.util.JsonData;
import com.fh.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;
@RestController
@RequestMapping("TestController")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("test")
    public JsonData test(){
        List<Area> areaList=testService.test();
        return JsonData.getJsonSuccess(areaList);
    }
    @RequestMapping("testAop")
    public JsonData testAop(){
        //报异常配事务删不掉是对的因为事务会进行回滚
        return JsonData.getJsonSuccess("删除成功");
    }

    @RequestMapping("selectAreaAll")
    public JsonData selectAreaAll(){
        Jedis jedis = RedisUtils.getJedis();
        String t_area = jedis.get("t_area");
        return JsonData.getJsonSuccess(t_area);
    }

}
