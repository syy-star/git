package com.fh.service.impl;

import com.fh.TestMapper.OrderFromMapper;
import com.fh.model.OrderFrom;
import com.fh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFromServiceImpl implements OrderService {

    @Autowired
    private OrderFromMapper orderFromMapper;

    @Override
    public List<OrderFrom> selectOrderFrom() {
        return orderFromMapper.selectList(null);
    }
}
