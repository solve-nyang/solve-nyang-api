package com.ssafy.solvedpick.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.ssafy.solvedpick.common.error.exception.jwt.JwtExpiredException;
import com.ssafy.solvedpick.common.error.exception.jwt.JwtInvalidException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtUtil(
    		@Value("${jwt.secret}") String secretKey,
    		@Value("${jwt.access-token-expiration}") long accessTokenExpiration,
    		@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
    
    public String generateAccessToken(String username) {
        return generateToken(username, accessTokenExpiration);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshTokenExpiration);
    }

    private String generateToken(String username, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
        	Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration() == null) {
                throw new JwtInvalidException("유효하지 않은 토큰입니다.");
            }
            
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new JwtInvalidException("유효하지 않은 토큰입니다.");
        }
    }
    
    public void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
    	ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
    	        .httpOnly(true)
    	        .secure(true)
    	        .path("/")
                .domain("www.solve-nyang.com")
    	        .sameSite("Strict")
    	        .maxAge(Duration.ofMillis(refreshTokenExpiration))
    	        .build();
    	response.addHeader("Set-Cookie", cookie.toString());
    }
    
    public String recreateAccessToken(String refreshToken) {
        String username = validateToken(refreshToken);
        return generateAccessToken(username);
    }
    
    public String extractJwtFromRequest(HttpServletRequest request) {
   	 String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
   }
   
    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
   	Cookie[] cookies = request.getCookies();
   	if (cookies != null) {
   		for (Cookie cookie : cookies) {
   			if ("refresh_token".equals(cookie.getName())) {
   				return cookie.getValue();
   			}
   		}
   	}
   	return null;
   }
}