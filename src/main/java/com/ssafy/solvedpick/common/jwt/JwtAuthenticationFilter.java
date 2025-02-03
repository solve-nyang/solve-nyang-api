package com.ssafy.solvedpick.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String accessToken = extractJwtFromRequest(request);
            
            if (accessToken != null) {
            	try {
                    processToken(accessToken, request);
                } catch (RuntimeException e) {
                    String refreshToken = extractRefreshTokenFromCookie(request);
                    if (refreshToken != null) {
                        String newAccessToken = jwtUtil.recreateAccessToken(refreshToken);
                        response.setHeader("New-Access-Token", newAccessToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleAuthenticationError(response, e.getMessage());
        }
    }
    
    private String extractJwtFromRequest(HttpServletRequest request) {
    	 String authorizationHeader = request.getHeader("Authorization");
         if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
             return authorizationHeader.substring(7);
         }
         return null;
    }
    
    private void processToken(String accessToken, HttpServletRequest request) {
        String username = jwtUtil.validateToken(accessToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            createAndSetAuthentication(username, request);
        }
    }

    private void createAndSetAuthentication(String username, HttpServletRequest request) {
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(username, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleAuthenticationError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Authentication Failed");
        errorDetails.put("message", message);
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);

        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
    
    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
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