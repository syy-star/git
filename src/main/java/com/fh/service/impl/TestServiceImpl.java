package com.fh.service.impl;

import com.fh.TestMapper.TestMapper;
import com.fh.model.Area;
import com.fh.service.TestService;
import com.fh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
        @Autowired
        private TestMapper testMapper;

    @Override
    public List<Area> test() {
        List<Area> areaList=testMapper.selectList(null);
        return areaList;
    }
}
