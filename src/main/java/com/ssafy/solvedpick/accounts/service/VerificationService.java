package com.ssafy.solvedpick.accounts.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ssafy.solvedpick.accounts.domain.VerificationKey;
import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.UsernameResponse;
import com.ssafy.solvedpick.accounts.repository.VerificationKeyRepository;
import com.ssafy.solvedpick.global.error.exception.VerificationNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Slf4j
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
			log.debug("check user");
            restTemplate.getForEntity(url + username, UsernameResponse.class);
            return true;
        } catch (Exception e) {
			log.error("{}", e.getMessage());
			return false;
        }
	}

    public boolean verifyUser(SignupFormDTO signupFormDTO) {
    	String username = signupFormDTO.getUsername();

        try {
        	VerificationKey verificationKey = verificationKeyRepository.findByUsername(username)
                    .orElseThrow(() -> new VerificationNotFoundException("Verification key not found for username: " + username));
			log.debug("code: {}", verificationKey.getVerificationCode());

        	ResponseEntity<UsernameResponse> response =
					restTemplate.getForEntity(url + username, UsernameResponse.class);

            return response.getBody() != null
					&& verificationKey.getVerificationCode().equals(response.getBody().getName());
        } catch(HttpClientErrorException.NotFound e) {
			log.error("user not found");
			return false;
		} catch (Exception e) {
			log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to verify user");
        }
    }
}
