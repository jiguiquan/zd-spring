package com.jiguiquan.www.spring.annotation;

import com.jiguiquan.www.spring.factoryBean.ZidanBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiguiquan on 2021/7/29
 */
//Import手动导入Bean，但是当Spring发现，我们导入的类实现了ImportBeanDefinitionRegistrar接口，Spring就会主动调用其中的registerBeanDefinitions()；
@Import(ZidanBeanDefinitionRegistrar.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZidanScan {
    String value();
}
