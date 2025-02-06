package com.ssafy.solvedpick.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    private static final Set<String> PERMIT_ALL_PATHS = Set.of(
    		"/account/verify",
            "/account/signin",
            "/account/signout",
            "/account/signup",
            "/account/password/find",
            "/jwt/reissue",
            "/avatar",
            "/compose/**",
            "/favicon.ico",
            "/user/me/extension"
    );
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	if (PERMIT_ALL_PATHS.contains(request.getRequestURI())) {
    	        filterChain.doFilter(request, response);
    	        return;
    	    }
    	
        try {
            String accessToken = jwtUtil.extractJwtFromRequest(request);
            
            if (accessToken != null) {
            	processToken(accessToken, request);
            }
            
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleAuthenticationError(response, e.getMessage());
        }
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
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Authentication Failed");
        errorDetails.put("message", message);
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);

        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}
