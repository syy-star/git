package com.fh.TestMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestMapper extends BaseMapper<Area> {
}
