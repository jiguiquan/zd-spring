package com.jiguiquan.www.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义Bean是单例singleton还是原型prototype, 默认为singleton
 * Created by jiguiquan on 2021/7/22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZdScope {
    String value() default "singleton";
}
