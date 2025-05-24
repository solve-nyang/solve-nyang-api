package com.solvenyang.common.jwt.presentation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solvenyang.common.error.dto.ErrorResponse;
import com.solvenyang.common.error.exception.jwt.JwtExpiredException;
import com.solvenyang.common.error.exception.jwt.JwtInvalidException;
import com.solvenyang.common.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {
	private final JwtUtil jwtUtil;
	
	@PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest httpRequest, HttpServletResponse response) {
		String accessToken = jwtUtil.extractJwtFromRequest(httpRequest);
		String refreshToken = jwtUtil.extractRefreshTokenFromCookie(httpRequest);
        
		if (accessToken == null) {
			throw new JwtInvalidException("Access token이 필요합니다.");
	    }
		
        if (refreshToken == null) {
        	throw new JwtInvalidException("Refresh token이 필요합니다.");
        }

        try {
        	try {
                jwtUtil.validateToken(accessToken);
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .message("Access token이 아직 유효합니다.")
                            .build());
            } catch (JwtExpiredException e) {
                String newAccessToken = jwtUtil.recreateAccessToken(refreshToken);
                	
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", newAccessToken);
                
                return ResponseEntity.ok(tokens);
            }            
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
        }
    }
}
