package com.jiguiquan.www;


import com.jiguiquan.www.spring.AppConfig;
import com.jiguiquan.www.spring.mapper.UserMapper;
import com.jiguiquan.www.spring.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiguiquan on 2021/7/28
 */
public class TestMybatis {
    public static void main(String[] args) throws IOException {
//        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();   //sql
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);   //代理对象
//        userMapper.selectById(1);  //执行第一行代码的时候才建立连接

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.test();
    }
}
