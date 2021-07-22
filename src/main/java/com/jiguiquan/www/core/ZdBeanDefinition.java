package com.jiguiquan.www.core;

/**
 * Created by jiguiquan on 2021/7/22
 */
public class ZdBeanDefinition {
    private String scope;

    private Boolean isLazy;

    private Class beanClass;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getLazy() {
        return isLazy;
    }

    public void setLazy(Boolean lazy) {
        isLazy = lazy;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
