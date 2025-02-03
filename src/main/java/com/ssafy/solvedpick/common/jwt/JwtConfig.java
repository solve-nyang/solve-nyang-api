package com.ssafy.solvedpick.common.jwt;

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
         return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}