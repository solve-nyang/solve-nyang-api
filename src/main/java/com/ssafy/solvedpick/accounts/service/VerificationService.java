package com.ssafy.solvedpick.accounts.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.solvedpick.accounts.domain.VerificationKey;
import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.UsernameResponse;
import com.ssafy.solvedpick.accounts.repository.VerificationKeyRepository;
import com.ssafy.solvedpick.global.error.exception.ApiResponseException;
import com.ssafy.solvedpick.global.error.exception.VerificationNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {
	private final RestTemplate restTemplate;
	private final VerificationKeyRepository verificationKeyRepository;

	@Transactional
	public String generateVerificationCode(String username) {
	    String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    SecureRandom secureRandom = new SecureRandom();
	    
	    StringBuilder code = new StringBuilder(12);
	    for (int i = 0; i < 12; i++) {
	        code.append(characters.charAt(secureRandom.nextInt(characters.length())));
	    }
	    String verificationCode = code.toString();
	    
	    verificationKeyRepository.findByUsername(username)
        .ifPresentOrElse(
            key -> key.updateVerificationCode(verificationCode),
            () -> {
                VerificationKey newKey = VerificationKey.builder()
                    .username(username)
                    .verificationCode(verificationCode)
                    .createdAt(LocalDateTime.now())
                    .build();
                verificationKeyRepository.save(newKey);
            }
        );

	    return verificationCode;
	}
	
	@Value("${URL.USER_INFO}")
	private String url;
	
	public boolean checkUser(String username) {
        try {
            ResponseEntity<UsernameResponse> response = 
                restTemplate.getForEntity(url + username, UsernameResponse.class);
            System.out.println(response.getBody().getName());
            if (response.getBody().getName() == null) {
                throw new ApiResponseException("User not found: " + username);
            }
            
            return true;
        } catch (Exception e) {
        	throw new ApiResponseException("Failed to check user");
        }
	}

    public boolean verifyUser(SignupFormDTO signupFormDTO) {
    	String username = signupFormDTO.getUsername();
        try {
        	VerificationKey verificationKey = verificationKeyRepository.findByUsername(username)
                    .orElseThrow(() -> new VerificationNotFoundException("Verification key not found for username: " + username));
        	System.out.println(verificationKey);
        	ResponseEntity<UsernameResponse> response = 
                    restTemplate.getForEntity(url + username, UsernameResponse.class);

            if (response.getBody() == null) {
                throw new ApiResponseException("User not found");
            }

            return verificationKey.getVerificationCode().equals(response.getBody().getName());
                
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify user");
        }
    }
}
