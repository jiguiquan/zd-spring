package com.jiguiquan.www.service;

import com.jiguiquan.www.annotation.ZdComponent;
import com.jiguiquan.www.core.ZdBeanPostProcessor;

/**
 * Created by jiguiquan on 2021/7/22
 */
@ZdComponent("processorA")
public class ProcessorA implements ZdBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println(beanName + ":ProcessorA初始化之前");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println(beanName + ":ProcessorA初始化之后");
        return bean;
    }
}
