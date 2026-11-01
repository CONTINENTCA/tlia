package com.zly.tliaswebmanagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * JWT 令牌工具类
 */
@Component
public class JwtUtils {

    // 【修复】使用够长的安全密钥（长度 ≥256位，满足新版JJWT要求）
    private static final String SIGN_KEY = "aXRoZWltYQ==aGVsbG93b3JsZGp3dHNlY3JldGtleQ==aGVsbG93b3JsZGp3dHNlY3JldGtleQ==";

    // 令牌有效期：12小时（单位：毫秒）
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000L;

    /**
     * 生成 JWT 令牌
     * @param claims 要存储的自定义数据（如 id, username）
     * @return jwt令牌字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        // 新版：生成安全密钥
        SecretKey secretKey = Keys.hmacShaKeyFor(SIGN_KEY.getBytes());

        return Jwts.builder()
                .claims(claims) // 自定义数据
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(secretKey) // 自动选择HS256算法
                .compact();
    }


    /**
     * 解析 JWT 令牌
     * @param token 前端传过来的 jwt 令牌
     * @return 解析出来的载荷数据
     */
    /**
     * 解析 JWT 令牌
     */
    public static Claims parseJwt(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SIGN_KEY.getBytes());

        return Jwts.parser()
                .verifyWith(secretKey) // 新版验证方式
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

