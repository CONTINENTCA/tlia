package com.zly.tliaswebmanagement.interceptor;

import com.zly.tliaswebmanagement.utils.CurrentHolder;
import com.zly.tliaswebmanagement.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    // 只需要实现preHandle
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        // 1 获取请求路径
        String requestURI = request.getRequestURI(); // URI类似/clazzs/list

        // 2 判断是否是登录请求（请求路径是否包含/login）
        // 如果是登陆操作 那么放行 如果不是就要获取请求头中token
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            // 放行
            return true;
        }
         */

        // 3 获取请求头中token
        String token = request.getHeader("MyToken"); // 要和前端中请求拦截器相对应 config.headers.MyToken = loginUser.token

        // 4 判断token是否存在 如果不存在 说明用户没有登录 响应401状态码
        if(token == null || token.isEmpty()){
            log.info("令牌解析失败，响应401状态码");
            response.setStatus(401);
            return false;
        }

        // 5 如果token存在 校验令牌 如果校验失败 返回错误信息 响应401状态码
        try{
            Claims claims = JwtUtils.parseJwt(token);

            // 安全获取 id（修复你上一个问题）
            Integer userId = claims.get("id", Integer.class);
            /*
            第二个参数 Integer.class = 告诉方法：我要把取出来的值，转成 Integer 类型返回给我。
            claims 本质是一个 Map<String, Object>`里面存的是：
            key："id"
            value：Object 类型（可能是 Integer、Long、String...）
            所以：
            claims.get("id") → 返回 Object
            claims.get("id", Integer.class) → 返回 Integer
            作用：
            自动类型转换
            内部帮你把 Object 强转成你指定的类型
            不用自己写强转、不用 toString
            类型安全，不容易报错
             */
            CurrentHolder.setCurrentId(userId);

        }catch (Exception e){ // 如果解析失败 响应401
            log.info("令牌为空，响应401状态码");
            response.setStatus(401);
            return false; // 不放行，且不抛异常
        }

        // 6 校验通过 放行
        log.info("令牌合法 放行");
        return true;

        // 7 删除ThreadLoca中的数据
        //CurrentHolder.removeCurrentId();

    }

    // ================== 关键：必须在这里清除 ThreadLocal ==================
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 7 删除ThreadLoca中的数据
        CurrentHolder.removeCurrentId();
    }

}
