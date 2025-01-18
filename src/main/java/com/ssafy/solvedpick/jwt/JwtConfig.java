package com.ssafy.solvedpick.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
public class JwtConfig {
	private final String secretKey;
    
    public JwtConfig(@Value("${jwt.secret}") String secretKey) {
    	this.secretKey = secretKey;
    }
    @Bean
    public Key key() {
        // 안전한 길이의 키 생성
//        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        
//         또는 기존 시크릿 키를 사용하는 경우
         return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}