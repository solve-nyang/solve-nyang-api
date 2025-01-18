package com.ssafy.solvedpick.accounts.service;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.solvedpick.accounts.domain.VerificationKey;
import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.SolvedACResponse;
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
        // 랜덤한 12자리 알파벳+숫자 조합 생성
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 12; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        String verificationCode = code.toString();
        
        // 기존 키가 있는지 먼저 확인하고 업데이트하거나 새로 생성
        if (verificationKeyRepository.findByUsername(username) != null) {
        	VerificationKey verificationKey = verificationKeyRepository.findByUsername(username);
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
		String url = "https://solved.ac/api/v3/user/additional_info?handle=" + username;
        try {
            ResponseEntity<SolvedACResponse> response = 
                restTemplate.getForEntity(url, SolvedACResponse.class);
            System.out.println(response.getBody());
            if (response.getBody() != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to check user with Solved.AC");
        }
	}

    public boolean verifyUser(SignupFormDTO signupFormDTO) {
    	String username = signupFormDTO.getUsername();
        String url = "https://solved.ac/api/v3/user/additional_info?handle=" + username;
        try {
            ResponseEntity<SolvedACResponse> response = 
                restTemplate.getForEntity(url, SolvedACResponse.class);
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
