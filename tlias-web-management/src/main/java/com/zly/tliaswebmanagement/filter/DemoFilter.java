package com.zly.tliaswebmanagement.filter;
/*
1 定义Filter：定义一个类 implement Filter接口 实现所有方法
2 配置Filter：Filter类上加@WebFilter注解 配置拦截路径 引导类上加@ServletComponentScan 开启Servlet组件支持
 */

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;


/*
@WebFilter(urlPatterns = "/*") // /*代表拦截所有请求
@Slf4j
public class DemoFilter implements Filter {

    // 初始化方法 web服务器启动的时候执行 只执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("Demo init初始化方法");
    }

    // 拦截到请求后执行 会执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Demo 拦截到了请求");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse); // 执行filterChain中doFilter方法 继续传递俩参数
        System.out.println("Demo 已放行");
    }

    // 销毁方法 web服务器关闭的时候执行 只执行一次
    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("Demo 销毁方法");
    }
}
 */
