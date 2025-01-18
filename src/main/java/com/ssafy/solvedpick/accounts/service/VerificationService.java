package com.ssafy.solvedpick.accounts.service;

import java.security.SecureRandom;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.solvedpick.accounts.domain.VerificationKey;
import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.UsernameResponse;
import com.ssafy.solvedpick.accounts.repository.VerificationKeyRepository;

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

	    VerificationKey verificationKey = verificationKeyRepository.findByUsername(username);
	    if (verificationKey != null) {
	        verificationKey.updateVerificationCode(verificationCode);
	    } else {
	        VerificationKey key = VerificationKey.builder()
	            .username(username)
	            .verificationCode(verificationCode)
	            .build();
	        this.verificationKeyRepository.save(key);
	    }

	    return verificationCode;
	}
	
	public boolean checkUser(String username) {
		String url = "${URL.USER_INFO}";
        try {
            ResponseEntity<UsernameResponse> response = 
                restTemplate.getForEntity(url, UsernameResponse.class);
            
            return response.getBody() != null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to check user with Solved.AC");
        }
	}

    public boolean verifyUser(SignupFormDTO signupFormDTO) {
    	String username = signupFormDTO.getUsername();
        String url = "${URL.USER_INFO}";
        try {
            ResponseEntity<UsernameResponse> response = 
                restTemplate.getForEntity(url, UsernameResponse.class);
            System.out.println("res : " + response.getBody().getName());
            String code = verificationKeyRepository.findByUsername(username).getVerificationCode();
            System.out.println(code);
            
            if (response.getBody() != null && 
            		code.equals(response.getBody().getName())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify user with Solved.AC");
        }
    }
}
