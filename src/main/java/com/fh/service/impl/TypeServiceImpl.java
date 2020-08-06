package com.fh.service.impl;

import com.fh.TestMapper.TypeMapper;
import com.fh.model.Type;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> selectType() {
        return typeMapper.selectList(null);
    }
}
