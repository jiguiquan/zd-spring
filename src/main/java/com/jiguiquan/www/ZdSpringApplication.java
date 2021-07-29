package com.jiguiquan.www;

import com.jiguiquan.www.core.ZdAppConfig;
import com.jiguiquan.www.core.ZdConfigApplicationContext;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by jiguiquan on 2021/7/22
 */
@MapperScan("")
public class ZdSpringApplication {
    public static void main(String[] args) {
        //启动ZdSpring
        ZdConfigApplicationContext context = new ZdConfigApplicationContext(ZdAppConfig.class);

        Object userService = context.getBean("userService");
        System.out.println(userService);
    }
    
}
