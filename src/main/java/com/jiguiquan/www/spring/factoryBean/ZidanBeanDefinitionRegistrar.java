package com.jiguiquan.www.spring.factoryBean;

import com.jiguiquan.www.spring.annotation.ZidanScan;
import com.jiguiquan.www.spring.mapper.OrderMapper;
import com.jiguiquan.www.spring.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 手动注册BeanDefinition
 * Created by jiguiquan on 2021/7/29
 */
public class ZidanBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取ZidanScan里面的value值，即为scan路径
//        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(ZidanScan.class.getName());
//        Object path = attributes.get("value");
//        System.out.println(path);
        //扫描path下面的所有Class，然后放到List<Class>中进行遍历
        List<Class> mappers = new ArrayList<>();
        mappers.add(UserMapper.class);
        mappers.add(OrderMapper.class);
        for (Class mapper : mappers) {
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
            beanDefinition.setBeanClass(ZidanFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);
            registry.registerBeanDefinition(mapper.getName(), beanDefinition);
        }
    }
}
