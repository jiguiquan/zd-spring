package com.jiguiquan.www.service;

import com.jiguiquan.www.core.ZdBeanNameAware;
import com.jiguiquan.www.annotation.ZdAutowired;
import com.jiguiquan.www.annotation.ZdComponent;
import com.jiguiquan.www.core.ZdInitializingBean;
import com.jiguiquan.www.spring.mapper.UserMapper;

/**
 * Created by jiguiquan on 2021/7/22
 */
@ZdComponent("userService")
//@ZdScope("prototype")
public class UserService implements ZdBeanNameAware, ZdInitializingBean {
    @ZdAutowired
    private User user;


    private String beanName;  //需要用过ZdBeanNameWare进行回调

    private String userName;  //通过ZdInitializingBean.afterPropertiesSet()方法完成赋值

    public void test(){
        System.out.println(user);
        System.out.println(beanName);
        System.out.println(userName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        this.userName = this.beanName + "用户";
    }
}
