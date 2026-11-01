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
在 web.xml 中：过滤器执行顺序 ≠ 按类名字符串排序过滤器执行顺序 = 你配置 <filter-mapping> 的先后顺序
1. 传统 web.xml 里的顺序
你在 web.xml 里先写哪个 filter-mapping，就先执行哪个过滤器。
比如：
xml
<!-- 先写 encodingFilter → 先执行 -->
<filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>

<!-- 后写 logFilter → 后执行 -->
<filter-mapping>
    <filter-name>logFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
执行顺序：EncodingFilter → LogFilter → Servlet
和类名 EncodingFilter、LogFilter 的字母顺序完全无关。
2. 如果你用的是 注解版 @WebFilter（没有 web.xml）
那就是 按过滤器类名的字符串字典序排序！
比如：
AFilter.java
BFilter.java
LogFilter.java
执行顺序：AFilter → BFilter → LogFilter
因为 @WebFilter 没有指定顺序的属性，容器 (Tomcat) 只能按类名字符串排序来决定过滤器链顺序。
3. Spring Boot 中的 Filter 顺序（最常用）
Spring Boot 里不按类名排序，而是按：
@Order(数字) 越小越先执行
或者通过 FilterRegistrationBean.setOrder(1) 设置
示例：
@Order(1)
@Component
public class EncodingFilter implements Filter {}

@Order(2)
@Component
public class LogFilter implements Filter {}
顺序：1 → 2，和类名无关。
一句话总结
web.xml：按 <filter-mapping> 先后顺序
纯 @WebFilter：按类名字符串排序
Spring Boot：按 @Order 数字排序
 */

/*
@WebFilter(urlPatterns = "/*") // /*代表拦截所有请求
@Slf4j
public class ADemoFilter implements Filter {

    // 初始化方法 web服务器启动的时候执行 只执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("ADemo init初始化方法");
    }

    // 拦截到请求后执行 会执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("ADemo 拦截到了请求");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse); // 执行filterChain中doFilter方法 继续传递俩参数
        System.out.println("ADemo 已放行");
    }

    // 销毁方法 web服务器关闭的时候执行 只执行一次
    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("ADemo destroy 销毁方法");
    }
}
 */

