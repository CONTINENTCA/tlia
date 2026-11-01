package com.zly.tliaswebmanagement.controller;
/*
JWT令牌
全称：JSON Web Token(https://iwt.io/)
●定义了一种简洁的、自包含的格式，用于在通信双方以jso数据格式安全的传输信息。
●组成：
第一部分：Header(头)，记录令牌类型、签名算法等。例如：{"alg":"HS256","type":"JWT"}
第二部分：Payload(有效载荷)，携带一些自定义信息、默认信息等。例如：{"id":"1","username":"Tom"}
第三部分：Signature(签名)，防止Token被篡改、确保安全性。将header、payload融入，并加入指定秘钥，通过指定签名算法计算而来。
 */
import com.zly.tliaswebmanagement.pojo.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SessionController {
    // 设置cookie
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse response){
        response.addCookie(new Cookie("login_username","itheima")); // 设置cookie
        return Result.success();
    }

    // 获取cookie
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login_username")){
                System.out.println("login_username:"+cookie.getValue());
            }
        }
        return Result.success();
    }

    @GetMapping("/s1")
    public Result session1(HttpSession session){
        log.info("HttpSession-s1:{}",session.hashCode());
        session.setAttribute("loginUser","tom"); // 往session中存储数据
        return Result.success();
    }

    @GetMapping("/s2")
    public Result session2(HttpSession session){
        log.info("HttpSession-s2:{}",session.hashCode());
        Object obj = session.getAttribute("loginUser"); // 从session获取数据
        log.info("loginUser:{}",obj);
        return Result.success(obj);
    }
}
