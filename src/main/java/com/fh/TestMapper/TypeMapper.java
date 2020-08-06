package com.fh.TestMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

}
