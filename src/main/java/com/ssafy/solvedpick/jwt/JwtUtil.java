package com.ssafy.solvedpick.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;
//    private final long accessTokenExpiration;
//    private final long refreshTokenExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
//            @Value("${jwt.access-token-expiration}") long accessTokenExpiration
//            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
//		this.accessTokenExpiration = accessTokenExpiration;
//        this.refreshTokenExpiration = refreshTokenExpiration;
    }
    
    public String generateAccessToken(String username) {
//        return generateToken(username, accessTokenExpiration);
    	return generateToken(username);
    }

//    public String generateRefreshToken(String username) {
//        return generateToken(username, refreshTokenExpiration);
//    }

//    private String generateToken(String username, long expiration) {
    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("Token has expired");
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }
    
//    public String recreateAccessToken(String refreshToken) {
//        String username = validateToken(refreshToken);
//        return generateAccessToken(username);
//    }

    
    // 인증키
    private final long verificationTokenExpiration = 1000 * 60 * 10;

    public String generateVerificationToken(String token) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + verificationTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .claim("token", token)
                .claim("type", "verification")
                .compact();
    }

    public String extractTokenFromVerificationToken(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        
        if (!"verification".equals(claims.get("type"))) {
            throw new JwtException("Invalid token type");
        }
        
        return claims.get("token", String.class);
    }
}