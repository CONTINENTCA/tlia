package com.zly.tliaswebmanagement.config;

import com.zly.tliaswebmanagement.interceptor.DemoInterceptor;
import com.zly.tliaswebmanagement.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Documented;

// 标识这个类是个配置类
@Configuration
// 相当于打包了
/*
@Component
@Documented
 */

/*
 /*             一级路径                能匹配/depts /emps /login 不能匹配/depts/1
 /**            任意级路径               能匹配任意
 /depts/*       /depts下的一级路径        能匹配/depts/1 不能匹配/depts /depts/1/2
 /depts/**      /depts下的任意级路径       能匹配/depts /depts/1 /depts/1/2
 */
public class WebConfig implements WebMvcConfigurer {

    //@Autowired
    //DemoInterceptor demoInterceptor;

    @Autowired
    TokenInterceptor tokenInterceptor;

    // 重写这个函数 注册拦截器
    // 如果不需要拦截器 就把下面的函数注释掉


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(demoInterceptor).addPathPatterns("/**"); // 链式变成 设置拦截路径 这个代表拦截所有
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**"). // 拦截所有路径
                excludePathPatterns("/login"); // 排除登录
    }

}
