package com.fh.controller;

import com.fh.model.OrderFrom;
import com.fh.service.OrderService;
import com.fh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("OrderFromController")
public class OrderFromController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("selectOrderFrom")
    public JsonData selectOrderFrom(){
        List<OrderFrom> orderFromList=orderService.selectOrderFrom();
        return JsonData.getJsonSuccess(orderFromList);
    }

}
