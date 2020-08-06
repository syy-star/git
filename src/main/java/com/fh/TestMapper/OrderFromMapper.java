package com.fh.TestMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.OrderFrom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderFromMapper extends BaseMapper<OrderFrom> {
}
