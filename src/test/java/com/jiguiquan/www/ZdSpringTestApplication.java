package com.jiguiquan.www;

import com.jiguiquan.www.core.ZdAppConfig;
import com.jiguiquan.www.core.ZdConfigApplicationContext;
import com.jiguiquan.www.service.UserService;

/**
 * Created by jiguiquan on 2021/7/22
 */
public class ZdSpringTestApplication {
    public static void main(String[] args) {
        ZdConfigApplicationContext context = new ZdConfigApplicationContext(ZdAppConfig.class);

        UserService userService = (UserService) context.getBean("userService");
        userService.test();
    }
}
