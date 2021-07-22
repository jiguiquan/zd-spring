package com.jiguiquan.www.core;

import com.jiguiquan.www.annotation.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiguiquan on 2021/7/22
 */
public class ZdConfigApplicationContext {

    private Class configClass;
    private ConcurrentHashMap<String, ZdBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, ZdBeanDefinition>();  //bean定义文件
    private ConcurrentHashMap<String, Object> singletonBeanMap = new ConcurrentHashMap<String, Object>();  //单例池
    private List<ZdBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public ZdConfigApplicationContext(Class configClass) {
        this.configClass = configClass;
        //**Spring启动过程中要做的事情**扫描包——实例化非懒加载模式的单例Bean——放入单例池中(Map)
        //Spring创建类的方式是反射，所以需要扫描到所有的类的Class

        //1、扫描得到Class列表（重点——真正要扫描的不是java文件夹，而是target下面的包）
        List<Class> classList = scan(configClass);

        //2、遍历解析BeanDefinition添加到BeanDefinitionMap中（里面存储着Bean是单例/原型，是否懒加载，最重要的Bean的Class）
        for (Class clazz : classList) {
            if (clazz.isAnnotationPresent(ZdComponent.class)) {
                //判断，如果这个Class是继承于ZdBeanPostProcessor.class的，则是个processor增强器，需要放到beanPostProcessors数组中
                if (ZdBeanPostProcessor.class.isAssignableFrom(clazz)){ //判断ZdBeanPostProcessor是否为后面类的父类
                    try {
                        ZdBeanPostProcessor processor = (ZdBeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                        beanPostProcessors.add(processor);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                //继续
                ZdBeanDefinition beanDefinition = new ZdBeanDefinition();
                ZdComponent componentAnnotation = (ZdComponent) clazz.getAnnotation(ZdComponent.class);
                String beanName = componentAnnotation.value();
                if (clazz.isAnnotationPresent(ZdScope.class)) {
                    ZdScope scopeAnnotation = (ZdScope) clazz.getAnnotation(ZdScope.class);
                    beanDefinition.setScope(scopeAnnotation.value());
                }else {
                    beanDefinition.setScope("singleton");
                }
                if (clazz.isAnnotationPresent(ZdLazy.class)) {
                    beanDefinition.setLazy(true);
                }else {
                    beanDefinition.setLazy(false);
                }
                beanDefinition.setBeanClass(clazz);
                beanDefinitionMap.put(beanName, beanDefinition);
            }
        }

        //2、基于classList去实例化自己的Bean对象
        instanceSingletonBean();
    }



    /**
     * 扫描指定包，得到ClassList
     * @param configClass
     * @return
     */
    private List<Class> scan(Class configClass) {
        List<Class> classList = new ArrayList<Class>();
        ZdComponentScan annotation;
        if (configClass.isAnnotationPresent(ZdComponentScan.class)) {
            annotation = (ZdComponentScan) configClass.getAnnotation(ZdComponentScan.class);
        }else {
            throw new RuntimeException("ZdComponentScan注解不存在");
        }
        String path = annotation.value();  //结果：包路径：com.jiguiquan.www.service
        path = path.replace(".", "/");  //之所以要替换成"/"，是因为操作系统中是用"/"作为层级划分的 com/jiguiquan/www/service
        //为了快速地拼接出操作系统中的实际路径，我们可以借助ClassLoader来快速定位到classpath
        ClassLoader classLoader = ZdConfigApplicationContext.class.getClassLoader(); //首先获取到的是AppClassLoader
        URL url = classLoader.getResource(path);  //在classpath下加载
        File file = new File(url.getFile());  //这肯定是个文件夹
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absolutePath = f.getAbsolutePath();
                //输出结果
                // D:\Study\zd-spring\target\classes\com\jiguiquan\www\service\User.class
                // D:\Study\zd-spring\target\classes\com\jiguiquan\www\service\UserService.class
                absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                //此处结果：
                // com\jiguiquan\www\service\User
                // com\jiguiquan\www\service\UserService
                absolutePath = absolutePath.replace("\\", ".");
                //此处结果：
                // com.jiguiquan.www.service.User
                // com.jiguiquan.www.service.UserService
                try {
                    Class<?> clazz = classLoader.loadClass(absolutePath);  //终于我们加载到指定包下面的所有Class了
                    classList.add(clazz); //这里还可以判断一下是否有ZdComponent这个注解，有的我们后面才需要对他进行实例化
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(classList);  //[class com.jiguiquan.www.service.User, class com.jiguiquan.www.service.UserService]
        return classList;
    }

    //实例化单例Bean
    private void instanceSingletonBean() {
        for (String beanName : beanDefinitionMap.keySet()) {
            ZdBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (!beanDefinition.getLazy() && "singleton".equals(beanDefinition.getScope())) { //只有非懒加载的单例bean才会被实例化出来
                //创建Bean
                if (!singletonBeanMap.containsKey(beanName)){
                    Object bean = doCreateBean(beanName, beanDefinition);
                    singletonBeanMap.put(beanName, bean);
                }
            }
        }
    }

    //实际执行创建Bean的方法 实例化-初始化
    private Object doCreateBean(String beanName, ZdBeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            //1、实例化
            Object bean = beanClass.getDeclaredConstructor().newInstance();  //无参构造

            //2、属性填充
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ZdAutowired.class)) {
                    String fieldName = field.getName();
                    //有ZdAutowired注解的，我们对这个属性进行赋值,值来源为单丽池
                    field.setAccessible(true);  //要求可以Access进入进行修改
                    field.set(bean, getBean(fieldName));
                }
            }

            //3、执行是否实现了ZdBeanNameAware接口
            if (bean instanceof ZdBeanNameAware) {
                ((ZdBeanNameAware) bean).setBeanName(beanName);
            }

            //4-1、初始化之前调用：ZdBeanPostProcessor.postProcessBeforeInitialization();
            if (! (bean instanceof ZdBeanPostProcessor)){
                for (ZdBeanPostProcessor processor : beanPostProcessors) {
                    processor.postProcessBeforeInitialization(bean, beanName);
                }
            }


            //4、执行初始化方法 —— ZdInitializingBean.afterPropertiesSet()方法完成赋值
            if (bean instanceof ZdInitializingBean) {
                ((ZdInitializingBean) bean).afterPropertiesSet();
            }

            //4+1、初始化之后调用：ZdBeanPostProcessor.postProcessAfterInitialization();
            if (! (bean instanceof ZdBeanPostProcessor)){
                for (ZdBeanPostProcessor processor : beanPostProcessors) {
                    processor.postProcessAfterInitialization(bean, beanName);
                }
            }

            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取Bean
    public Object getBean(String beanName){
        ZdBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if ("prototype".equals(beanDefinition.getScope())){
            return doCreateBean(beanName, beanDefinition);
        }else if ("singleton".equals(beanDefinition.getScope())){
            if (!singletonBeanMap.containsKey(beanName)) {
                Object bean = doCreateBean(beanName, beanDefinition);
                singletonBeanMap.put(beanName, bean);
                return bean;
            }
            return singletonBeanMap.get(beanName);
        }
        return null;
    }
}
