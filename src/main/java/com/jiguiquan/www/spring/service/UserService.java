package com.jiguiquan.www.spring.service;

import com.jiguiquan.www.service.User;
import com.jiguiquan.www.spring.mapper.OrderMapper;
import com.jiguiquan.www.spring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiguiquan on 2021/7/28
 */
@Service
public class UserService {

    @Autowired
    private UserMapper UserMapper;

    @Autowired
    private OrderMapper OrderMapper;

    public void test(){
        UserMapper.selectById(1);
        OrderMapper.selectById(1);
    }

}
