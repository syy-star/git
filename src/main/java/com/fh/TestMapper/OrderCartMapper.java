package com.fh.TestMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface OrderCartMapper extends BaseMapper<OrderDetail> {
    void batchAdd(@Param("list")List<OrderDetail> orderFromList,@Param("oid")  Integer Id);
}
