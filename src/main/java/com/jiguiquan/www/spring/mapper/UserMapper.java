package com.jiguiquan.www.spring.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by jiguiquan on 2021/7/28
 */
@Mapper
public interface UserMapper {

    Object selectById(Integer id);
}
