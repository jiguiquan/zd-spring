package com.jiguiquan.www.core;

import com.jiguiquan.www.annotation.ZdComponent;

/**
 * 允许用户在Bean初始化之前或初始化之后的位置，增加自己的业务逻辑
 * Created by jiguiquan on 2021/7/22
 */
public interface ZdBeanPostProcessor {
    //初始化之前
    Object postProcessBeforeInitialization(Object bean, String beanName);

    //初始化之后
    Object postProcessAfterInitialization(Object bean, String beanName);
}
