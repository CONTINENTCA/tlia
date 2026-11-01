package com.zly.tliaswebmanagement;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /*
     * 必须使用足够长度的密钥。
     * 该字符串是 Base64 编码后的密钥。
     */
    private static final String SECRET =
            "aXRoZWltYV9qd3Rfc2VjcmV0X2tleV8zMjEyMzQ1Njc4OTA=";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    /**
     * 生成 JWT 令牌
     */
    @Test
    public void testGenerateJwt() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");

        String jwt = Jwts.builder()
                .claims(dataMap)
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(getSecretKey())
                .compact();

        System.out.println(jwt);
    }

    /**
     * 解析 JWT 令牌
     */
    @Test
    public void testParseJwt() {
        String token = testCreateToken();

        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        System.out.println(claims);
    }

    /**
     * 生成一个用于测试解析的 Token
     */
    private String testCreateToken() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");

        return Jwts.builder()
                .claims(dataMap)
                .signWith(getSecretKey())
                .compact();
    }
}