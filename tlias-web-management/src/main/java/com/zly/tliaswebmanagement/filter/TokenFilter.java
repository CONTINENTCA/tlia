package com.zly.tliaswebmanagement.filter;

import com.zly.tliaswebmanagement.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/*
拦截具体路径     /login      只有访问/login路径时 才会被拦截
目录拦截        /emps/*      访问/emps下的所有资源 都会被拦截
拦截所有        /*          拦截所有路径
 */

@Slf4j
//@WebFilter("/*") // 拦截所有请求 如果需要filter就把这个注释取消了
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1 获取请求路径
        String requestURI = request.getRequestURI(); // URI类似/clazzs/list

        // 2 判断是否是登录请求（请求路径是否包含/login）
        // 如果是登陆操作 那么放行 如果不是就要获取请求头中token
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            filterChain.doFilter(request, response); // 放行
            return;
        }

        // 3 获取请求头中token
        String token = request.getHeader("MyToken"); // 要和前端的request拦截器config.headers.MyToken = loginUser.token相对应

        // 4 判断token是否存在 如果不存在 说明用户没有登录 响应401状态码
        if(token == null || token.isEmpty()){
            log.info("令牌为空，响应401状态码");
            response.setStatus(401);
            return;
        }

        // 5 如果token存在 校验令牌 如果校验失败 返回错误信息 响应401状态码
        try{
            JwtUtils.parseJwt(token);
        }catch (Exception e){ // 如果解析失败 响应401
            log.info("令牌解析失败，响应401状态码");
            response.setStatus(401);
            return;
        }

        // 6 校验通过 放行
        log.info("令牌合法 放行");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
