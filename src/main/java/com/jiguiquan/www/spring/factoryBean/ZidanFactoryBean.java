package com.jiguiquan.www.spring.factoryBean;

import com.jiguiquan.www.spring.mapper.UserMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jiguiquan on 2021/7/28
 */
//@Component //交给了ZidanBeanDefinitionRegistrar使用构造方法完成，所以不再需要@Component注解了
public class ZidanFactoryBean implements FactoryBean {

    private Class mapper;

    public ZidanFactoryBean(Class mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object getObject() throws Exception {
        Object obj = Proxy.newProxyInstance(ZidanFactoryBean.class.getClassLoader(), new Class[]{mapper}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());  //打印结果：toString
                // 必须判断当前执行的方法是在哪个类上面生成的，如果是在Object上的方法，那么就直接执行原方法，不进行代理
                if (Object.class.equals(method.getDeclaringClass())){
                    System.out.println(this);  //打印结果：代理对象com.jiguiquan.www.spring.factoryBean.ZidanFactoryBean$1@67b467e9
                    return method.invoke(this, args);
                }else {
                    return null;
                }
            }
        });
        return obj;
    }

    @Override
    public Class<?> getObjectType() {
        return mapper;
    }
}
