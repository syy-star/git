package com.fh.controller;

import com.fh.service.WeChatService;
import com.fh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("WeChatController")
public class WeChatController {
    @Autowired
    private WeChatService weChatService;
    @RequestMapping("addWeChat")
    //订单表跟用户表关联对顶单表进行一个维护
    public JsonData addWeChat(Integer orderId) throws Exception {
        Map map=weChatService.addWeChat(orderId);
        return JsonData.getJsonSuccess(map);
    }

    //查询一下状态
    @RequestMapping("selectStatus")
    //根据低昂单id查询一下状态
    public JsonData selectStatus(Integer orderId) throws Exception {
        Integer status=weChatService.selectStatus(orderId);
        return JsonData.getJsonSuccess(status);
    }
}
