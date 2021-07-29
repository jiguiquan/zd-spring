package com.jiguiquan.www.spring;

import com.jiguiquan.www.spring.annotation.ZidanScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jiguiquan on 2021/7/29
 */
@Configuration
@ComponentScan("com.jiguiquan.www.spring")
@ZidanScan("com.jiguiquan.www.spring.mapper")
public class AppConfig {

}
