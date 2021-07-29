package com.jiguiquan.www;

import com.jiguiquan.www.core.ZdAppConfig;
import com.jiguiquan.www.core.ZdConfigApplicationContext;
import com.jiguiquan.www.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiguiquan on 2021/7/22
 */
public class ZdSpringTestApplication {
    public static void main(String[] args) {
//        ZdConfigApplicationContext context = new ZdConfigApplicationContext(ZdAppConfig.class);
//
//        UserService userService = (UserService) context.getBean("userService");
//        userService.test();
//
//
//        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(ZdAppConfig.class);
        ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext("xxx.xml");
//        context1.getBean("userService");
    }
}
